package com.sparta.backend5959.repository;

import com.sparta.backend5959.entity.Board;
import com.sparta.backend5959.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByOrderByModifiedAtDesc();
    List<Board> findAllByOrderByIdDesc();
    Optional<List<Board>> findAllByMember(Member member);
    Slice<Board> findAllByOrderById(Pageable pageable);
    Slice<Board> findFirstBy(Pageable pageable);
}