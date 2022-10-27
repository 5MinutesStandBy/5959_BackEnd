package com.sparta.backend5959.repository;

import com.sparta.backend5959.entity.Board;
import com.sparta.backend5959.entity.Comments;
import com.sparta.backend5959.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comments, Long> {
    List<Comments> findAllByBoard(Board board);
    List<Comments> findAllByMember(Member member);
    Page<Comments> findAllByMember(Member member, Pageable pageable);
}