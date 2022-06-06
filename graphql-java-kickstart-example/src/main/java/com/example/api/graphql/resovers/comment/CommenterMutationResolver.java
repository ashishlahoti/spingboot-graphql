package com.example.api.graphql.resovers.comment;

import com.example.api.model.Comment;
import com.example.api.model.Commenter;
import com.example.api.model.CommenterInput;
import com.example.api.service.CommentService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

/**
 * GraphQLMutationResolver to resolve Mutations (Create, Update and Delete Operations). Note the method names and return types.
 */
@Component
@RequiredArgsConstructor
@Slf4j
@Validated
public class CommenterMutationResolver implements GraphQLMutationResolver {

    private final CommentService commentService;

    public Optional<Comment> updateCommenter(@Valid CommenterInput input) {
        log.info("updateCommenter for {}", input);
        return Optional.ofNullable(commentService.getCommentById(input.getCommentId()))
                .map(comment -> {
                    Commenter commenterResult = Optional.ofNullable(comment.getCommenter())
                            .map(commenter -> {
                                commenter.setEmail(input.getEmail());
                                commenter.setName(input.getName());
                                return commenter;
                            })
                            .orElseGet(() -> Commenter.builder().id(1L).email(input.getEmail()).name(input.getName()).build());
                    comment.setCommenter(commenterResult);
                    //log.info("updateCommenter comment: {}", comment);
                    return comment;
                });
    }
}
