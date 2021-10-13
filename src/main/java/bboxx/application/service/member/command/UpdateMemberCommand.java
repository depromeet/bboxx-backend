package bboxx.application.service.member.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateMemberCommand {
    private Long memberId;
    private String nickname;
}
