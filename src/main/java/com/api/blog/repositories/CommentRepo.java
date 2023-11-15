package com.api.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
