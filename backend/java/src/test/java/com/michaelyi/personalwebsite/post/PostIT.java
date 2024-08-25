package com.michaelyi.personalwebsite.post;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.michaelyi.personalwebsite.IntegrationTest;
import com.michaelyi.personalwebsite.auth.AuthTestHelper;
import com.michaelyi.personalwebsite.cache.CacheDao;
import com.michaelyi.personalwebsite.s3.S3Service;

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
    private CacheDao cacheDao;

    private static final MockMultipartFile IMAGE = new MockMultipartFile(
            "image",
            "image.png",
            "image/png",
            "image".getBytes());
    private String auth;

    @BeforeEach
    void setUp() throws Exception {
        auth = AuthTestHelper.getAuth(mvc, MAPPER, WRITER);
        cacheDao.flushAll();
        dao.deleteAllPosts();
        s3Service.deleteObject("title");
        s3Service.deleteObject("oldboy");
    }

    @Test
    void postConstructor() throws Exception {
        String text = "";
        MockHttpServletResponse res = PostTestHelper.createPost(
                auth,
                text,
                IMAGE,
                mvc,
                MAPPER,
                WRITER);
        assertEquals("Text is invalid", getError(res));

        text = "no-title";
        res = PostTestHelper.createPost(
                auth,
                text,
                IMAGE,
                mvc,
                MAPPER,
                WRITER);
        assertEquals("Title cannot be blank", getError(res));

        text = "<h1></h1>";
        res = PostTestHelper.createPost(
                auth,
                text,
                IMAGE,
                mvc,
                MAPPER,
                WRITER);
        assertEquals("Title cannot be blank", getError(res));

        text = "<h1>no-content</h1>";
        res = PostTestHelper.createPost(
                auth,
                text,
                IMAGE,
                mvc,
                MAPPER,
                WRITER);
        assertEquals("Content cannot be blank", getError(res));

        text = "<h1>Oldboy (2003)</h1><p>content</p>";
        res = PostTestHelper.createPost(
                auth,
                text,
                IMAGE,
                mvc,
                MAPPER,
                WRITER);
        String id = PostTestHelper.getPostIdFromResponse(res, MAPPER);

        res = PostTestHelper.getPost(id, mvc, MAPPER, WRITER);
        Post post = PostTestHelper.getPostFromResponse(res, MAPPER);
        assertEquals(id, post.getId());
        assertEquals("Oldboy (2003)", post.getTitle());
        assertEquals("<p>content</p>", post.getContent());
    }

    @Test
    void willThrowUnauthorizedDuringCreatePostWhenNoAuth() throws Exception {
        String text = "<h1>Oldboy (2003)</h1><p>content</p>";
        MockHttpServletResponse res = PostTestHelper.createPost(
                "",
                text,
                IMAGE,
                mvc,
                MAPPER,
                WRITER);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), res.getStatus());
    }

    @Test
    void willThrowBadRequestDuringCreatePostWhenPostWithSameTitleAlreadyExists()
            throws Exception {
        String text = "<h1>Oldboy (2003)</h1><p>content</p>";
        MockHttpServletResponse res = PostTestHelper.createPost(
                auth,
                text,
                IMAGE,
                mvc,
                MAPPER,
                WRITER);
        assertEquals(HttpStatus.CREATED.value(), res.getStatus());

        res = PostTestHelper.createPost(
                auth,
                text,
                IMAGE,
                mvc,
                MAPPER,
                WRITER);
        assertEquals(HttpStatus.BAD_REQUEST.value(), res.getStatus());
        assertEquals(
                "A post with the same title already exists",
                getError(res));
    }

    @Test
    void willCreatePostWhenPostDoesNotAlreadyExist() throws Exception {
        String text = "<h1>Oldboy (2003)</h1><p>content</p>";
        MockHttpServletResponse res = PostTestHelper.createPost(
                auth,
                text,
                IMAGE,
                mvc,
                MAPPER,
                WRITER);
        assertEquals(HttpStatus.CREATED.value(), res.getStatus());

        String id = PostTestHelper.getPostIdFromResponse(res, MAPPER);
        res = PostTestHelper.getPost(id, mvc, MAPPER, WRITER);
        Post post = PostTestHelper.getPostFromResponse(res, MAPPER);
        assertEquals(id, post.getId());
        assertEquals("Oldboy (2003)", post.getTitle());
        assertEquals("<p>content</p>", post.getContent());

        res = PostTestHelper.getPostImage(id, mvc, MAPPER, WRITER);
        byte[] image = PostTestHelper.getImageFromResponse(
                res,
                MAPPER);
        assertArrayEquals(IMAGE.getBytes(), image);
    }

    @Test
    void willThrowNotFoundDuringGetPost() throws Exception {
        MockHttpServletResponse res = PostTestHelper.getPost(
                "oldboy",
                mvc,
                MAPPER,
                WRITER);
        assertEquals("Post not found", getError(res));
    }

    @Test
    void willGetPostWhenPostExists() throws Exception {
        String text = "<h1>Oldboy (2003)</h1>"
                + "<p>In Park Chan-wook's film...</p>";
        MockHttpServletResponse res = PostTestHelper.createPost(
                auth,
                text,
                IMAGE,
                mvc,
                MAPPER,
                WRITER);
        assertEquals(HttpStatus.CREATED.value(), res.getStatus());

        String postId = PostTestHelper.getPostIdFromResponse(res, MAPPER);
        res = PostTestHelper.getPost(postId, mvc, MAPPER, WRITER);
        assertEquals(HttpStatus.OK.value(), res.getStatus());
        Post actual = PostTestHelper.getPostFromResponse(res, MAPPER);
        assertEquals("oldboy", postId);
        assertEquals("oldboy", actual.getId());
        assertEquals("Oldboy (2003)", actual.getTitle());
        assertEquals(
                "<p>In Park Chan-wook's film...</p>",
                actual.getContent());
    }

    @Test
    void willThrowGetPostImageWhenPostDoesNotExist() throws Exception {
        MockHttpServletResponse res = PostTestHelper.getPostImage(
                "oldboy",
                mvc,
                MAPPER,
                WRITER);
        assertEquals(HttpStatus.NOT_FOUND.value(), res.getStatus());
        assertEquals("Post not found", getError(res));
    }

    @Test
    void getPostImage() throws Exception {
        String text = "<h1>Oldboy (2003)</h1>"
                + "<p>In Park Chan-wook's film...</p>";
        MockHttpServletResponse res = PostTestHelper.createPost(
                auth,
                text,
                IMAGE,
                mvc,
                MAPPER,
                WRITER);
        assertEquals(HttpStatus.CREATED.value(), res.getStatus());

        String postId = PostTestHelper.getPostIdFromResponse(res, MAPPER);
        res = PostTestHelper.getPostImage(postId, mvc, MAPPER, WRITER);
        byte[] actual = PostTestHelper.getImageFromResponse(res, MAPPER);
        assertArrayEquals(IMAGE.getBytes(), actual);
    }

    @Test
    void getAllPosts() throws Exception {
        String text = "<h1>Title (1994)</h1>Content";
        MockHttpServletResponse res = PostTestHelper.createPost(
                auth,
                text,
                IMAGE,
                mvc,
                MAPPER,
                WRITER);
        assertEquals(HttpStatus.CREATED.value(), res.getStatus());

        text = "<h1>Oldboy (2003)</h1><p>In Park Chan-wook's film...</p>";
        res = PostTestHelper.createPost(
                auth,
                text,
                IMAGE,
                mvc,
                MAPPER,
                WRITER);
        assertEquals(HttpStatus.CREATED.value(), res.getStatus());

        text = "<h1>It's A Wonderful Life (1946)</h1><p>by Frank Capra.</p>";
        res = PostTestHelper.createPost(
                auth,
                text,
                IMAGE,
                mvc,
                MAPPER,
                WRITER);
        assertEquals(HttpStatus.CREATED.value(), res.getStatus());

        res = PostTestHelper.getAllPosts(mvc, MAPPER, WRITER);
        assertEquals(HttpStatus.OK.value(), res.getStatus());

        List<Post> posts = PostTestHelper.getPostsFromResponse(res, MAPPER);
        assertEquals(
                "its-a-wonderful-life",
                posts.get(0).getId());
        assertEquals(
                "It's A Wonderful Life (1946)",
                posts.get(0).getTitle());
        assertEquals(
                "<p>by Frank Capra.</p>",
                posts.get(0).getContent());

        assertEquals("oldboy", posts.get(1).getId());
        assertEquals("Oldboy (2003)", posts.get(1).getTitle());
        assertEquals(
                "<p>In Park Chan-wook's film...</p>",
                posts.get(1).getContent());

        assertEquals("title", posts.get(2).getId());
        assertEquals("Title (1994)", posts.get(2).getTitle());
        assertEquals("Content", posts.get(2).getContent());
    }

    @Test
    void willThrowUnauthorizedDuringUpdatePostWhenNoAuth() throws Exception {
        String text = "<h1>Oldboy (2003)</h1><p>by Park Chan-wook.</p>";
        MockHttpServletResponse res = PostTestHelper.updatePost(
                "",
                "oldboy",
                text,
                IMAGE,
                mvc,
                MAPPER,
                WRITER);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), res.getStatus());
        assertEquals("Unauthorized", getError(res));
    }

    @Test
    void willThrowNotFoundDuringUpdatePostWhenPostNotFound() throws Exception {
        String text = "<h1>Oldboy (2003)</h1><p>by Park Chan-wook.</p>";
        MockHttpServletResponse res = PostTestHelper.updatePost(
                auth,
                "oldboy",
                text,
                IMAGE,
                mvc,
                MAPPER,
                WRITER);
        assertEquals(HttpStatus.NOT_FOUND.value(), res.getStatus());
        assertEquals("Post not found", getError(res));
    }

    @Test
    void updatePost() throws Exception {
        String text = "<h1>Oldboy (2003)</h1>"
                + "<p>In Park Chan-wook's film...</p>";
        MockHttpServletResponse res = PostTestHelper.createPost(
                auth, text, IMAGE, mvc, MAPPER, WRITER);
        assertEquals(HttpStatus.CREATED.value(), res.getStatus());

        String resJson = res.getContentAsString();
        CreatePostResponse createPostResponse = MAPPER.readValue(
                resJson,
                CreatePostResponse.class);
        String id = createPostResponse.getPostId();

        text = "<h1>Oldboy (2004)</h1><p>by Park Chan-wook.</p>";
        res = PostTestHelper.updatePost(
                auth,
                id,
                text,
                IMAGE,
                mvc,
                MAPPER,
                WRITER);
        resJson = res.getContentAsString();
        assertEquals(HttpStatus.NO_CONTENT.value(), res.getStatus());
        assertEquals("{}", resJson);

        res = PostTestHelper.getPost(id, mvc, MAPPER, WRITER);
        resJson = res.getContentAsString();
        assertEquals(HttpStatus.OK.value(), res.getStatus());

        GetPostResponse getPostResponse = MAPPER.readValue(
                resJson,
                GetPostResponse.class);
        Post actual = getPostResponse.getPost();
        assertEquals(id, actual.getId());
        assertEquals("Oldboy (2004)", actual.getTitle());
        assertEquals("<p>by Park Chan-wook.</p>", actual.getContent());

        res = PostTestHelper.getPostImage(id, mvc, MAPPER, WRITER);
        resJson = res.getContentAsString();
        assertEquals(HttpStatus.OK.value(), res.getStatus());

        GetPostImageResponse getPostImageResponse = MAPPER.readValue(
                resJson,
                GetPostImageResponse.class);

        assertArrayEquals(IMAGE.getBytes(), getPostImageResponse.getImage());
        byte[] imageRes = getPostImageResponse.getImage();

        MockMultipartFile image = new MockMultipartFile(
                "image",
                "image.jpg",
                "image/jpeg",
                "New Hello World!".getBytes());
        res = PostTestHelper.updatePost(
                auth, id, text, image, mvc, MAPPER, WRITER);
        resJson = res.getContentAsString();
        assertEquals(HttpStatus.NO_CONTENT.value(), res.getStatus());
        assertEquals("{}", resJson);

        res = PostTestHelper.getPostImage(id, mvc, MAPPER, WRITER);
        assertEquals(HttpStatus.OK.value(), res.getStatus());
        byte[] newImage = PostTestHelper.getImageFromResponse(res, MAPPER);

        assertNotEquals(imageRes, newImage);
        assertArrayEquals(
                "New Hello World!".getBytes(),
                newImage);
    }

    @Test
    void willThrowUnauthorizedDuringDeletePostWhenNoAuth() throws Exception {
        MockHttpServletResponse res = PostTestHelper.deletePost(
                "",
                "oldboy",
                mvc,
                MAPPER,
                WRITER);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), res.getStatus());
        assertEquals("Unauthorized", getError(res));
    }

    @Test
    void willThrowNotFoundDuringDeletePostWhenPostDoesNotExist()
            throws Exception {
        MockHttpServletResponse res = PostTestHelper
                .deletePost(auth, "oldboy", mvc, MAPPER, WRITER);
        assertEquals(HttpStatus.NOT_FOUND.value(), res.getStatus());
        assertEquals("Post not found", getError(res));
    }

    @Test
    void willDeletePost() throws Exception {
        String text = "<h1>Oldboy (2003)</h1><p>by Park Chan-wook.</p>";
        MockHttpServletResponse res = PostTestHelper
                .createPost(auth, text, IMAGE, mvc, MAPPER, WRITER);
        assertEquals(HttpStatus.CREATED.value(), res.getStatus());

        String postId = PostTestHelper.getPostIdFromResponse(res, MAPPER);
        res = PostTestHelper.getPost(postId, mvc, MAPPER, WRITER);
        Post post = PostTestHelper.getPostFromResponse(res, MAPPER);
        assertEquals(postId, post.getId());
        assertEquals("Oldboy (2003)", post.getTitle());
        assertEquals("<p>by Park Chan-wook.</p>", post.getContent());

        res = PostTestHelper.deletePost(auth, postId, mvc, MAPPER, WRITER);
        String resJson = res.getContentAsString();
        assertEquals(HttpStatus.NO_CONTENT.value(), res.getStatus());
        assertEquals("{}", resJson);

        res = PostTestHelper.getPost(postId, mvc, MAPPER, WRITER);
        assertEquals(HttpStatus.NOT_FOUND.value(), res.getStatus());
        assertEquals("Post not found", getError(res));
    }
}
