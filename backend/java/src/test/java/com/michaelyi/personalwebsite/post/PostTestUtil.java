package com.michaelyi.personalwebsite.post;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class PostTestUtil {
    protected static MockHttpServletResponse createPost(
            String auth,
            String text,
            MockMultipartFile image,
            MockMvc mvc,
            ObjectMapper mapper,
            ObjectWriter writer) throws Exception {
        String endpoint = "/v2/post";

        return mvc.perform(multipart(endpoint)
                .file(image)
                .param("text", text)
                .servletPath(endpoint)
                .header(HttpHeaders.AUTHORIZATION, auth))
                .andReturn()
                .getResponse();
    }

    protected static MockHttpServletResponse getPost(
            String id,
            MockMvc mvc,
            ObjectMapper mapper,
            ObjectWriter writer) throws Exception {
        String endpoint = "/v2/post/" + id;

        return mvc.perform(get(endpoint))
                .andReturn()
                .getResponse();
    }

    protected static MockHttpServletResponse getPostImage(
            String id,
            MockMvc mvc,
            ObjectMapper mapper,
            ObjectWriter writer) throws Exception {
        String endpoint = "/v2/post/image/" + id;

        return mvc.perform(get(endpoint))
                .andReturn()
                .getResponse();
    }

    protected static MockHttpServletResponse getAllPosts(
            MockMvc mvc,
            ObjectMapper mapper,
            ObjectWriter writer) throws Exception {
        String endpoint = "/v2/post";

        return mvc.perform(get(endpoint))
                .andReturn()
                .getResponse();
    }

    protected static MockHttpServletResponse updatePost(
            String auth,
            String id,
            String text,
            MockMultipartFile image,
            MockMvc mvc,
            ObjectMapper mapper,
            ObjectWriter writer) throws Exception {
        String endpoint = "/v2/post/" + id;

        return mvc.perform(multipart(HttpMethod.PATCH, endpoint)
                .file(image)
                .param("text", text)
                .servletPath(endpoint)
                .header(HttpHeaders.AUTHORIZATION, auth))
                .andReturn()
                .getResponse();
    }

    protected static MockHttpServletResponse deletePost(
            String auth,
            String id,
            MockMvc mvc,
            ObjectMapper mapper,
            ObjectWriter writer) throws Exception {
        String endpoint = "/v2/post/" + id;

        return mvc.perform(delete(endpoint)
                .servletPath(endpoint)
                .header(HttpHeaders.AUTHORIZATION, auth))
                .andReturn()
                .getResponse();
    }

    protected static String getPostIdFromResponse(
            MockHttpServletResponse response,
            ObjectMapper mapper) throws Exception {
        String json = response.getContentAsString();
        CreatePostResponse res = mapper.readValue(
            json,
            CreatePostResponse.class);
        return res.getPostId();
    }

    protected static Post getPostFromResponse(
            MockHttpServletResponse response,
            ObjectMapper mapper) throws Exception {
        String json = response.getContentAsString();
        GetPostResponse res = mapper.readValue(
            json,
            GetPostResponse.class);
        return res.getPost();
    }

    protected static byte[] getImageFromResponse(
            MockHttpServletResponse response,
            ObjectMapper mapper) throws Exception {
        String json = response.getContentAsString();
        GetPostImageResponse res = mapper.readValue(
            json,
            GetPostImageResponse.class);
        return res.getImage();
    }

    protected static List<Post> getPostsFromResponse(
            MockHttpServletResponse response,
            ObjectMapper mapper) throws Exception {
        String json = response.getContentAsString();
        GetAllPostsResponse res = mapper.readValue(
            json,
            GetAllPostsResponse.class);
        return res.getPosts();
    }
}
