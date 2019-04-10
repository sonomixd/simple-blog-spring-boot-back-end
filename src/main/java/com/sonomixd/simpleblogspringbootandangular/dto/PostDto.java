package com.sonomixd.simpleblogspringbootandangular.dto;

import com.sonomixd.simpleblogspringbootandangular.model.Category;
import com.sonomixd.simpleblogspringbootandangular.model.Comment;

import java.util.Set;

public class PostDto {

     private Long id;
     private String content;
     private String title;
     private String username;
     private Category category;
     private String photo;
     private Set<Comment> comment;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Comment> getComment() {
        return comment;
    }

    public void setComment(Set<Comment> comment) {
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
