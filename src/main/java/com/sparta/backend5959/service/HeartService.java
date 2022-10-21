package com.sparta.backend5959.service;


import com.sparta.backend5959.entity.Board;
import com.sparta.backend5959.entity.Heart;
import com.sparta.backend5959.entity.Member;
import com.sparta.backend5959.repository.BoardRepository;
import com.sparta.backend5959.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;

    private final BoardRepository boardRepository;

    @Transactional
    public void heart(Long boardId, Member member) {

        Board board = boardRepository.findById(boardId).orElseThrow();

        if(heartRepository.existsByBoardAndMember(board, member)){
            heartRepository.deleteByBoardAndMember(board, member);
        } else {
            Heart heart = new Heart(member, board);
            heartRepository.save(heart);
        }
    }
}