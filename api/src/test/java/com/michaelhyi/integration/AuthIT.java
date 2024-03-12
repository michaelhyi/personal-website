package com.michaelhyi.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.michaelhyi.dao.UserRepository;
import com.michaelhyi.dto.LoginRequest;
import com.michaelhyi.entity.User;
import com.michaelhyi.jwt.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource("classpath:application-it.properties")
class AuthIT {
    private static final int REDIS_PORT = 6379;
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.36");
    static GenericContainer<?> redis = new GenericContainer<>("redis:6.2.14")
                                            .withExposedPorts(REDIS_PORT);

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", () -> String.valueOf(redis.getMappedPort(REDIS_PORT))); 
    }

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository repository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private ObjectMapper mapper;
    private ObjectWriter writer;

    @BeforeAll
    static void beforeAll() {
        mysql.start();
        redis.start();
    }

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        writer = mapper.writer().withDefaultPrettyPrinter();
    } 

    @AfterEach
    void tearDown() {
        cacheManager.getCacheNames()
                    .parallelStream()
                    .forEach(n -> cacheManager.getCache(n).clear());
    }

    @AfterAll
    static void afterAll() {
        mysql.stop();
        redis.stop();
    }

    @Test
    void login() throws Exception {
        User alreadyExists = new User("alreadyexists@mail.com");
        repository.save(alreadyExists);

        String res = mvc.perform(post("/v1/auth/login")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(writer.writeValueAsString(new LoginRequest("alreadyexists@mail.com"))))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

        assertTrue(jwtService.isTokenValid(res, alreadyExists));
        assertEquals(alreadyExists.getEmail(), jwtService.extractUsername(res));

        String error = mvc.perform(post("/v1/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(writer.writeValueAsString(new LoginRequest("unauthorized@mail.com"))))
                            .andExpect(status().isUnauthorized())
                            .andReturn()
                            .getResolvedException()
                            .getMessage();
        
        assertEquals("Unauthorized.", error);

        res = mvc.perform(post("/v1/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(writer.writeValueAsString(new LoginRequest("test@mail.com"))))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

        User expected = repository
                            .findById("test@mail.com")
                            .get();

        assertTrue(jwtService.isTokenValid(res, expected));
        assertEquals(expected.getEmail(), jwtService.extractUsername(res));
    }

    @Test
    void validateToken() throws Exception {
        User user = new User("test@mail.com"); 
        String token = jwtService.generateToken(user);

        String error = mvc.perform(get("/v1/auth/validate-token/" + token))
                            .andExpect(status().isNotFound())
                            .andReturn()
                            .getResolvedException()
                            .getMessage();
        
        assertEquals("User not found.", error);
        
        repository.save(user);

        String unauthorizedToken = generateUnauthorizedToken(user);

        error = mvc.perform(get("/v1/auth/validate-token/" + unauthorizedToken))
            .andExpect(status().isUnauthorized())
            .andReturn()
            .getResolvedException()
            .getMessage();

        assertEquals("Unauthorized.", error);

        final String expiredToken = "eyJhbGciOiJIUzI1NiJ9"
                + ".eyJzdWIiOiJ0ZXN0QG1haWwuY29tIiwiaWF0IjoxNzA5MzI0OTUxLCJleHAiOjE3MDkzMjQ5NTF9"
                + ".0kgPiP5MELw6Pq6i9tJMXDxDy7n4Eu-LprqHOD4O2QM";

        error = mvc.perform(get("/v1/auth/validate-token/" + expiredToken))
            .andExpect(status().isUnauthorized())
            .andReturn()
            .getResolvedException()
            .getMessage();

        assertEquals("Unauthorized.", error);

        mvc.perform(get("/v1/auth/validate-token/" + token))
            .andExpect(status().isOk());
        
    }

    private String generateUnauthorizedToken(UserDetails details) {
        Map<String, Object> extraClaims = new HashMap<>();
        byte[] keyBytes = Decoders.BASE64.decode("fakesigninkeyfakesigninkeyfakesigninkeyfakesigninkey");

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(details.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(
                        System.currentTimeMillis() + 1L 
                ))
                .signWith(Keys.hmacShaKeyFor(keyBytes), SignatureAlgorithm.HS256)
                .compact();
    }
}
