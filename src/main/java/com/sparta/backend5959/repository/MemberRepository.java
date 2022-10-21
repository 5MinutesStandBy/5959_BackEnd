package com.sparta.backend5959.repository;


import com.sparta.backend5959.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository  extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    Optional<Member> findByKakaoId(Long kakaoId);
    Optional<Member> findByEmail(String email);
    boolean existsByUsername(String username);
}