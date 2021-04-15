package com.example.api.graphql.comment;

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

    List<Comment> comments(){
        return commentService.getAllComments();
    }

    Comment commentById(Long commentId){ return commentService.getCommentById(commentId); }

    List<Comment> commentByPostId(Long postId){
        return commentService.getAllCommentsByPostId(postId);
    }
}
