package com.capstone.repository;

import com.capstone.domain.Member;
import com.capstone.domain.MemberSize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberSizeRepository extends JpaRepository<MemberSize, Long> {
    Optional<MemberSize> findByMember(Member member);
}
