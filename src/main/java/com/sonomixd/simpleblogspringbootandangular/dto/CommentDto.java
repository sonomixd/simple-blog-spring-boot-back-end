package com.sonomixd.simpleblogspringbootandangular.dto;


import com.sonomixd.simpleblogspringbootandangular.model.Post;

public class CommentDto {

    private Long id;
    private String text;
    private Post post;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
