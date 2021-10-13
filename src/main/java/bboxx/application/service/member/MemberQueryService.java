package bboxx.application.service.member;

import bboxx.domain.member.query.GetMemberOneQuery;
import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import bboxx.domain.member.querymodel.MemberReader;
import bboxx.domain.member.querymodel.MemberView;
import org.springframework.stereotype.Service;

@Service
public class MemberQueryService {

    private final MemberReader memberReader;

    public MemberQueryService(MemberReader memberReader) {
        this.memberReader = memberReader;
    }

    public MemberView getMemberOne(GetMemberOneQuery query) {
        return memberReader.findById(query.getMemberId())
                .map(MemberView::of)
                .orElseThrow(() -> new DomainException(DomainErrorCode.MEMBER_NOT_FOUND_ERROR));
    }
}
