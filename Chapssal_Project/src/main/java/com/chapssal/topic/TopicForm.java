package com.chapssal.topic;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicForm {
    @NotEmpty(message = "토픽을 입력해주세요")
    private String topic;
}
