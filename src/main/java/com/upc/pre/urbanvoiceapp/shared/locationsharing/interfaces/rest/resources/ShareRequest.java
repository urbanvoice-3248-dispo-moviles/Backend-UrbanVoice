package com.upc.pre.urbanvoiceapp.shared.locationsharing.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShareRequest {
    @JsonProperty("target_user_id")
    private Long targetUserId;
}
