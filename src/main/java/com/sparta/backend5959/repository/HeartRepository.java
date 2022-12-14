package com.sparta.backend5959.repository;

import com.sparta.backend5959.entity.Board;
import com.sparta.backend5959.entity.Heart;
import com.sparta.backend5959.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    boolean existsByBoardAndMember(Board boardId, Member member);
    void deleteByBoardAndMember(Board boardId, Member member);
    List<Heart> findAllByMember(Member member);
    Page<Heart> findAllByMember(Member member, Pageable pageable);
}