package com.sparta.backend5959.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.backend5959.security.Authority;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Member extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_id;

    @NotBlank(message = "아이디를 입력해주세요")
    @Column(nullable = false, unique = true)
    private String username;

    @JsonIgnore
    @NotBlank(message = "비밀번호를 입력해주세요")
    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @NotBlank(message = "이메일을 입력해주세요")
    @Column(nullable = false)
    private String email;

    @Column(unique = true)
    private Long kakaoId;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Authority authority;

    public Member(String username, String password, String email, Authority authority) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.authority = authority;
        this.kakaoId = null;
    }

    public Member(String username, String password, String email, Authority authority, Long kakaoId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.authority = authority;
        this.kakaoId = kakaoId;
    }

}
