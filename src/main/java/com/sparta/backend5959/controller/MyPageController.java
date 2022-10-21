package com.sparta.backend5959.controller;

import com.sparta.backend5959.dto.ResponseDto;
import com.sparta.backend5959.security.MemberDetails;
import com.sparta.backend5959.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage/")
public class MyPageController {

    private final MyPageService myPageService;

    // 마이 페이지
    
    // 내가 작성한 게시글 불러오기
    @GetMapping("/getuserboard")
    public ResponseDto<?> getMyBoardList(MemberDetails memberDetails) {
        return ResponseDto.success(myPageService.getMyBoardList(memberDetails.getMember()));
    }

    // 내가 작성한 댓글 불러오기
    @GetMapping("/getusercomment")
    public ResponseDto<?> getMyCommentList(MemberDetails memberDetails) {
        return ResponseDto.success(myPageService.getMyCommentList(memberDetails.getMember()));
    }

    // 내가 좋아요한 게시글 불러오기
    @GetMapping("/getuserheart")
    public ResponseDto<?> getMyHeartList(MemberDetails memberDetails) {
        return ResponseDto.success(myPageService.getMyHeartList(memberDetails.getMember()));
    }

}
