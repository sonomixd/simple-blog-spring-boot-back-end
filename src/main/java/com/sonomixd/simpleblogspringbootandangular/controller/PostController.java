package com.sonomixd.simpleblogspringbootandangular.controller;

import com.sonomixd.simpleblogspringbootandangular.model.Category;
import com.sonomixd.simpleblogspringbootandangular.dto.PostDto;
import com.sonomixd.simpleblogspringbootandangular.model.Post;
import com.sonomixd.simpleblogspringbootandangular.repository.PostRepository;
import com.sonomixd.simpleblogspringbootandangular.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;

    @PostMapping("/createPostWithImage")
    public ResponseEntity createPostwithPhoto( @RequestParam("file") MultipartFile file, @RequestParam("title") String title,
    @RequestParam("content") String content, @RequestParam("category") Category category) {
        PostDto postDto = new PostDto() ;
        postDto.setCategory(category);
        postDto.setContent(content);
        postDto.setTitle(title);
        postService.createPostwithPhoto(file,postDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/createPost")
    public ResponseEntity createPost(@Valid @RequestBody PostDto postDto) {
        postService.createPost(postDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> showAllPosts() {
        return new ResponseEntity<>(postService.showAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PostDto> getSinglePost(@PathVariable @RequestBody Long id) {
        return new ResponseEntity<>(postService.readSinglePost(id), HttpStatus.OK);
    }


    @GetMapping("/category/{id}")
    public ResponseEntity<List<Post>> getPostsByCategory(@PathVariable("id") Category category) {
        return new ResponseEntity<>(postService.getByCategory(category), HttpStatus.OK);
    }



}
