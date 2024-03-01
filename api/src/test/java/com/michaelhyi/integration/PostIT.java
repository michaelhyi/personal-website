// package com.michaelhyi.integration;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
// import org.springframework.test.context.TestPropertySource;
// import org.springframework.test.web.servlet.MockMvc;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.databind.ObjectWriter;
// import com.michaelhyi.dao.PostRepository;
// import com.michaelhyi.dto.PostRequest;
// import com.michaelhyi.entity.Post;

// @SpringBootTest
// @AutoConfigureMockMvc
// @TestPropertySource("classpath:application-it.properties")
// class PostIT {
//     @Autowired
//     private MockMvc mvc;

//     @Autowired
//     private PostRepository repository;

//     @BeforeEach
//     void setUp() {
//         repository.deleteAll();
//     }

//     @AfterEach
//     void tearDown() {
//         repository.deleteAll();
//     }

//     //TODO: complete
//     @Test
//     void createPost() throws Exception {
//         String token = mvc.perform(post("/v1/auth/test@mail.com")
//                                     .accept(MediaType.APPLICATION_JSON))
//                         .andExpect(status().isOk())
//                         .andReturn()
//                         .getResponse()
//                         .getContentAsString();

//         PostRequest req = new PostRequest("<h1>Already Exists</h1>content");
//         Post alreadyExists = new Post(req);

//         repository.save(alreadyExists);

//         ObjectMapper mapper = new ObjectMapper();
//         ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

//         String error = mvc.perform(post("/v1/post")
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .content(ow.writeValueAsString(req))
//                         .header("Authorization", "Bearer " + token))
//             .andExpect(status().isBadRequest())
//             .andReturn().getResolvedException().getMessage();

//         assertEquals(error, "A post with the same title already exists.");

//         req = new PostRequest("<h1>Title</h1>Content");

//         String res = mvc.perform(post("/v1/post")
//                                     .contentType(MediaType.APPLICATION_JSON)
//                                     .content(ow.writeValueAsString(req))
//                                     .header("Authorization", "Bearer " + token))
//                         .andExpect(status().isOk())
//                         .andReturn()
//                         .getResponse()
//                         .getContentAsString();

//         error = mvc.perform(post("/v1/post")
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .content(ow.writeValueAsString(req))
//                         .header("Authorization", "Bearer " + token))
//             .andExpect(status().isBadRequest())
//             .andReturn().getResolvedException().getMessage();

//         assertEquals(error, "A post with the same title already exists.");
//     }

//     //TODO: check if no such key
//     //TODO: works otherwise
//     @Test
//     void createPostImage() throws Exception {
//         String error = mvc.perform(post("/v1/post/does-not-exist/image")
//                                     .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
//                         .andExpect(status().isNotFound())
//                         .andReturn()
//                         .getResolvedException()
//                         .getMessage();

//         assertEquals("Post not found.", error);
//     }
// }
