package com.example.picshare.service;

import com.example.picshare.domain.Post;
import com.example.picshare.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Null;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllPosts() {
        Post post = new Post(1L, "Title", "Content", "dummy/path/to/image.jpg");
        post.setId(1L);
        post.setTitle("Title");
        post.setContent("Content");
        post.setImageFilePath("dummy/path/to/image.jpg");

        // Mock 객체 설정
        when(postRepository.findAll()).thenReturn(Collections.singletonList(post));

        // 테스트 실행
        List<Post> result = postService.getAllPosts();

        // 결과 검증
        assertEquals(1, result.size());
        assertEquals(post.getTitle(), result.get(0).getTitle());

        // Mock 메서드 호출 검증
        verify(postRepository, times(1)).findAll();
    }
}
