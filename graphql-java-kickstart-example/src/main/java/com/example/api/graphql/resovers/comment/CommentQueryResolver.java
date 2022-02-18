package com.example.api.graphql.resovers.comment;

import com.example.api.model.Comment;
import com.example.api.service.CommentService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentQueryResolver implements GraphQLQueryResolver {

    private final CommentService commentService;

    List<Comment> getComments() {
        return commentService.getAllComments();
    }

    Comment getCommentById(Long commentId) {
        return commentService.getCommentById(commentId);
    }

    List<Comment> getCommentByPostId(Long postId) {
        return commentService.getAllCommentsByPostId(postId);
    }
}
