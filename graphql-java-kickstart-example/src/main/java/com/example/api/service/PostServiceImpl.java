package com.example.api.service;

import com.example.api.client.PostFeignClient;
import com.example.api.model.Post;
import com.example.api.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostFeignClient postFeignClient;

    @Override
    public List<Post> getAllPosts() {
        return postFeignClient.getAllPosts();
    }

    @Override
    public Post getPostById(Long postId) {
        return postFeignClient.getPostById(postId);
    }

    @Override
    public List<Post> getAllPostsByUserId(Long userId) {
        return postFeignClient.getPostByUserId(userId);
    }

    @Override
    public Post createPost(Post post) {
        return postFeignClient.createPost(post);
    }

    @Override
    public void updatePost(Long postId, Post post) {
        postFeignClient.updatePost(post);
    }

    @Override
    public void deletePost(Long postId) {
        postFeignClient.deletePost(postId);
    }

    @Override
    public Map<Long, List<Post>> getAllPostsByUserId(Map<Long, User> userIds, String userId) {
        log.info("getAllPostsByUserId - Requesting batch post of userIds: {} for user Id: {}", userIds, userId);
        Map<Long, List<Post>> result = new HashMap<>();

        // Original set of ids is available via
        var ids = userIds.keySet();
        log.info("userIds: {}", ids);
        userIds.forEach((key, value) -> result.put(key, postFeignClient.getPostByUserId(key)));
        return result;
    }

    @Override
    public Map<Long, List<Post>> getAllPostsByUserId(List<User> users, String userId) {
        log.info("getAllPostsByUserId - Requesting batch post of userIds: {} for user Id: {}", users, userId);
        Map<Long, List<Post>> result = new HashMap<>();
        users.forEach(user -> result.put(user.getId(), postFeignClient.getPostByUserId(user.getId())));
        return result;
    }

    @Override
    public Map<Long, List<Post>> getAllPostsByUserId(Set<Long> users, String userId) {
        log.info("getAllPostsByUserId - Requesting batch post of userIds: {} for user Id: {}", users, userId);
        Map<Long, List<Post>> result = new HashMap<>();
        users.forEach(user -> result.put(user, postFeignClient.getPostByUserId(user)));
        return result;
    }

    @Override
    public Map<Long, List<Post>> getAllPostsByUserId(Set<Long> users) {
        log.info("getAllPostsByUserId - Requesting batch post of userIds: {} for user Id: {}", users);
        Map<Long, List<Post>> result = new HashMap<>();
        users.forEach(user -> result.put(user, postFeignClient.getPostByUserId(user)));
        return result;
    }
}
