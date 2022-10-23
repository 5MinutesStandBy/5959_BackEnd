package com.sparta.backend5959.service;


import com.sparta.backend5959.dto.CommentReqDto;
import com.sparta.backend5959.dto.ResponseDto;
import com.sparta.backend5959.entity.Board;
import com.sparta.backend5959.entity.Comments;
import com.sparta.backend5959.entity.Member;
import com.sparta.backend5959.repository.BoardRepository;
import com.sparta.backend5959.repository.CommentRepository;
import com.sparta.backend5959.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    // 댓글 작성 (권한)
    @Transactional
    public ResponseDto<?> createComment(Member member, CommentReqDto commentReqDto) {
        Board board = boardRepository.findById(commentReqDto.getBoard_id()).orElseThrow(()->new RuntimeException("게시글이 존재하지 않습니다"));
        Comments comments = new Comments(commentReqDto, member, board);
        commentRepository.save(comments);
        return ResponseDto.success(comments);
    }

    // 댓글 수정 (권한)
    @Transactional
    public ResponseDto<?> editComment(Long id, Member member, CommentReqDto commentReqDto) {
        Comments comments = commentRepository.findById(id).orElseThrow(
                ()->new RuntimeException("댓글을 찾을 수 없습니다")
        );
        if(!comments.getMember().getUsername().equals(member.getUsername())) {
            throw new RuntimeException("본인이 작성한 댓글이 아닙니다");
        }
        comments.update(commentReqDto);
        commentRepository.save(comments);
        return ResponseDto.success(comments);
    }

    @Transactional
    public ResponseDto<?> deleteComment(Long id, Member member) {
        Comments comments = commentRepository.findById(id).orElseThrow(()->new RuntimeException("댓글을 찾을 수 없습니다"));

        if(!comments.getMember().getUsername().equals(member.getUsername())) {
            throw new RuntimeException("본인이 작성한 댓글이 아닙니다");
        }
        commentRepository.deleteById(id);
        return ResponseDto.success("댓글 삭제 성공");
    }
}
