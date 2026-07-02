package com.upc.pre.urbanvoiceapp.reports.domain.repositories;

import com.upc.pre.urbanvoiceapp.reports.domain.entities.Vote;

import java.util.List;
import java.util.Optional;

public interface VoteRepository {
    Vote save(Vote vote);
    Optional<Vote> findById(Long id);
    Optional<Vote> findByReportIdAndUserId(Long reportId, Long userId);
    List<Vote> findByReportId(Long reportId);
    long countByReportIdAndVoteType(Long reportId, String voteType);
    void deleteById(Long id);
}
