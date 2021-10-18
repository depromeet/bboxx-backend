package bboxx.domain.decibel;

import bboxx.domain.BaseTimeEntity;
import bboxx.domain.member.Member;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Decibel extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long decibel;

    private Long memberId;

}
