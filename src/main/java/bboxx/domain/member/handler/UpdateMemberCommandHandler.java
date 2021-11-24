package bboxx.domain.member.handler;

import bboxx.domain.CommandHandler;
import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import bboxx.domain.member.Member;
import bboxx.domain.member.command.UpdateMemberCommand;
import bboxx.domain.member.commandmodel.MemberRepository;

public class UpdateMemberCommandHandler implements CommandHandler<UpdateMemberCommand, Long> {

    private final MemberRepository memberRepository;

    public UpdateMemberCommandHandler(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Long handle(UpdateMemberCommand command) {
        Member member = memberRepository.findById(command.getMemberId())
                .orElseThrow(() -> new DomainException(DomainErrorCode.MEMBER_NOT_FOUND_ERROR));

        member.updateInfo(command.getNickname());
        return member.getId();
    }
}
