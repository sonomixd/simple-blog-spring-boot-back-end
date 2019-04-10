package com.sonomixd.simpleblogspringbootandangular.repository;

import com.sonomixd.simpleblogspringbootandangular.model.Category;
import com.sonomixd.simpleblogspringbootandangular.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByCategory(Category category);
}
