package com.upc.pre.urbanvoiceapp.reports.interfaces.rest;

import com.upc.pre.urbanvoiceapp.reports.application.services.VoteApplicationService;
import com.upc.pre.urbanvoiceapp.reports.interfaces.rest.resources.VoteRequest;
import com.upc.pre.urbanvoiceapp.reports.interfaces.rest.resources.VoteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/votes")
@Tag(name = "Votes", description = "API para votar reportes de incidentes")
public class VoteController {

    private final VoteApplicationService voteService;

    public VoteController(VoteApplicationService voteService) {
        this.voteService = voteService;
    }

    @PostMapping
    @Operation(summary = "Votar un reporte (UP/DOWN)")
    public ResponseEntity<VoteResponse> vote(
            @RequestHeader(value = "X-User-ID", required = true) Long userId,
            @RequestBody VoteRequest request) {
        var vote = voteService.vote(request.getReportId(), userId, request.getVoteType());
        long upvotes = voteService.countUpvotes(request.getReportId());
        long downvotes = voteService.countDownvotes(request.getReportId());

        VoteResponse response = new VoteResponse();
        if (vote != null) {
            response.setId(vote.getId());
            response.setReportId(vote.getReportId());
            response.setVoteType(vote.getVoteType());
        }
        response.setUpvotes(upvotes);
        response.setDownvotes(downvotes);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{reportId}")
    @Operation(summary = "Obtener recuento de votos de un reporte")
    public ResponseEntity<VoteResponse> getVoteCount(@PathVariable Long reportId) {
        long upvotes = voteService.countUpvotes(reportId);
        long downvotes = voteService.countDownvotes(reportId);
        VoteResponse response = new VoteResponse();
        response.setReportId(reportId);
        response.setUpvotes(upvotes);
        response.setDownvotes(downvotes);
        return ResponseEntity.ok(response);
    }
}
