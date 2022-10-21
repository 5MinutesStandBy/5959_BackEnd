package com.sparta.backend5959.controller;

import com.sparta.backend5959.dto.BoardReqDto;
import com.sparta.backend5959.dto.ResponseDto;
import com.sparta.backend5959.security.MemberDetails;
import com.sparta.backend5959.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    // 게시판 전체 조회
    @GetMapping("/boards")
    public ResponseDto<?> getBoardList() {
        return ResponseDto.success(boardService.getBoardList());
    }

    // 게시판 전체 페이징 조회 GET /boards/pager?page=3&size=10&sort=id,DESC  + Board Total Count
    @GetMapping ("/boards/pager")
    public ResponseDto<?> getBoardPagerList(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc) {
        return boardService.getBoardPagerList(page,size,sortBy,isAsc);
    }

    // 게시판 하나만 가져오기
    @PostMapping("/boards/{id}")
    public ResponseDto<?> getBoard(@PathVariable Long id) {
        return ResponseDto.success(boardService.getBoard(id));
    }

    // 게시판 생성 (권한 필요)
    @PostMapping("/auth/boards")
    public ResponseDto<?> createBoard(
            @RequestBody @Valid BoardReqDto boardReqDto,
            @AuthenticationPrincipal MemberDetails memberDetails
            ) {
        return ResponseDto.success(boardService.createBoard(boardReqDto, memberDetails.getMember()));
    }

    // 게시판 수정 (권한 필요)
    @PutMapping("/auth/boards/{id}")
    public ResponseDto<?> editBoard(
            @PathVariable Long id,
            @RequestBody BoardReqDto boardReqDto,
            @AuthenticationPrincipal MemberDetails memberDetails) {
        return ResponseDto.success(boardService.editBoard(id, boardReqDto, memberDetails.getMember()));
    }

    //게시판 삭제 (권한 필요)
    @DeleteMapping("/auth/boards/{id}")
    public ResponseDto<?> deleteBoard(
            @PathVariable Long id,
            @AuthenticationPrincipal MemberDetails memberDetails) {
        boardService.deleteBoard(id, memberDetails.getMember());
        return ResponseDto.success("게시글 삭제 완료");
    }
}
