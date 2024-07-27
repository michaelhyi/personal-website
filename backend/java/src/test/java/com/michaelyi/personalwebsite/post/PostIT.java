package com.michaelyi.personalwebsite.post;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.michaelyi.personalwebsite.TestConfig;
import com.michaelyi.personalwebsite.auth.AuthRequest;
import com.michaelyi.personalwebsite.s3.S3Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestPropertySource("classpath:application-it.properties")
class PostIT extends TestConfig {
    private static final String AUTHORIZED_PASSWORD = "authorized password";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PostDao dao;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private ObjectMapper mapper;
    private ObjectWriter writer;

    @BeforeEach
    void setUp() {
        dao.deleteAllPosts();
        writer = mapper.writer().withDefaultPrettyPrinter();
        s3Service.deleteObject("title");
        s3Service.deleteObject("oldboy");
    }

    @Test
    void postConstructor() throws Exception {
        String token = mvc.perform(post("/v2/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(
                                new AuthRequest(AUTHORIZED_PASSWORD))
                        ))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String text = "";
        String error = mvc.perform(multipart("/v2/post")
                        .file(new MockMultipartFile(
                                "image",
                                "Hello World!".getBytes()
                        ))
                        .param("text", text)
                        .header(
                                "Authorization",
                                String.format("Bearer %s", token)
                        ))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResolvedException()
                .getMessage();
        assertEquals("Fields cannot be blank.", error);

        text = "no-title";
        error = mvc.perform(multipart("/v2/post")
                        .file(new MockMultipartFile(
                                "image",
                                "Hello World!".getBytes()
                        ))
                        .param("text", text)
                        .header(
                                "Authorization",
                                String.format("Bearer %s", token)
                        ))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResolvedException()
                .getMessage();
        assertEquals("Title cannot be blank.", error);

        text = "<h1></h1>";
        error = mvc.perform(multipart("/v2/post")
                        .file(new MockMultipartFile(
                                "image",
                                "Hello World!".getBytes()
                        ))
                        .param("text", text)
                        .header(
                                "Authorization",
                                String.format("Bearer %s", token)
                        ))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResolvedException()
                .getMessage();
        assertEquals("Title cannot be blank.", error);

        text = "<h1>no-content</h1>";
        error = mvc.perform(multipart("/v2/post")
                        .file(new MockMultipartFile(
                                "image",
                                "Hello World!".getBytes()
                        ))
                        .param("text", text)
                        .header(
                                "Authorization",
                                String.format("Bearer %s", token)
                        ))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResolvedException()
                .getMessage();
        assertEquals("Content cannot be blank.", error);

        text = "<h1>Oldboy (2003)</h1><p>content</p>";
        String id = mvc.perform(multipart("/v2/post")
                        .file(new MockMultipartFile(
                                "image",
                                "Hello World!".getBytes()
                        ))
                        .param("text", text)
                        .header(
                                "Authorization",
                                String.format("Bearer %s", token)
                        ))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String res = mvc.perform(get("/v2/post/" + id))
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
        String token = mvc.perform(post("/v2/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(
                                new AuthRequest(AUTHORIZED_PASSWORD))
                        ))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String text = "<h1>Already Exists (1994)</h1>content";

        mvc.perform(multipart("/v2/post")
                        .file(new MockMultipartFile(
                                "image",
                                "Hello World!".getBytes()
                        ))
                        .param("text", text)
                        .header(
                                "Authorization",
                                String.format("Bearer %s", token)
                        ))
                .andExpect(status().isCreated());

        String error = mvc.perform(multipart("/v2/post")
                        .file(new MockMultipartFile(
                                "image",
                                "Hello World!".getBytes()
                        ))
                        .param("text", text)
                        .header(
                                "Authorization",
                                String.format("Bearer %s", token)
                        ))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResolvedException()
                .getMessage();

        assertEquals(error, "A post with the same title already exists.");

        mvc.perform(multipart("/v2/post")
                        .file(new MockMultipartFile(
                                "image",
                                "Hello World!".getBytes()
                        ))
                        .param("text", text))
                .andExpect(status().isForbidden());

        text = "<h1>Title (1994)</h1>Content";

        String id = mvc.perform(multipart("/v2/post")
                        .file(new MockMultipartFile(
                                "image",
                                "Hello World!".getBytes()
                        ))
                        .param("text", text)
                        .header(
                                "Authorization",
                                String.format("Bearer %s", token)
                        ))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String res = mvc.perform(get("/v2/post/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Post actual = mapper.readValue(res, Post.class);

        assertEquals(id, actual.getId());
        assertEquals("Title (1994)", actual.getTitle());
        assertEquals("Content", actual.getContent());

        byte[] imageRes = mvc.perform(get("/v2/post/image/" + id)
                        .accept(MediaType.IMAGE_JPEG_VALUE))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsByteArray();

        assertArrayEquals("Hello World!".getBytes(), imageRes);
    }

    @Test
    void readPost() throws Exception {
        String token = mvc.perform(post("/v2/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(
                                new AuthRequest(AUTHORIZED_PASSWORD))
                        ))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String error = mvc.perform(get("/v2/post/oldboy"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResolvedException()
                .getMessage();
        assertEquals("Post not found.", error);

        String text =
                "<h1>Oldboy (2003)</h1><p>In Park Chan-wook's film...</p>";

        String id = mvc.perform(multipart("/v2/post")
                        .file(new MockMultipartFile(
                                "image",
                                "Hello World!".getBytes()
                        ))
                        .param("text", text)
                        .header(
                                "Authorization",
                                String.format("Bearer %s", token)
                        ))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String res = mvc.perform(get("/v2/post/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Post actual = mapper.readValue(res, Post.class);

        assertEquals("oldboy", id);
        assertEquals("oldboy", actual.getId());
        assertEquals("Oldboy (2003)", actual.getTitle());
        assertEquals(
                "<p>In Park Chan-wook's film...</p>",
                actual.getContent()
        );
    }

    @Test
    void readPostImage() throws Exception {
        String token = mvc.perform(post("/v2/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(
                                new AuthRequest(AUTHORIZED_PASSWORD))
                        ))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String error = mvc.perform(get("/v2/post/image/oldboy"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResolvedException()
                .getMessage();
        assertEquals("Post not found.", error);

        String text =
                "<h1>Oldboy (2003)</h1><p>In Park Chan-wook's film...</p>";

        String id = mvc.perform(multipart("/v2/post")
                        .file(new MockMultipartFile(
                                "image",
                                "Hello World!".getBytes()
                        ))
                        .param("text", text)
                        .header(
                                "Authorization",
                                String.format("Bearer %s", token)
                        ))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        byte[] image = mvc.perform(get("/v2/post/image/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsByteArray();
        assertArrayEquals("Hello World!".getBytes(), image);
    }

    @Test
    void readAllPosts() throws Exception {
        String token = mvc.perform(post("/v2/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(
                                new AuthRequest(AUTHORIZED_PASSWORD))
                        ))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String text = "<h1>Title (1994)</h1>Content";
        mvc.perform(multipart("/v2/post")
                        .file(new MockMultipartFile(
                                "image",
                                "Hello World!".getBytes()
                        ))
                        .param("text", text)
                        .header(
                                "Authorization",
                                String.format("Bearer %s", token)
                        ))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        text = "<h1>Oldboy (2003)</h1><p>In Park Chan-wook's film...</p>";
        mvc.perform(multipart("/v2/post")
                        .file(new MockMultipartFile(
                                "image",
                                "Hello World!".getBytes()
                        ))
                        .param("text", text)
                        .header(
                                "Authorization",
                                String.format("Bearer %s", token)
                        ))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        text = "<h1>It's A Wonderful Life (1946)</h1><p>by Frank Capra.</p>";
        mvc.perform(multipart("/v2/post")
                        .file(new MockMultipartFile(
                                "image",
                                "Hello World!".getBytes()
                        ))
                        .param("text", text)
                        .header(
                                "Authorization",
                                String.format("Bearer %s", token)
                        ))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String res = mvc.perform(get("/v2/post"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<Post> actual = mapper.readValue(
                res,
                new TypeReference<List<Post>>() {
                }
        );

        assertEquals(
                "its-a-wonderful-life",
                actual.get(0).getId()
        );
        assertEquals(
                "It's A Wonderful Life (1946)",
                actual.get(0).getTitle()
        );
        assertEquals(
                "<p>by Frank Capra.</p>",
                actual.get(0).getContent()
        );

        assertEquals("oldboy", actual.get(1).getId());
        assertEquals("Oldboy (2003)", actual.get(1).getTitle());
        assertEquals(
                "<p>In Park Chan-wook's film...</p>",
                actual.get(1).getContent()
        );

        assertEquals("title", actual.get(2).getId());
        assertEquals("Title (1994)", actual.get(2).getTitle());
        assertEquals("Content", actual.get(2).getContent());
    }

    @Test
    void updatePost() throws Exception {
        String token = mvc.perform(post("/v2/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(
                                new AuthRequest(AUTHORIZED_PASSWORD))
                        ))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String text = "<h1>Oldboy (2003)</h1><p>by Park Chan-wook.</p>";
        mvc.perform(multipart(HttpMethod.PUT, "/v2/post/oldboy")
                        .file(new MockMultipartFile(
                                "image",
                                "Hello World!".getBytes()
                        ))
                        .param("text", text))
                .andExpect(status().isForbidden());

        String error = mvc.perform(multipart(HttpMethod.PUT, "/v2/post/oldboy")
                        .file(new MockMultipartFile(
                                "image",
                                "Hello World!".getBytes()
                        ))
                        .param("text", text)
                        .header(
                                "Authorization",
                                String.format("Bearer %s", token)
                        ))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResolvedException()
                .getMessage();

        assertEquals("Post not found.", error);

        text = "<h1>Oldboy (2003)</h1><p>In Park Chan-wook's film...</p>";

        String id = mvc.perform(multipart("/v2/post")
                        .file(new MockMultipartFile(
                                "image",
                                "Hello World!".getBytes()
                        ))
                        .param("text", text)
                        .header(
                                "Authorization",
                                String.format("Bearer %s", token)
                        ))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        text = "<h1>Oldboy (2004)</h1><p>by Park Chan-wook.</p>";

        String res = mvc.perform(multipart(
                        HttpMethod.PUT,
                        String.format("/v2/post/%s", id))
                        .file(new MockMultipartFile(
                                "image",
                                "Hello World!".getBytes()
                        ))
                        .param("text", text)
                        .header(
                                "Authorization",
                                String.format("Bearer %s", token)
                        ))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Post actual = mapper.readValue(res, Post.class);

        assertEquals(id, actual.getId());
        assertEquals("Oldboy (2004)", actual.getTitle());
        assertEquals("<p>by Park Chan-wook.</p>", actual.getContent());

        res = mvc.perform(get("/v2/post/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        actual = mapper.readValue(res, Post.class);
        assertEquals(id, actual.getId());
        assertEquals("Oldboy (2004)", actual.getTitle());
        assertEquals("<p>by Park Chan-wook.</p>", actual.getContent());

        byte[] imageRes = mvc.perform(get("/v2/post/image/" + id)
                        .accept(MediaType.IMAGE_JPEG_VALUE))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsByteArray();

        assertArrayEquals("Hello World!".getBytes(), imageRes);

        mvc.perform(multipart(HttpMethod.PUT, "/v2/post/oldboy")
                        .file(new MockMultipartFile(
                                "image",
                                "New Hello World!".getBytes()
                        ))
                        .param("text", text)
                        .header(
                                "Authorization",
                                String.format("Bearer %s", token)
                        ))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        byte[] newImageRes = mvc.perform(get(String.format(
                        "/v2/post/image/%s",
                        id
                ))
                        .accept(MediaType.IMAGE_JPEG_VALUE))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsByteArray();

        assertNotEquals(imageRes, newImageRes);
        assertArrayEquals("New Hello World!".getBytes(), newImageRes);
    }

    @Test
    void deletePost() throws Exception {
        String token = mvc.perform(post("/v2/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writer.writeValueAsString(
                                new AuthRequest(AUTHORIZED_PASSWORD)
                        )))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String text = "<h1>Oldboy (2003)</h1><p>by Park Chan-wook.</p>";

        mvc.perform(delete("/v2/post/oldboy"))
                .andExpect(status().isForbidden());

        String error = mvc.perform(delete("/v2/post/oldboy")
                        .header(
                                "Authorization",
                                String.format("Bearer %s", token)
                        ))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResolvedException()
                .getMessage();

        assertEquals("Post not found.", error);

        String id = mvc.perform(multipart("/v2/post")
                        .file(
                                new MockMultipartFile(
                                        "image",
                                        "Hello World!".getBytes()
                                )
                        )
                        .param("text", text)
                        .header(
                                "Authorization",
                                String.format("Bearer %s", token)
                        ))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String res = mvc.perform(get("/v2/post/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Post actual = mapper.readValue(res, Post.class);
        assertEquals(id, actual.getId());
        assertEquals("Oldboy (2003)", actual.getTitle());
        assertEquals("<p>by Park Chan-wook.</p>", actual.getContent());

        mvc.perform(delete("/v2/post/" + id)
                        .header(
                                "Authorization",
                                String.format("Bearer %s", token)
                        ))
                .andExpect(status().isOk());

        error = mvc.perform(get("/v2/post/" + id))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResolvedException()
                .getMessage();
        assertEquals("Post not found.", error);
    }
}
