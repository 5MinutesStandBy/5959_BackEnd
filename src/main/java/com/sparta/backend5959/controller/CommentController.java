package com.sparta.backend5959.controller;


import com.sparta.backend5959.dto.CommentReqDto;
import com.sparta.backend5959.dto.ResponseDto;
import com.sparta.backend5959.security.MemberDetails;
import com.sparta.backend5959.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/boards")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성 (권한 필요)
    @PostMapping("/{boardId}/comments")
    public ResponseDto<?> createComment(
            @PathVariable Long boardId,
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody @Valid CommentReqDto commentReqDto) {
        return commentService.createComment(memberDetails.getMember(), commentReqDto, boardId);
    }

    // 댓글 수정하기 (권한 필요)
    @PostMapping("/comments/{id}")
    public ResponseDto<?> editComment(
            @PathVariable Long id,
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestBody CommentReqDto commentReqDto) {
        return commentService.editComment(id, memberDetails.getMember(), commentReqDto);
    }

    // 댓글 삭제하기 (권한 필요)
    @DeleteMapping("/comments/{id}")
    public ResponseDto<?> deleteComment(
            @PathVariable Long id,
            @AuthenticationPrincipal MemberDetails memberDetails) {
        return commentService.deleteComment(id, memberDetails.getMember());
    }
}

// 댓글 작성하기, 댓글 수정하기(작성자만), 댓글 삭제하기(작성자만)