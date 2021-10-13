package bboxx.domain.member.commandmodel;

public interface TokenGenerator {
    String generateToken(String id, String nickname);
}
