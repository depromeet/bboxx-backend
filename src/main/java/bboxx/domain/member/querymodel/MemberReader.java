package bboxx.domain.member.querymodel;

import bboxx.domain.member.Member;

import java.util.Optional;

public interface MemberReader {
    Optional<Member> findById(Long id);
}
