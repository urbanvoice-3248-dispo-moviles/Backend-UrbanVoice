package com.upc.pre.urbanvoiceapp.reports.application.services;

import com.upc.pre.urbanvoiceapp.reports.domain.entities.Vote;
import com.upc.pre.urbanvoiceapp.reports.domain.repositories.VoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VoteApplicationService {

    private final VoteRepository voteRepository;

    public VoteApplicationService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public Vote vote(Long reportId, Long userId, String voteType) {
        if (!voteType.equals("UP") && !voteType.equals("DOWN")) {
            throw new IllegalArgumentException("voteType must be UP or DOWN");
        }
        var existing = voteRepository.findByReportIdAndUserId(reportId, userId);
        if (existing.isPresent()) {
            Vote existingVote = existing.get();
            if (existingVote.getVoteType().equals(voteType)) {
                voteRepository.deleteById(existingVote.getId());
                return null;
            }
            voteRepository.deleteById(existingVote.getId());
        }
        Vote vote = new Vote(reportId, userId, voteType);
        return voteRepository.save(vote);
    }

    @Transactional(readOnly = true)
    public long countUpvotes(Long reportId) {
        return voteRepository.countByReportIdAndVoteType(reportId, "UP");
    }

    @Transactional(readOnly = true)
    public long countDownvotes(Long reportId) {
        return voteRepository.countByReportIdAndVoteType(reportId, "DOWN");
    }
}
