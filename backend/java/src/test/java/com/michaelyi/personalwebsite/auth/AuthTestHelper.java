package com.michaelyi.personalwebsite.auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthTestHelper {
    protected static final String FAKE_SIGNING_KEY = "fakesigningkeyfakesigningkeyfakesigningkeyfakesigningkey";

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

    protected static String generateToken(String key, long expiration) {
        Key signingKey = AuthUtil.getSigningKey(key);
        Map<String, Object> claims = new HashMap<>();
        long currTime = System.currentTimeMillis();

        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(AuthUtil.ADMIN_EMAIL)
                .setIssuedAt(new Date(currTime))
                .setExpiration(new Date(currTime + expiration))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
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
