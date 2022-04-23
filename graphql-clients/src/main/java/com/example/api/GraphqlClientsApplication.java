package com.example.api;

import com.example.api.webclient.MyGraphQLWebClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@Slf4j
public class GraphqlClientsApplication implements CommandLineRunner {

    @Autowired
    MyGraphQLWebClient myGraphQLWebClient;

    private static ConfigurableApplicationContext ctx = null;

    public static void main(String[] args) {
        ctx = SpringApplication.run(GraphqlClientsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("run ----");
        myGraphQLWebClient.getUserById(1L);
        //SpringApplication.exit(ctx, () -> 0);
        System.exit(0);
    }
}
