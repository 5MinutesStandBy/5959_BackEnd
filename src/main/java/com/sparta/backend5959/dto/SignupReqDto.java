package com.sparta.backend5959.dto;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SignupReqDto {

    @Pattern(regexp = "^([0-9]|[a-z]|[A-Z]|-|_|@|\\.){3,40}$", message = "잘못된 아이디 형식입니다")
    @NotBlank(message = "이름을 입력해주세요")
    private String username;

    @Pattern(regexp = "^([0-9]|[a-z]|[A-Z]){4,12}$", message = "잘못된 비밀번호 형식입니다")
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    private String passwordConfirm;

//    @Pattern(regexp = "^[a-zA-Z0-9]@[a-zA-Z].[a-z]$", message = "잘못된 이메일 형식입니다")
//    @NotBlank(message = "이메일을 입력해주세요")
//    private String email;
}
