package com.personal.parkwa.dto;

import javax.validation.constraints.NotBlank;

import com.personal.parkwa.entity.Server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class ArticleForm {

    @NotBlank(message = "닉네임 없이 보낼수 없습니다.")
    private String name;
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    private Server server;


}
