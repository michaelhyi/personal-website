package com.michaelyi.personalwebsite.auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Random;
import java.util.Base64;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import io.netty.util.internal.ThreadLocalRandom;

public class AuthTestHelper {
    protected static String generateJwtSecret() {
        Random random = ThreadLocalRandom.current();
        byte[] bytes = new byte[64];
        random.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }

    protected static MockHttpServletResponse login(
            AuthRequest req,
            MockMvc mvc,
            ObjectMapper mapper,
            ObjectWriter writer) throws Exception {
        String endpoint = "/v2/auth/login";
        String reqJson = writer.writeValueAsString(req);

        return mvc.perform(post(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(reqJson))
                .andReturn()
                .getResponse();
    }

    protected static MockHttpServletResponse validateToken(
            String token,
            MockMvc mvc,
            ObjectMapper mapper,
            ObjectWriter writer) throws Exception {
        String endpoint = "/v2/auth/validate-token";

        return mvc.perform(post(endpoint)
                .servletPath(endpoint)
                .header(
                        HttpHeaders.AUTHORIZATION,
                        String.format("Bearer %s", token)))
                .andReturn()
                .getResponse();
    }

    protected static String getTokenFromResponse(
            MockHttpServletResponse servletResponse,
            ObjectMapper mapper,
            ObjectWriter writer) throws Exception {
        String json = servletResponse.getContentAsString();
        LoginResponse res = mapper.readValue(
                json,
                LoginResponse.class);
        return res.getToken();
    }

    public static String getAuth(
            MockMvc mvc,
            ObjectMapper mapper,
            ObjectWriter writer) throws Exception {
        AuthRequest req = new AuthRequest("authorized password");
        MockHttpServletResponse res = login(req, mvc, mapper, writer);
        String token = getTokenFromResponse(res, mapper, writer);
        return String.format("Bearer %s", token);
    }
}
