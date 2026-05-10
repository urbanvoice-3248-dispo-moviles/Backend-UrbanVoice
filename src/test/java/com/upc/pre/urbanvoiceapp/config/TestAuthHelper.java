package com.upc.pre.urbanvoiceapp.config;

import com.upc.pre.urbanvoiceapp.security.iam.interfaces.rest.resources.SignInResource;
import com.upc.pre.urbanvoiceapp.security.iam.interfaces.rest.resources.SignUpResource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class TestAuthHelper {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public TestAuthHelper(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    public String registerAndAuthenticateTestUser(String username, String password) throws Exception {
        // 🔹 Sign-up
        SignUpResource signUp = new SignUpResource(username, password, Collections.singletonList("ROLE_USER"));
        mockMvc.perform(post("/api/v1/authentication/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUp)))
                .andExpect(status().isCreated());

        // 🔹 Sign-in
        SignInResource signIn = new SignInResource(username, password);
        String response = mockMvc.perform(post("/api/v1/authentication/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signIn)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode tokenNode = objectMapper.readTree(response);
        return tokenNode.get("token").asText();
    }
}