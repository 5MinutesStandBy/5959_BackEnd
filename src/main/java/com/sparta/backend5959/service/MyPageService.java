package com.sparta.backend5959.service;


import com.sparta.backend5959.dto.ResponseDto;
import com.sparta.backend5959.entity.Comments;
import com.sparta.backend5959.entity.Heart;
import com.sparta.backend5959.entity.Member;
import com.sparta.backend5959.repository.BoardRepository;
import com.sparta.backend5959.repository.CommentRepository;
import com.sparta.backend5959.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {

    public final BoardRepository boardRepository;
    public final CommentRepository commentRepository;
    public final HeartRepository heartRepository;

    // 내 게시글 불러오기
    public ResponseDto<?> getMyBoardList(Member member) {
        return ResponseDto.success(boardRepository.findAllByMember(member).orElseThrow
                (() -> new RuntimeException("아이디로 작성된 게시글은 찾을 수 없습니다")));
    }
    // 내 댓글 불러오기
    public List<Comments> getMyCommentList(Member member) {
        return commentRepository.findAllByMember(member).orElseThrow
                (() -> new RuntimeException("아이디로 작성된 댓글은 찾을 수 없습니다"));
    }

    // 내가 좋아요한 게시글 불러오기
    public List<Heart> getMyHeartList(Member member) {
        return heartRepository.findAllByMember(member).orElseThrow
                (() -> new RuntimeException("좋아요한 글은 찾을 수 없습니다"));
    }
}
