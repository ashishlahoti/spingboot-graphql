package com.example.api.datafetcher;

import com.example.api.model.Comment;
import com.example.api.model.Post;
import com.example.api.service.CommentService;
import com.example.api.service.PostService;
import com.netflix.graphql.dgs.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DgsComponent
@RequiredArgsConstructor
public class PostDataFetcher {

    private final PostService postService;
    private final CommentService commentService;

    @DgsQuery
    public List<Post> posts(){
        return postService.getAllPosts();
    }

    @DgsQuery
    public Post postById(String id){
        return postService.getPostById(Long.parseLong(id));
    }

    @DgsQuery
    public List<Post> postsByUserId(String userId){
        return postService.getAllPostsByUserId(Long.parseLong(userId));
    }

    @DgsData(parentType = "Post")
    public List<Comment> comments(DgsDataFetchingEnvironment dfe) {
        Post post = dfe.getSource();
        return commentService.getAllCommentsByPostId(post.getId());
    }

}
