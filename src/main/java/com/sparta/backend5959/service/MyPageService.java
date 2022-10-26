package com.sparta.backend5959.service;


import com.sparta.backend5959.dto.MyPageBoardResDto;
import com.sparta.backend5959.dto.ResponseDto;
import com.sparta.backend5959.entity.Board;
import com.sparta.backend5959.entity.Member;
import com.sparta.backend5959.repository.BoardRepository;
import com.sparta.backend5959.repository.CommentRepository;
import com.sparta.backend5959.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {

    public final BoardRepository boardRepository;
    public final CommentRepository commentRepository;
    public final HeartRepository heartRepository;

    // 내 게시글 불러오기
    public ResponseDto<?> getMyBoardList(Member member) {
        List<Board> boardList = boardRepository.findAllByMember(member).orElseThrow(() -> new RuntimeException(member.getUsername() + "님이 작성한 게시글을 찾을 수 없습니다"));
        List<MyPageBoardResDto> myPageBoardListResDto = new ArrayList<>();
        for (Board board : boardList) {
            MyPageBoardResDto myPageBoardResDto = new MyPageBoardResDto(board);
            myPageBoardListResDto.add(myPageBoardResDto);
        }
        return ResponseDto.success(myPageBoardListResDto);
    }

    // 내 댓글 불러오기
    public ResponseDto<?> getMyCommentList(Member member) {
        return ResponseDto.success(commentRepository.findAllByMember(member).orElseThrow
                (() -> new RuntimeException(member.getUsername() + "님이 작성한 댓글을 찾을 수 없습니다")));
    }

    // 내가 좋아요한 게시글 불러오기
    public ResponseDto<?> getMyHeartList(Member member) {
        return ResponseDto.success(heartRepository.findAllByMember(member).orElseThrow
                (() -> new RuntimeException(member.getUsername() + "님이 좋아요한 게시글을 찾을 수 없습니다")));
    }

    // 내가 작성한 게시글 불러오기 (페이저)
    public ResponseDto<?> getMyBoardPagerList(int page,int size,String sortBy,boolean isAsc, Member member) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseDto.success(boardRepository.findAllByMember(member, pageable).orElseThrow
                (() -> new RuntimeException(member.getUsername() + "님이 작성한 게시글을 찾을 수 없습니다")));
    }

    // 내가 작성한 댓글 불러오기 (페이저)
    public ResponseDto<?> getMyCommentPagerList(int page,int size,String sortBy,boolean isAsc, Member member) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseDto.success(commentRepository.findAllByMember(member, pageable).orElseThrow
                (() -> new RuntimeException(member.getUsername() + "님이 작성한 댓글을 찾을 수 없습니다")));
    }

    // 내가 좋아요한 게시글 불러오기 (페이저)
    public ResponseDto<?> getMyHeartPagerList(int page,int size,String sortBy,boolean isAsc, Member member) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseDto.success(heartRepository.findAllByMember(member, pageable).orElseThrow
                (() -> new RuntimeException(member.getUsername() + "님이 좋아요한 게시글을 찾을 수 없습니다")));
    }
}
