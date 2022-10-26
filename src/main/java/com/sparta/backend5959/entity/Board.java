package com.sparta.backend5959.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.backend5959.dto.BoardReqDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Board extends Timestamped {

    @Formula("(select count(1) from comments bc where bc.board_id = id)")
    private int totalCommentCount;

    @Formula("(select count(1) from heart bc where bc.board_id = id)")
    private int totalHeartCount;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String author;

    //ManyToOne은 fetch default가 EAGER이므로 LAZY 붙여줘야함
    //OneToMany는 fetch default가 LAZY이므로 빼줘도 됨
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Comments> commentList = new java.util.ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Heart> HeartList = new java.util.ArrayList<>();

    public Board(BoardReqDto boardReqDto, Member member) {
        this.title = boardReqDto.getTitle();
        this.content = boardReqDto.getContent();
        this.author = member.getUsername();
        this.member = member;
    }

    public void update(BoardReqDto boardReqDto) {
        this.title = boardReqDto.getTitle();
        this.content = boardReqDto.getContent();
    }

    public void updateCommentList(List<Comments> commentsList) {
        this.commentList = commentsList;
    }
}
