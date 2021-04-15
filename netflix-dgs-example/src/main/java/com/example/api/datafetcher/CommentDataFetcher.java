package com.example.api.datafetcher;

import com.example.api.model.Comment;
import com.example.api.service.CommentService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DgsComponent
@RequiredArgsConstructor
public class CommentDataFetcher {

    private final CommentService commentService;

    @DgsQuery
    public List<Comment> comments(){
        return commentService.getAllComments();
    }

    @DgsQuery
    public Comment commentById(String id){ return commentService.getCommentById(Long.parseLong(id)); }

    @DgsQuery
    public List<Comment> commentsByPostId(String postId){
        return commentService.getAllCommentsByPostId(Long.parseLong(postId));
    }

}
