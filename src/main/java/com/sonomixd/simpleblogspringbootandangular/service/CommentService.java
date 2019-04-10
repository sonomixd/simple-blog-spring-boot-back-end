package com.sonomixd.simpleblogspringbootandangular.service;

import com.sonomixd.simpleblogspringbootandangular.dto.CommentDto;
import com.sonomixd.simpleblogspringbootandangular.dto.PostDto;
import com.sonomixd.simpleblogspringbootandangular.exception.ResourceNotFoundException;
import com.sonomixd.simpleblogspringbootandangular.model.Comment;
import com.sonomixd.simpleblogspringbootandangular.model.Post;
import com.sonomixd.simpleblogspringbootandangular.repository.CommentRepository;
import com.sonomixd.simpleblogspringbootandangular.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private AuthService authService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    public List<CommentDto> getAllCommentsByPostId( Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(this::mapFromCommentToDto).collect(Collectors.toList());
    }

    public Comment createCommentNew( Long postId,CommentDto commentDto) {
        return postRepository.findById(postId).map(post -> {
            commentDto.setPost(post);
            Comment comment = mapFromDtoToComment(commentDto);
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    }

    public  Comment updateComment(Long postId, Long commentId, CommentDto commentDto) {
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("PostId " + postId + " not found");
        }

        return commentRepository.findById(commentId).map(comment -> {
            comment.setText(commentDto.getText());
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("CommentId " + commentId + "not found"));

    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(Long postId, Long commentId) {
        return commentRepository.findByIdAndPostId(commentId, postId).map(comment -> {
            commentRepository.delete(comment);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + commentId + " and postId " + postId));
    }






    private CommentDto mapFromCommentToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        commentDto.setPost(comment.getPost());
        return commentDto;
    }

    private Comment mapFromDtoToComment(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setText(commentDto.getText());
        comment.setPost(commentDto.getPost());

        return comment;
    }
}
