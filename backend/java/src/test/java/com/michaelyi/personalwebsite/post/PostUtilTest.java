package com.michaelyi.personalwebsite.post;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
public class PostUtilTest {
    @Test
    void willThrowConstructPostWhenTextHasNoTitle() {
        // given
        String text1 = "<p>Content...</p>";
        String text2 = "<h1><p>Content...</p>";
        String text3 = "</h1><p>Content...</p>";
        String text4 = "<h1></h1><p>Content...</p>";

        // when
        IllegalArgumentException err1 = assertThrows(
                IllegalArgumentException.class,
                () -> PostUtil.constructPost(text1));
        IllegalArgumentException err2 = assertThrows(
                IllegalArgumentException.class,
                () -> PostUtil.constructPost(text2));
        IllegalArgumentException err3 = assertThrows(
                IllegalArgumentException.class,
                () -> PostUtil.constructPost(text3));
        IllegalArgumentException err4 = assertThrows(
                IllegalArgumentException.class,
                () -> PostUtil.constructPost(text4));

        // then
        assertEquals("Title cannot be blank", err1.getMessage());
        assertEquals("Title cannot be blank", err2.getMessage());
        assertEquals("Title cannot be blank", err3.getMessage());
        assertEquals("Title cannot be blank", err4.getMessage());
    }

    @Test
    void willThrowConstructPostWhenTextHasNoContent() {
        // given
        String text = "<h1>Oldboy (2003)</h1>";

        // when
        IllegalArgumentException err = assertThrows(
                IllegalArgumentException.class,
                () -> PostUtil.constructPost(text));

        // then
        assertEquals("Content cannot be blank", err.getMessage());
    }

    @Test
    void canConstructPost() {
        // given
        String text = "<h1>Eternal Sunshine of the Spotless Mind (2004)</h1>"
                + "<p>In Michel Gondry's romance/sci-fi film...</p>";

        // when
        Post actual = PostUtil.constructPost(text);

        // then
        Post expected = new Post(
                "eternal-sunshine-of-the-spotless-mind",
                actual.getDate(),
                "Eternal Sunshine of the Spotless Mind (2004)",
                null,
                "<p>In Michel Gondry's romance/sci-fi film...</p>");
        assertEquals(expected, actual);
    }

    @Test
    void willThrowGetImageWhenMultipartFileIsNull() {
        // given
        MultipartFile image = null;

        // when
        IllegalArgumentException err = assertThrows(
                IllegalArgumentException.class,
                () -> PostUtil.getImage(image));

        // then
        assertEquals("Image is invalid", err.getMessage());
    }

    @Test
    void willThrowGetImageWhenMultipartFileIsEmpty() {
        // given
        MultipartFile image = mock(MultipartFile.class);
        when(image.isEmpty()).thenReturn(true);

        // when
        IllegalArgumentException err = assertThrows(
                IllegalArgumentException.class,
                () -> PostUtil.getImage(image));

        // then
        assertEquals("Image is invalid", err.getMessage());
    }

    @Test
    void willThrowGetImageWhenMultipartFileHasSizeZero() {
        // given
        MultipartFile image = mock(MultipartFile.class);
        when(image.isEmpty()).thenReturn(false);
        when(image.getSize()).thenReturn(0L);

        // when
        IllegalArgumentException err = assertThrows(
                IllegalArgumentException.class,
                () -> PostUtil.getImage(image));

        // then
        assertEquals("Image is invalid", err.getMessage());
    }

    @Test
    void willThrowGetImageWhenMultipartFileHasInvalidFileExtension() {
        // given
        MultipartFile image = mock(MultipartFile.class);
        when(image.isEmpty()).thenReturn(false);
        when(image.getSize()).thenReturn(1L);
        when(image.getOriginalFilename()).thenReturn("Hello, World!.pdf");

        // when
        IllegalArgumentException err = assertThrows(
                IllegalArgumentException.class,
                () -> PostUtil.getImage(image));

        // then
        assertEquals("Image is invalid", err.getMessage());
    }

    @Test
    void willThrowGetImageWhenMultipartFileHasInvalidContentType() {
        // given
        MultipartFile image = mock(MultipartFile.class);
        when(image.isEmpty()).thenReturn(false);
        when(image.getSize()).thenReturn(1L);
        when(image.getOriginalFilename()).thenReturn("Hello, World!.jpg");
        when(image.getContentType()).thenReturn("application/pdf");

        // when
        IllegalArgumentException err = assertThrows(
                IllegalArgumentException.class,
                () -> PostUtil.getImage(image));

        // then
        assertEquals("Image is invalid", err.getMessage());
    }

    @Test
    void canGetImage() throws Exception {
        // given
        MultipartFile jpgImage = new MockMultipartFile(
                "Hello, World!",
                "Hello, World!.jpg",
                "image/jpeg",
                "Hello, World!.jpg".getBytes());
        MultipartFile jpegImage = new MockMultipartFile(
                "Hello, World!",
                "Hello, World!.jpeg",
                "image/jpeg",
                "Hello, World!.jpeg".getBytes());
        MultipartFile pngImage = new MockMultipartFile(
                "Hello, World!",
                "Hello, World!.png",
                "image/png",
                "Hello, World!.png".getBytes());
        MultipartFile webpImage = new MockMultipartFile(
                "Hello, World!",
                "Hello, World!.webp",
                "image/webp",
                "Hello, World!.webp".getBytes());

        // when
        byte[] actualJpgImage = PostUtil.getImage(jpgImage);
        byte[] actualJpegImage = PostUtil.getImage(jpegImage);
        byte[] actualPngImage = PostUtil.getImage(pngImage);
        byte[] actualWebpImage = PostUtil.getImage(webpImage);

        // then
        assertArrayEquals("Hello, World!.jpg".getBytes(), actualJpgImage);
        assertArrayEquals("Hello, World!.jpeg".getBytes(), actualJpegImage);
        assertArrayEquals("Hello, World!.png".getBytes(), actualPngImage);
        assertArrayEquals("Hello, World!.webp".getBytes(), actualWebpImage);
    }
}
