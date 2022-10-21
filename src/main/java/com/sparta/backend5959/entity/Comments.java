package com.sparta.backend5959.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.backend5959.dto.CommentReqDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Comments extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String content;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @JsonIgnore
    @ManyToOne
    @JoinColumn( name = "member_id", nullable = false)
    private Member member;

    @JsonIgnore
    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Heart> HeartList = new java.util.ArrayList<>();


    public Comments(CommentReqDto commentReqDto, Member member, Board board ){
        this.content = commentReqDto.getContent();
        this.author = member.getUsername();
        this.member = member;
        this.board = board;
    }

    public void update( CommentReqDto commentReqDto) {
        this.content = commentReqDto.getContent();
    }
}

//댓글 : 내용, 작성자(아이디), 생성일자, 수정일자