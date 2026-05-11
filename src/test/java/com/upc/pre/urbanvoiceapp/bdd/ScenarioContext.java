package com.upc.pre.urbanvoiceapp.bdd;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ScenarioContext {
    private ResponseEntity<?> lastResponse;

    public ResponseEntity<?> getLastResponse() {
        return lastResponse;
    }

    public void setLastResponse(ResponseEntity<?> lastResponse) {
        this.lastResponse = lastResponse;
    }
}
