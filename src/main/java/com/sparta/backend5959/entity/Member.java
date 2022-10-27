package com.sparta.backend5959.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.backend5959.security.Authority;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Member extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_id;

    @Column(nullable = false, unique = true)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

//    @JsonIgnore
//    @Column(nullable = false)
//    private String email;

    @Column(unique = true)
    private Long kakaoId;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Authority authority;

    // 관계성 읽기 전용 (허트가 잘 읽어갈 수 있도록 읽기 설정해줌)
    @OneToMany(mappedBy = "member")
    private List<Heart> heartList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comments> commentsList = new ArrayList<>();

    public Member(String username, String password, Authority authority) {
        this.username = username;
        this.password = password;
//        this.email = email;
        this.authority = authority;
        this.kakaoId = null;
    }

    public Member(String username, String password, Authority authority, Long kakaoId) {
        this.username = username;
        this.password = password;
//        this.email = email;
        this.authority = authority;
        this.kakaoId = kakaoId;
    }

}
