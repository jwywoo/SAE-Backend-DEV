package com.example.saebackenddev.domain.member.base.entity;

import com.example.saebackenddev.domain.member.auth.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SavedStockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requested_user")
    private MemberEntity requestedMember;

    private String tickerCode;

    public SavedStockEntity(MemberEntity requestedMember, String tickerCode){
        this.requestedMember = requestedMember;
        this.tickerCode = tickerCode;
    }

}
