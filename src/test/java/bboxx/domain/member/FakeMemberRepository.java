package bboxx.domain.member;

import bboxx.domain.member.commandmodel.MemberRepository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FakeMemberRepository implements MemberRepository {

    public List<Member> members = new ArrayList<>();

    @Override
    public Member save(Member member) {
        if (member.getId() == null) {
            try {
                Field idField = member.getClass().getDeclaredField("id");
                idField.setAccessible(true);
                idField.set(member, RandomIdGenerator.generate(members.stream().map(Member::getId).collect(Collectors.toList())));
            } catch (Exception e) {
                // do nothing ...
            }
            this.members.add(member);
        } else {
            int memberIndex = members.stream()
                    .map(Member::getId)
                    .collect(Collectors.toList())
                    .indexOf(member.getId());
            if (memberIndex == -1) {
                members.add(member);
            } else {
                members.set(memberIndex, member);
            }
        }
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return members.stream()
                .filter(member -> id.equals(member.getId()))
                .findFirst();
    }

    @Override
    public Optional<Member> findByProviderIdAndProviderType(String providerId, SocialProviderType providerType) {
        return members.stream()
                .filter(member -> providerId.equals(member.getProviderId()))
                .filter(member -> providerType.equals(member.getProviderType()))
                .findFirst();
    }
}

