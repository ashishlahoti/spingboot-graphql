package com.example.api.service;

import com.example.api.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getAllComments();

    Comment getCommentById(Long id);

    List<Comment> getAllCommentsByPostId(Long commentId);

    Comment createComment(Comment comment);

    void updateComment(Long commentId, Comment comment);

    void deleteComment(Long commentId);

}
