package com.example.theme_market.repository;


import com.example.theme_market.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String userName);

    Optional<Member> findByEmail(String email);

}
