package com.sparta.backend5959.service;


import com.sparta.backend5959.dto.MyPageBoardResDto;
import com.sparta.backend5959.dto.ResponseDto;
import com.sparta.backend5959.entity.Board;
import com.sparta.backend5959.entity.Heart;
import com.sparta.backend5959.entity.Member;
import com.sparta.backend5959.repository.BoardRepository;
import com.sparta.backend5959.repository.CommentRepository;
import com.sparta.backend5959.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        List<Board> boardList = boardRepository.findAllByMember(member);
        List<MyPageBoardResDto> myPageBoardListResDto = new ArrayList<>();
        for (Board board : boardList) {
            MyPageBoardResDto myPageBoardResDto = new MyPageBoardResDto(board);
            myPageBoardListResDto.add(myPageBoardResDto);
        }
        return ResponseDto.success(myPageBoardListResDto);
    }

    // 내 댓글 불러오기
    public ResponseDto<?> getMyCommentList(Member member) {
        return ResponseDto.success(commentRepository.findAllByMember(member));
    }

    // 내가 좋아요한 게시글 불러오기
    @Transactional
    public ResponseDto<?> getMyHeartList(Member member) {

// 좋아요 테이블에서 현재 멤버의 member_id로 된 컬럼들을 가져온다
        List<Heart> heartList = heartRepository.findAllByMember(member);

        List<MyPageBoardResDto> myPageBoardResDtos = new ArrayList<>();
// 가져온 컬럼들에서 boar_id를 뽑아낸다
        for (Heart heart:heartList) {
            Long board_id = heart.getBoard().getId();
            Board board = boardRepository.findById(board_id).orElseThrow(()->new RuntimeException("있쥬?"));
            MyPageBoardResDto myPageBoardResDto = new MyPageBoardResDto(board);
            myPageBoardResDtos.add(myPageBoardResDto);
        }
// 해당 board_id들로 boardList를 만들어 반환한다

        return ResponseDto.success(myPageBoardResDtos);
    }
}
