package com.sparta.backend5959.controller;

import com.sparta.backend5959.dto.ResponseDto;
import com.sparta.backend5959.security.MemberDetails;
import com.sparta.backend5959.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage/")
public class MyPageController {

    private final MyPageService myPageService;

    // 마이 페이지
    
    // 내가 작성한 게시글 불러오기
    @GetMapping("/userboard")
    public ResponseDto<?> getMyBoardList(MemberDetails memberDetails) {
        return myPageService.getMyBoardList(memberDetails.getMember());
    }

    // 내가 작성한 댓글 불러오기
    @GetMapping("/usercomment")
    public ResponseDto<?> getMyCommentList(MemberDetails memberDetails) {
        return myPageService.getMyCommentList(memberDetails.getMember());
    }

    // 내가 좋아요한 게시글 불러오기
    @GetMapping("/userheart")
    public ResponseDto<?> getMyHeartList(MemberDetails memberDetails) {
        return myPageService.getMyHeartList(memberDetails.getMember());
    }


    // 내가 작성한 게시글 불러오기 (페이저)
    @GetMapping("/userpagerboard")
    public ResponseDto<?> getMyBoardPagerList(MemberDetails memberDetails, @RequestParam("page") int page,
                                              @RequestParam("size") int size,
                                              @RequestParam("sortBy") String sortBy,
                                              @RequestParam("isAsc") boolean isAsc) {
        int pageTemp = page - 1;
        return myPageService.getMyBoardPagerList(pageTemp,size,sortBy,isAsc, memberDetails.getMember());
    }

    // 내가 작성한 댓글 불러오기 (페이저)
    @GetMapping("/userpagercomment")
    public ResponseDto<?> getMyCommentPagerList(MemberDetails memberDetails, @RequestParam("page") int page,
                                                @RequestParam("size") int size,
                                                @RequestParam("sortBy") String sortBy,
                                                @RequestParam("isAsc") boolean isAsc) {
        int pageTemp = page - 1;
        return myPageService.getMyCommentPagerList(pageTemp,size,sortBy,isAsc, memberDetails.getMember());
    }

    // 내가 좋아요한 게시글 불러오기 (페이저)
    @GetMapping("/userpagerheart")
    public ResponseDto<?> getMyHeartPagerList(MemberDetails memberDetails, @RequestParam("page") int page,
                                              @RequestParam("size") int size,
                                              @RequestParam("sortBy") String sortBy,
                                              @RequestParam("isAsc") boolean isAsc) {
        int pageTemp = page - 1;
        return myPageService.getMyHeartPagerList(pageTemp,size,sortBy,isAsc, memberDetails.getMember());
    }

}
