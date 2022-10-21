package com.sparta.backend5959.repository;

import com.sparta.backend5959.entity.Board;
import com.sparta.backend5959.entity.Heart;
import com.sparta.backend5959.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    boolean existsByBoardAndMember(Board boardId, Member member);

    void deleteByBoardAndMember(Board boardId, Member member);

    List<Heart> findAllByBoard(Board board);

    Optional<List<Heart>> findAllByMember(Member member);
}
