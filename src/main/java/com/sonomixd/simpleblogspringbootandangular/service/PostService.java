package com.sonomixd.simpleblogspringbootandangular.service;

import com.sonomixd.simpleblogspringbootandangular.model.Category;
import com.sonomixd.simpleblogspringbootandangular.dto.PostDto;
import com.sonomixd.simpleblogspringbootandangular.exception.PostNotFoundException;
import com.sonomixd.simpleblogspringbootandangular.model.Post;
import com.sonomixd.simpleblogspringbootandangular.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private AuthService authService;

    @Autowired
    private PostRepository postRepository;

    public List<PostDto> showAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapFromPostToDto).collect(Collectors.toList());
    }

    public void createPostwithPhoto(MultipartFile file,@Valid PostDto postDto) {
        postDto = addImage(file,postDto) ;
        Post post = mapFromDtoToPost(postDto);
        postRepository.save(post);
    }

    public void createPost(@Valid PostDto postDto) {
        Post post = mapFromDtoToPost(postDto);
        postRepository.save(post);
    }


    public void updatePost(@Valid PostDto postDto) {
        Optional<Post> postOptional = postRepository.findById(postDto.getId());
        if (postOptional.isPresent()) {
            Post existingPost = postOptional.get();
            mapFromPostToDto(existingPost);
        } else {
            throw new PostNotFoundException("For id " + postDto.getId());
        }
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }


    public PostDto addImage(MultipartFile file, PostDto postDto) {
        if (!file.isEmpty()) {

            try {
                byte[] bytes = file.getBytes();
                String rootPath = Paths.get("").toAbsolutePath().toString() + File.separator + "src" + File.separator + "main"
                        + File.separator + "resources";
                File dir = new File(rootPath + File.separator + "posts");
                if (!dir.exists())
                    dir.mkdirs();
                String fileName = new Date().getTime() + file.getOriginalFilename();
                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
                postDto.setPhoto(fileName);
                return postDto;

            } catch (Exception e) {

            }
        }
        return postDto;
    }

    private PostDto mapFromPostToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setUsername(post.getUsername());
        postDto.setCategory(post.getCategory());
        postDto.setComment(post.getComments());
        postDto.setPhoto(post.getPhoto());

        return postDto;
    }


    private Post mapFromDtoToPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        post.setCreatedOn(Instant.now());
        post.setUsername(loggedInUser.getUsername());
        post.setUpdatedOn(Instant.now());
        post.setCategory(postDto.getCategory());
        post.setComments(postDto.getComment());
        post.setPhoto(postDto.getPhoto());

        return post;
    }

    public List<Post> getByCategory(Category category) {
        return postRepository.findAllByCategory(category);
    }


    public PostDto readSinglePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
        return mapFromPostToDto(post);
    }
}
