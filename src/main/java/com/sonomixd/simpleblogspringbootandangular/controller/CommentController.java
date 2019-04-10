package com.sonomixd.simpleblogspringbootandangular.controller;


import com.sonomixd.simpleblogspringbootandangular.dto.CommentDto;
import com.sonomixd.simpleblogspringbootandangular.exception.ResourceNotFoundException;
import com.sonomixd.simpleblogspringbootandangular.model.Comment;
import com.sonomixd.simpleblogspringbootandangular.repository.CommentRepository;
import com.sonomixd.simpleblogspringbootandangular.repository.PostRepository;
import com.sonomixd.simpleblogspringbootandangular.service.CommentService;
import com.sonomixd.simpleblogspringbootandangular.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;


@RestController
public class CommentController {



    @Autowired
    private CommentService commentService;

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getAllCommentsByPostId(@PathVariable (value = "postId") Long postId) {
        return commentService.getAllCommentsByPostId(postId);
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity createComment(@PathVariable (value = "postId") Long postId,
                                 @Valid @RequestBody CommentDto commentDto) {
         commentService.createCommentNew(postId, commentDto);
         return new ResponseEntity<>("Comment added successfully",HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity updateComment(@PathVariable (value = "postId") Long postId,
                                        @PathVariable (value = "commentId") Long commentId,
                                        @Valid @RequestBody CommentDto commentDto) {
        commentService.updateComment(postId, commentId, commentDto);

        return new ResponseEntity<>("Comment updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable (value = "postId") Long postId,
                                           @PathVariable (value = "commentId") Long commentId) {
        commentService.deleteComment(postId, commentId);

        return new ResponseEntity<>("Commment deleted successfully", HttpStatus.OK);
    }
}
