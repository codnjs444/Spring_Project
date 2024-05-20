package com.chapssal.user;

import java.time.LocalDateTime;

import com.chapssal.school.School;
import com.chapssal.school.SchoolRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private final UserService userService;

    @Autowired
    private final SchoolRepository schoolRepository;

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("userCreateForm", new UserCreateForm());
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }
        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 비밀번호가 일치하지 않습니다.");
            return "signup_form";
        }

        // 학교 코드가 존재하는지 확인
        School school = schoolRepository.findBySchoolCode(userCreateForm.getSchoolCode()).orElse(null);
        if(school == null) {
                bindingResult.rejectValue("schoolCode", "schoolCodeNotFound",
                    "유효한 학교 코드가 아닙니다.");
                return "signup_form";
        }


        try {
            userService.create(
                    userCreateForm.getUserId(),
                    userCreateForm.getPassword1(),
                    school.getSchoolNum(),  // 유저 엔티티에 학교 번호 저장
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );
        } catch (DuplicateUserIdException e) {
            bindingResult.rejectValue("userId", "duplicateUserId", e.getMessage());
            return "signup_form";
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }
        redirectAttributes.addFlashAttribute("successMessage", "회원가입이 성공하셨습니다.");
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login_form";
    }


    // 이 부분부터 소셜로그인 학교 코드 입력에 대한 부분

    @GetMapping("/enterSchoolCode")
    public String enterSchoolCode(Model model) {
        model.addAttribute("schoolCodeForm", new SchoolCodeForm());
        return "schoolCode_form";
    }

    @PostMapping("/enterSchoolCode")
    public String enterSchoolCode(@Valid SchoolCodeForm schoolCodeForm, BindingResult bindingResult, Authentication authentication, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "schoolCode_form";
        }

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String userId = oAuth2User.getAttribute("id").toString();
        User user = userService.findByUserId(userId);

        School school = schoolRepository.findBySchoolCode(schoolCodeForm.getSchoolCode()).orElse(null);
        if (school == null) {
            bindingResult.rejectValue("schoolCode", "schoolCodeNotFound", "유효한 학교 코드가 아닙니다.");
            return "schoolCode_form";
        }

        user.setSchool(school.getSchoolNum());
        userService.save(user);

        redirectAttributes.addFlashAttribute("successMessage", "학교 코드가 성공적으로 등록되었습니다.");
        return "redirect:/";
    }
}
