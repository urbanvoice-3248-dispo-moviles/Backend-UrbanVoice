package com.upc.pre.urbanvoiceapp.reports.infrastructure.persistence.jpa.mappers;

import com.upc.pre.urbanvoiceapp.reports.domain.entities.Vote;
import com.upc.pre.urbanvoiceapp.reports.infrastructure.persistence.jpa.entities.VoteJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class VoteJpaMapper {

    public Vote toDomain(VoteJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;
        return new Vote(
                jpaEntity.getId(),
                jpaEntity.getReportId(),
                jpaEntity.getUserId(),
                jpaEntity.getVoteType()
        );
    }

    public VoteJpaEntity toJpa(Vote domain) {
        if (domain == null) return null;
        VoteJpaEntity jpaEntity = new VoteJpaEntity();
        jpaEntity.setId(domain.getId());
        jpaEntity.setReportId(domain.getReportId());
        jpaEntity.setUserId(domain.getUserId());
        jpaEntity.setVoteType(domain.getVoteType());
        jpaEntity.setCreatedAt(domain.getCreatedAt());
        return jpaEntity;
    }
}
