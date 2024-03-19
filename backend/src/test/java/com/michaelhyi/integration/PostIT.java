package com.michaelhyi.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.michaelhyi.dao.PostRepository;
import com.michaelhyi.dto.LoginRequest;
import com.michaelhyi.dto.PostRequest;
import com.michaelhyi.entity.Post;
import com.michaelhyi.service.S3Service;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource("classpath:application-it.properties")
class PostIT {
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
    private PostRepository repository;

    @Autowired
    private S3Service s3Service;

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
        s3Service.deleteObject("title");
        s3Service.deleteObject("oldboy");
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
    void postConstructor() throws Exception {
        String token = mvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(new LoginRequest("test@mail.com"))))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PostRequest req = new PostRequest(null);
        String error = mvc.perform(post("/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(req))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResolvedException()
                .getMessage();
        assertEquals("Fields cannot be blank.", error);

        req = new PostRequest("");
        error = mvc.perform(post("/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(req))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResolvedException()
                .getMessage();
        assertEquals("Fields cannot be blank.", error);

        req = new PostRequest("no-title");
        error = mvc.perform(post("/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(req))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResolvedException()
                .getMessage();
        assertEquals("Title cannot be blank.", error);

        req = new PostRequest("<h1></h1>");
        error = mvc.perform(post("/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(req))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResolvedException()
                .getMessage();
        assertEquals("Title cannot be blank.", error);

        req = new PostRequest("<h1>no-content</h1>");
        error = mvc.perform(post("/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(req))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResolvedException()
                .getMessage();
        assertEquals("Content cannot be blank.", error);

        req = new PostRequest("<h1>bad-title</h1><p>insert content</p>");
        error = mvc.perform(post("/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(req))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResolvedException()
                .getMessage();
        assertEquals("Title must contain a year in parentheses.", error);

        req = new PostRequest("<h1>title(1994)</h1><p>insert content</p>");
        error = mvc.perform(post("/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(req))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResolvedException()
                .getMessage();
        assertEquals("Year must be preceded by a space.", error);

        req = new PostRequest("<h1>Oldboy (2003)</h1><p>content</p>");
        String id = mvc.perform(post("/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(req))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String res = mvc.perform(get("/v1/post/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Post actual = mapper.readValue(res, Post.class);
        assertEquals(id, actual.getId());
        assertEquals("Oldboy (2003)", actual.getTitle());
        assertEquals("<p>content</p>", actual.getContent());
    }

    @Test
    void createPost() throws Exception {
        String token = mvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(new LoginRequest("test@mail.com"))))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PostRequest req = new PostRequest("<h1>Already Exists (1994)</h1>content");

        mvc.perform(post("/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(req))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        String error = mvc.perform(post("/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(req))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResolvedException()
                .getMessage();

        assertEquals(error, "A post with the same title already exists.");

        req = new PostRequest("<h1>Title (1994)</h1>Content");

        String id = mvc.perform(post("/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(req))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String res = mvc.perform(get("/v1/post/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Post actual = mapper.readValue(res, Post.class);

        assertEquals(id, actual.getId());
        assertEquals("Title (1994)", actual.getTitle());
        assertEquals("Content", actual.getContent());
    }

    @Test
    void createPostImage() throws Exception {
        String token = mvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(new LoginRequest("test@mail.com"))))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        MockMultipartFile file = new MockMultipartFile("file", "Hello World!".getBytes());

        String error = mvc.perform(multipart("/v1/post/does-not-exist/image")
                        .file(file)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResolvedException()
                .getMessage();

        assertEquals("Post not found.", error);

        PostRequest req = new PostRequest("<h1>Title (1994)</h1>Content");
        String id = mvc.perform(post("/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(req))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        mvc.perform(multipart("/v1/post/" + id + "/image")
                        .file(file)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        byte[] res = mvc.perform(get("/v1/post/" + id + "/image")
                        .accept(MediaType.IMAGE_JPEG_VALUE))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsByteArray();

        assertArrayEquals(file.getBytes(), res);

        MockMultipartFile newFile = new MockMultipartFile("file", "Hello World!".getBytes());
        mvc.perform(multipart("/v1/post/" + id + "/image")
                        .file(newFile)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        byte[] newRes = mvc.perform(get("/v1/post/" + id + "/image")
                        .accept(MediaType.IMAGE_JPEG_VALUE))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsByteArray();

        assertNotEquals(res, newRes);
        assertArrayEquals(newFile.getBytes(), newRes);
    }

    @Test
    void readPost() throws Exception {
        String token = mvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(new LoginRequest("test@mail.com"))))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String error = mvc.perform(get("/v1/post/oldboy"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResolvedException()
                .getMessage();
        assertEquals("Post not found.", error);

        PostRequest req = new PostRequest("<h1>Oldboy (2003)</h1><p>In Park Chan-wook's masterpiece...</p>");

        String id = mvc.perform(post("/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(req))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String res = mvc.perform(get("/v1/post/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Post actual = mapper.readValue(res, Post.class);

        assertEquals("oldboy", id);
        assertEquals("oldboy", actual.getId());
        assertEquals("Oldboy (2003)", actual.getTitle());
        assertEquals("<p>In Park Chan-wook's masterpiece...</p>", actual.getContent());
    }

    @Test
    void readPostImage() throws Exception {
        String token = mvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(new LoginRequest("test@mail.com"))))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String error = mvc.perform(get("/v1/post/oldboy/image"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResolvedException()
                .getMessage();
        assertEquals("Post not found.", error);

        PostRequest req = new PostRequest("<h1>Oldboy (2003)</h1><p>In Park Chan-wook's masterpiece...</p>");

        String id = mvc.perform(post("/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(req))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        error = mvc.perform(get("/v1/post/oldboy/image"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResolvedException()
                .getMessage();
        assertEquals("S3 object not found.", error);

        MockMultipartFile file = new MockMultipartFile("file", "Hello World!".getBytes());
        mvc.perform(multipart("/v1/post/" + id + "/image")
                        .file(file)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        byte[] image = mvc.perform(get("/v1/post/oldboy/image"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsByteArray();
        assertArrayEquals(file.getBytes(), image);
    }

    @Test
    void readAllPosts() throws Exception {
        String token = mvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(new LoginRequest("test@mail.com"))))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PostRequest req = new PostRequest("<h1>Title (1994)</h1>Content");
        mvc.perform(post("/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(req))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        req = new PostRequest("<h1>Oldboy (2003)</h1><p>In Park Chan-wook's masterpiece...</p>");
        mvc.perform(post("/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(req))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        req = new PostRequest("<h1>It's A Wonderful Life (1946)</h1><p>by Frank Capra.</p>");
        mvc.perform(post("/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(req))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String res = mvc.perform(get("/v1/post"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<Post> actual = mapper.readValue(res, new TypeReference<List<Post>>() {
        });

        assertEquals("its-a-wonderful-life", actual.get(0).getId());
        assertEquals("It's A Wonderful Life (1946)", actual.get(0).getTitle());
        assertEquals("<p>by Frank Capra.</p>", actual.get(0).getContent());

        assertEquals("oldboy", actual.get(1).getId());
        assertEquals("Oldboy (2003)", actual.get(1).getTitle());
        assertEquals("<p>In Park Chan-wook's masterpiece...</p>", actual.get(1).getContent());

        assertEquals("title", actual.get(2).getId());
        assertEquals("Title (1994)", actual.get(2).getTitle());
        assertEquals("Content", actual.get(2).getContent());
    }

    @Test
    void updatePost() throws Exception {
        String token = mvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(new LoginRequest("test@mail.com"))))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PostRequest req = new PostRequest("<h1>Oldboy (2003)</h1><p>by Park Chan-wook.</p>");

        String error = mvc.perform(put("/v1/post/oldboy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(req))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResolvedException()
                .getMessage();

        assertEquals("Post not found.", error);

        req = new PostRequest("<h1>Oldboy (2003)</h1><p>In Park Chan-wook's masterpiece...</p>");

        String id = mvc.perform(post("/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(req))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        req = new PostRequest("<h1>Oldboy (2003)</h1><p>by Park Chan-wook.</p>");

        String res = mvc.perform(put("/v1/post/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(req))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Post actual = mapper.readValue(res, Post.class);

        assertEquals(id, actual.getId());
        assertEquals("Oldboy (2003)", actual.getTitle());
        assertEquals("<p>by Park Chan-wook.</p>", actual.getContent());

        res = mvc.perform(get("/v1/post/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        actual = mapper.readValue(res, Post.class);
        assertEquals(id, actual.getId());
        assertEquals("Oldboy (2003)", actual.getTitle());
        assertEquals("<p>by Park Chan-wook.</p>", actual.getContent());
    }

    @Test
    void deletePost() throws Exception {
        String token = mvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(new LoginRequest("test@mail.com"))))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PostRequest req = new PostRequest("<h1>Oldboy (2003)</h1><p>by Park Chan-wook.</p>");

        String error = mvc.perform(delete("/v1/post/oldboy")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResolvedException()
                .getMessage();

        assertEquals("Post not found.", error);

        String id = mvc.perform(post("/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(req))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String res = mvc.perform(get("/v1/post/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Post actual = mapper.readValue(res, Post.class);
        assertEquals(id, actual.getId());
        assertEquals("Oldboy (2003)", actual.getTitle());
        assertEquals("<p>by Park Chan-wook.</p>", actual.getContent());

        mvc.perform(delete("/v1/post/" + id)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        error = mvc.perform(get("/v1/post/" + id))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResolvedException()
                .getMessage();
        assertEquals("Post not found.", error);
    }
}
