package com.upc.pre.urbanvoiceapp.shared.locationsharing.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShareSessionResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("owner_user_id")
    private Long ownerUserId;

    @JsonProperty("target_user_id")
    private Long targetUserId;

    @JsonProperty("active")
    private Boolean active;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
