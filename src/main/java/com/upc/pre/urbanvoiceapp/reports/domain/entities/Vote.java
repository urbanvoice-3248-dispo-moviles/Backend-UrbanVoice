package com.upc.pre.urbanvoiceapp.reports.domain.entities;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Vote {
    private final Long id;
    private final Long reportId;
    private final Long userId;
    private final String voteType;
    private final LocalDateTime createdAt;

    public Vote(Long id, Long reportId, Long userId, String voteType) {
        this.id = id;
        this.reportId = reportId;
        this.userId = userId;
        this.voteType = voteType;
        this.createdAt = LocalDateTime.now();
    }

    public Vote(Long reportId, Long userId, String voteType) {
        this(null, reportId, userId, voteType);
    }
}
