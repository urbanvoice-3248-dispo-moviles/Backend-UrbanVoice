package com.upc.pre.urbanvoiceapp.reports.infrastructure.persistence.jpa.repositories;

import com.upc.pre.urbanvoiceapp.reports.infrastructure.persistence.jpa.entities.VoteJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteSpringDataRepository extends JpaRepository<VoteJpaEntity, Long> {
    Optional<VoteJpaEntity> findByReportIdAndUserId(Long reportId, Long userId);
    List<VoteJpaEntity> findByReportId(Long reportId);
    long countByReportIdAndVoteType(Long reportId, String voteType);
}
