package com.upc.pre.urbanvoiceapp.reports.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("report_id")
    private Long reportId;

    @JsonProperty("vote_type")
    private String voteType;

    @JsonProperty("upvotes")
    private long upvotes;

    @JsonProperty("downvotes")
    private long downvotes;
}
