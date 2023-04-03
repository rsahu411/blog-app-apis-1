package com.codewithsahu.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithsahu.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
