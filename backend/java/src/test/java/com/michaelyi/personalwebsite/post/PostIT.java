package com.michaelyi.personalwebsite.post;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.michaelyi.personalwebsite.IntegrationTest;
import com.michaelyi.personalwebsite.cache.CacheService;
import com.michaelyi.personalwebsite.s3.S3Service;
import com.michaelyi.personalwebsite.util.HttpResponse;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestPropertySource("classpath:application-test.properties")
class PostIT extends IntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private PostDao dao;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private CacheService cacheService;

    private static final MockMultipartFile IMAGE = new MockMultipartFile(
            "image",
            "image.png",
            "image/png",
            "image".getBytes());

    @BeforeEach
    void setUp() {
        cacheService.flushAll();
        dao.deleteAllPosts();
        s3Service.deleteObject("title");
        s3Service.deleteObject("oldboy");
    }

    @Test
    void postConstructor() throws Exception {
        String auth = getAuth(mvc);

        String text = "";
        String res = mvc.perform(multipart("/v2/post")
                .file(IMAGE)
                .param("text", text)
                .header(HttpHeaders.AUTHORIZATION, auth))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        CreatePostResponse createPostResponse = MAPPER.readValue(
                res,
                CreatePostResponse.class);
        assertEquals("Text is invalid", createPostResponse.getError());

        text = "no-title";
        res = mvc.perform(multipart("/v2/post")
                .file(IMAGE)
                .param("text", text)
                .header(HttpHeaders.AUTHORIZATION, auth))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        createPostResponse = MAPPER.readValue(
                res,
                CreatePostResponse.class);
        assertEquals("Title cannot be blank", createPostResponse.getError());

        text = "<h1></h1>";
        res = mvc.perform(multipart("/v2/post")
                .file(IMAGE)
                .param("text", text)
                .header(HttpHeaders.AUTHORIZATION, auth))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        createPostResponse = MAPPER.readValue(
                res,
                CreatePostResponse.class);
        assertEquals("Title cannot be blank", createPostResponse.getError());

        text = "<h1>no-content</h1>";
        res = mvc.perform(multipart("/v2/post")
                .file(IMAGE)
                .param("text", text)
                .header(HttpHeaders.AUTHORIZATION, auth))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        createPostResponse = MAPPER.readValue(
                res,
                CreatePostResponse.class);
        assertEquals("Content cannot be blank", createPostResponse.getError());

        text = "<h1>Oldboy (2003)</h1><p>content</p>";
        res = mvc.perform(multipart("/v2/post")
                .file(IMAGE)
                .param("text", text)
                .header(HttpHeaders.AUTHORIZATION, auth))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        createPostResponse = MAPPER.readValue(
                res,
                CreatePostResponse.class);
        String id = createPostResponse.getPostId();

        res = mvc.perform(get("/v2/post/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        GetPostResponse getPostResponse = MAPPER.readValue(
            res,
            GetPostResponse.class);
        Post actual = getPostResponse.getPost();
        assertEquals(id, actual.getId());
        assertEquals("Oldboy (2003)", actual.getTitle());
        assertEquals("<p>content</p>", actual.getContent());
    }

    @Test
    void createPost() throws Exception {
        String auth = getAuth(mvc);
        String text = "<h1>Already Exists (1994)</h1>content";

        mvc.perform(multipart("/v2/post")
                .file(IMAGE)
                .param("text", text)
                .header(HttpHeaders.AUTHORIZATION, auth))
                .andExpect(status().isCreated());

        String res = mvc.perform(multipart("/v2/post")
                .file(IMAGE)
                .param("text", text)
                .header(HttpHeaders.AUTHORIZATION, auth))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
        CreatePostResponse createPostResponse = MAPPER.readValue(
                res,
                CreatePostResponse.class);
        assertEquals(
                "A post with the same title already exists",
                createPostResponse.getError());

        mvc.perform(multipart("/v2/post")
                .file(IMAGE)
                .param("text", text))
                .andExpect(status().isUnauthorized());

        text = "<h1>Title (1994)</h1>Content";

        res = mvc.perform(multipart("/v2/post")
                .file(IMAGE)
                .param("text", text)
                .header(HttpHeaders.AUTHORIZATION, auth))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        createPostResponse = MAPPER.readValue(
                res,
                CreatePostResponse.class);
        String id = createPostResponse.getPostId();

        res = mvc.perform(get("/v2/post/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        GetPostResponse getPostResponse = MAPPER.readValue(
            res,
            GetPostResponse.class);
        Post actual = getPostResponse.getPost();

        assertEquals(id, actual.getId());
        assertEquals("Title (1994)", actual.getTitle());
        assertEquals("Content", actual.getContent());

        res = mvc.perform(get("/v2/post/image/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        GetPostImageResponse getPostImageResponse = MAPPER.readValue(
                res,
                GetPostImageResponse.class);

        assertArrayEquals(IMAGE.getBytes(), getPostImageResponse.getImage());
    }

    @Test
    void getPost() throws Exception {
        String auth = getAuth(mvc);
        String res = mvc.perform(get("/v2/post/oldboy"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();
        GetPostResponse getPostResponse = MAPPER.readValue(
            res,
            GetPostResponse.class);
        assertEquals("Post not found", getPostResponse.getError());

        String text = "<h1>Oldboy (2003)</h1><p>In Park Chan-wook's film...</p>";
        res = mvc.perform(multipart("/v2/post")
                .file(IMAGE)
                .param("text", text)
                .header(HttpHeaders.AUTHORIZATION, auth))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        CreatePostResponse createPostResponse = MAPPER.readValue(
                res,
                CreatePostResponse.class);
        String id = createPostResponse.getPostId();

        res = mvc.perform(get("/v2/post/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        getPostResponse = MAPPER.readValue(res, GetPostResponse.class);
        Post actual = getPostResponse.getPost();

        assertEquals("oldboy", id);
        assertEquals("oldboy", actual.getId());
        assertEquals("Oldboy (2003)", actual.getTitle());
        assertEquals(
                "<p>In Park Chan-wook's film...</p>",
                actual.getContent());
    }

    @Test
    void getPostImage() throws Exception {
        String auth = getAuth(mvc);
        String res = mvc.perform(get("/v2/post/image/oldboy"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();
        GetPostImageResponse getPostImageResponse = MAPPER.readValue(
                res,
                GetPostImageResponse.class);
        assertEquals("Post not found", getPostImageResponse.getError());

        String text = "<h1>Oldboy (2003)</h1><p>In Park Chan-wook's film...</p>";

        res = mvc.perform(multipart("/v2/post")
                .file(IMAGE)
                .param("text", text)
                .header(HttpHeaders.AUTHORIZATION, auth))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        CreatePostResponse createPostResponse = MAPPER.readValue(
                res,
                CreatePostResponse.class);
        String id = createPostResponse.getPostId();

        res = mvc.perform(get("/v2/post/image/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        getPostImageResponse = MAPPER.readValue(
            res,
            GetPostImageResponse.class);
        assertArrayEquals(IMAGE.getBytes(), getPostImageResponse.getImage());
    }

    @Test
    void getAllPosts() throws Exception {
        String auth = getAuth(mvc);
        String text = "<h1>Title (1994)</h1>Content";
        mvc.perform(multipart("/v2/post")
                .file(IMAGE)
                .param("text", text)
                .header(HttpHeaders.AUTHORIZATION, auth))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        text = "<h1>Oldboy (2003)</h1><p>In Park Chan-wook's film...</p>";
        mvc.perform(multipart("/v2/post")
                .file(IMAGE)
                .param("text", text)
                .header(HttpHeaders.AUTHORIZATION, auth))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        text = "<h1>It's A Wonderful Life (1946)</h1><p>by Frank Capra.</p>";
        mvc.perform(multipart("/v2/post")
                .file(IMAGE)
                .param("text", text)
                .header(HttpHeaders.AUTHORIZATION, auth))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String res = mvc.perform(get("/v2/post"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        GetAllPostsResponse getAllPostsResponse = MAPPER.readValue(
                res,
                GetAllPostsResponse.class);
        List<Post> actual = getAllPostsResponse.getPosts();

        assertEquals(
                "its-a-wonderful-life",
                actual.get(0).getId());
        assertEquals(
                "It's A Wonderful Life (1946)",
                actual.get(0).getTitle());
        assertEquals(
                "<p>by Frank Capra.</p>",
                actual.get(0).getContent());

        assertEquals("oldboy", actual.get(1).getId());
        assertEquals("Oldboy (2003)", actual.get(1).getTitle());
        assertEquals(
                "<p>In Park Chan-wook's film...</p>",
                actual.get(1).getContent());

        assertEquals("title", actual.get(2).getId());
        assertEquals("Title (1994)", actual.get(2).getTitle());
        assertEquals("Content", actual.get(2).getContent());
    }

    @Test
    void updatePost() throws Exception {
        String auth = getAuth(mvc);
        String text = "<h1>Oldboy (2003)</h1><p>by Park Chan-wook.</p>";
        mvc.perform(multipart(HttpMethod.PATCH, "/v2/post/oldboy")
                .file(IMAGE)
                .param("text", text))
                .andExpect(status().isUnauthorized());

        String res = mvc.perform(multipart(HttpMethod.PATCH, "/v2/post/oldboy")
                .file(IMAGE)
                .param("text", text)
                .header(HttpHeaders.AUTHORIZATION, auth))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();
        HttpResponse httpResponse = MAPPER.readValue(res, HttpResponse.class);
        assertEquals("Post not found", httpResponse.getError());

        text = "<h1>Oldboy (2003)</h1><p>In Park Chan-wook's film...</p>";

        res = mvc.perform(multipart("/v2/post")
                .file(IMAGE)
                .param("text", text)
                .header(HttpHeaders.AUTHORIZATION, auth))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        CreatePostResponse createPostResponse = MAPPER.readValue(
                res,
                CreatePostResponse.class);
        String id = createPostResponse.getPostId();

        text = "<h1>Oldboy (2004)</h1><p>by Park Chan-wook.</p>";

        mvc.perform(multipart(
                HttpMethod.PATCH,
                "/v2/post/" + id)
                .file(IMAGE)
                .param("text", text)
                .header(HttpHeaders.AUTHORIZATION, auth))
                .andExpect(status().isNoContent())
                .andReturn()
                .getResponse()
                .getContentAsString();

        res = mvc.perform(get("/v2/post/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        GetPostResponse getPostResponse = MAPPER.readValue(
            res,
            GetPostResponse.class);
        Post actual = getPostResponse.getPost();

        assertEquals(id, actual.getId());
        assertEquals("Oldboy (2004)", actual.getTitle());
        assertEquals("<p>by Park Chan-wook.</p>", actual.getContent());

        res = mvc.perform(get("/v2/post/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        getPostResponse = MAPPER.readValue(res, GetPostResponse.class);
        actual = getPostResponse.getPost();

        assertEquals(id, actual.getId());
        assertEquals("Oldboy (2004)", actual.getTitle());
        assertEquals("<p>by Park Chan-wook.</p>", actual.getContent());

        res = mvc.perform(get("/v2/post/image/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        GetPostImageResponse getPostImageResponse = MAPPER.readValue(
                res,
                GetPostImageResponse.class);

        assertArrayEquals(IMAGE.getBytes(), getPostImageResponse.getImage());
        byte[] imageRes = getPostImageResponse.getImage();

        mvc.perform(multipart(HttpMethod.PATCH, "/v2/post/oldboy")
                .file(
                        new MockMultipartFile(
                                "image",
                                "image.jpg",
                                "image/jpeg",
                                "New Hello World!".getBytes()))
                .param("text", text)
                .header(HttpHeaders.AUTHORIZATION, auth))
                .andExpect(status().isNoContent())
                .andReturn()
                .getResponse()
                .getContentAsString();

        res = mvc.perform(get(String.format(
                "/v2/post/image/%s",
                id))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        getPostImageResponse = MAPPER.readValue(
                res,
                GetPostImageResponse.class);

        assertNotEquals(imageRes, getPostImageResponse.getImage());
        assertArrayEquals(
            "New Hello World!".getBytes(),
            getPostImageResponse.getImage());
    }

    @Test
    void deletePost() throws Exception {
        String auth = getAuth(mvc);
        String text = "<h1>Oldboy (2003)</h1><p>by Park Chan-wook.</p>";

        mvc.perform(delete("/v2/post/oldboy"))
                .andExpect(status().isUnauthorized());

        String res = mvc.perform(delete("/v2/post/oldboy")
                .header(HttpHeaders.AUTHORIZATION, auth))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();
        HttpResponse httpResponse = MAPPER.readValue(res, HttpResponse.class);
        assertEquals("Post not found", httpResponse.getError());

        res = mvc.perform(multipart("/v2/post")
                .file(IMAGE)
                .param("text", text)
                .header(HttpHeaders.AUTHORIZATION, auth))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        CreatePostResponse createPostResponse = MAPPER.readValue(
            res,
            CreatePostResponse.class);
        String id = createPostResponse.getPostId();

        res = mvc.perform(get("/v2/post/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        GetPostResponse getPostResponse = MAPPER.readValue(
            res,
            GetPostResponse.class);
        Post actual = getPostResponse.getPost();
        assertEquals(id, actual.getId());
        assertEquals("Oldboy (2003)", actual.getTitle());
        assertEquals("<p>by Park Chan-wook.</p>", actual.getContent());

        mvc.perform(delete("/v2/post/" + id)
                .header(HttpHeaders.AUTHORIZATION, auth))
                .andExpect(status().isNoContent());

        res = mvc.perform(get("/v2/post/" + id))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();
        httpResponse = MAPPER.readValue(res, HttpResponse.class);
        assertEquals("Post not found", httpResponse.getError());
    }
}
