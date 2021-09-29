package bboxx.domain.member;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Member {

    private String id;

    private String nickname;

    private SocialProvider socialProvider;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Member(String id, String nickname, SocialProvider socialProvider, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.nickname = nickname;
        this.socialProvider = socialProvider;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
