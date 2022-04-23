package com.example.api.service;

import com.example.api.model.Post;
import com.example.api.model.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PostService {

    List<Post> getAllPosts();

    Post getPostById(Long postId);

    List<Post> getAllPostsByUserId(Long userId);

    Map<Long, List<Post>> getAllPostsByUserId(Map<Long, User> userIds, String userId);

    Map<Long, List<Post>> getAllPostsByUserId(List<User> users, String userId);

    Map<Long, List<Post>> getAllPostsByUserId(Set<Long> users, String userId);

    Map<Long, List<Post>> getAllPostsByUserId(Set<Long> users);

    Post createPost(Post post);

    void updatePost(Long postId, Post post);

    void deletePost(Long postId);
}
