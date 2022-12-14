package com.sparta.backend5959.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CommentReqDto {
    private Long board_id;

    @NotBlank(message = "댓글 내용이 필요합니다")
    private String content;
}
