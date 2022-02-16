package com.example.api.graphql.file;

import graphql.kickstart.servlet.context.DefaultGraphQLServletContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.Part;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class UploadFileMutationResolver implements GraphQLMutationResolver {

    public UUID uploadFile(DataFetchingEnvironment env){
        DefaultGraphQLServletContext context = env.getContext();
        log.info("Uploading files: {}", context.getFileParts());

        List<Part> fileParts = context.getFileParts();

        for (Part part: fileParts){
            log.info("uploading: {}, size: {}", part.getSubmittedFileName(), part.getSize());
        }

        return UUID.randomUUID();
    }

}
