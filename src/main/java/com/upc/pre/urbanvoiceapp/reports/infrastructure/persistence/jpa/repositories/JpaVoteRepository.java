package com.upc.pre.urbanvoiceapp.reports.infrastructure.persistence.jpa.repositories;

import com.upc.pre.urbanvoiceapp.reports.domain.entities.Vote;
import com.upc.pre.urbanvoiceapp.reports.domain.repositories.VoteRepository;
import com.upc.pre.urbanvoiceapp.reports.infrastructure.persistence.jpa.mappers.VoteJpaMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaVoteRepository implements VoteRepository {

    private final VoteSpringDataRepository springDataRepository;
    private final VoteJpaMapper mapper;

    public JpaVoteRepository(VoteSpringDataRepository springDataRepository, VoteJpaMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    public Vote save(Vote vote) {
        var jpaEntity = mapper.toJpa(vote);
        var saved = springDataRepository.save(jpaEntity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Vote> findById(Long id) {
        return springDataRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Vote> findByReportIdAndUserId(Long reportId, Long userId) {
        return springDataRepository.findByReportIdAndUserId(reportId, userId).map(mapper::toDomain);
    }

    @Override
    public List<Vote> findByReportId(Long reportId) {
        return springDataRepository.findByReportId(reportId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public long countByReportIdAndVoteType(Long reportId, String voteType) {
        return springDataRepository.countByReportIdAndVoteType(reportId, voteType);
    }

    @Override
    public void deleteById(Long id) {
        springDataRepository.deleteById(id);
    }
}
