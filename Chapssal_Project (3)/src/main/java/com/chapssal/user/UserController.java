package com.chapssal.user;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chapssal.follow.FollowService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {
	
    @Autowired
    private final UserService userService;
    
    @Autowired
    private FollowService followService;
	
    // 회원 가입 버튼 누를 시 회원 가입 페이지로 이동
    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("userCreateForm", new UserCreateForm());
        return "signup_form";
    }

	// 회원 가입 화면에서 양식 입력 후 버튼 누를 시 받아온 데이터 처리 
    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }
        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 비밀번호가 일치하지 않습니다.");
            return "signup_form";
        }
        try {
            userService.create(
                userCreateForm.getUserId(),
                userCreateForm.getPassword1(),
                LocalDateTime.now(),
                LocalDateTime.now(), 
                LocalDateTime.now()   
            );
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed","이미 등록된 사용자입니다.");
            return "signup_form";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed",e.getMessage());
            return "signup_form";
        }
        redirectAttributes.addFlashAttribute("successMessage", "회원가입이 성공하셨습니다.");
        return "redirect:/";
    }
	
    @GetMapping("/login")
    public String login() {
        return "login_form";
    }
    
    
    @GetMapping("/profile")
    public String getUserProfile(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        String username = currentUser.getUsername();
        String schoolName = userService.getSchoolNameByUserId(username);
        String userName = userService.getUserNameByUserId(username);
        String bio = userService.getUserBioByUserId(username);

        model.addAttribute("schoolName", schoolName);
        model.addAttribute("userName", userName);
        model.addAttribute("bio", bio);

        Integer userNum = userService.getUserNumByUserId(username);
        int followingCount = followService.countFollowingByUserNum(userNum);
        int followerCount = followService.countFollowerByUserNum(userNum);

        model.addAttribute("followingCount", followingCount);
        model.addAttribute("followerCount", followerCount);

        // 팔로잉과 팔로워 목록 가져오기
        List<User> followingUsers = followService.getFollowingUsers(userNum);
        List<User> followerUsers = followService.getFollowerUsers(userNum);

        model.addAttribute("followingUsers", followingUsers);
        model.addAttribute("followerUsers", followerUsers);


        return "profile";
    }

    
    @PostMapping("/updateProfile")
    public String updateProfile(@AuthenticationPrincipal UserDetails currentUser,
                                @RequestParam("userName") String newUserName,
                                @RequestParam("bio") String bio,
                                RedirectAttributes redirectAttributes) {
        try {
            userService.updateUserName(currentUser.getUsername(), newUserName, bio);
            redirectAttributes.addFlashAttribute("successMessage", "프로필이 성공적으로 업데이트되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "프로필 업데이트에 실패했습니다.");
            return "redirect:/user/profile";
        }

        return "redirect:/user/profile";
    }
    


}
