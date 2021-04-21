package com.example.api.datafetcher;

import com.example.api.model.Comment;
import com.example.api.model.Post;
import com.example.api.service.CommentService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsQuery;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@DgsComponent
@RequiredArgsConstructor
public class CommentDataFetcher {

    private final CommentService commentService;

    @DgsQuery
    public List<Comment> comments() {
        return commentService.getAllComments();
    }

    @DgsQuery
    public Comment commentById(String id) {
        return commentService.getCommentById(Long.parseLong(id));
    }

    @DgsQuery
    public List<Comment> commentsByPostId(String postId) {
        return commentService.getAllCommentsByPostId(Long.parseLong(postId));
    }

    @DgsData(parentType = "Post")
    public CompletableFuture<List<Comment>> comments(DgsDataFetchingEnvironment dfe) {
        Post post = dfe.getSource();
        return CompletableFuture.supplyAsync(() -> commentService.getAllCommentsByPostId(post.getId()));
    }
}
