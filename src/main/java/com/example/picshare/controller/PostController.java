package com.example.picshare.controller;

import com.example.picshare.domain.Post;
import com.example.picshare.dto.PostDto;
import com.example.picshare.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public String getAllPosts(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "post-list"; // post-list.html로 렌더링
    }

    @GetMapping("/{id}")
    public String getPostById(@PathVariable Long id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "post-details"; // post-details.html로 렌더링
    }

    @PostMapping
    public String createPost(@ModelAttribute("post") PostDto postDto) {
        // PostDto를 Post로 변환
        Post post = convertToPost(postDto);
        postService.createPost(post);
        return "redirect:/posts";
    }

    private Post convertToPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        // 이미지 등의 필드도 필요에 따라 설정
        return post;
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("post", new PostDto());
        return "post-form"; // post-form.html로 렌더링
    }

}