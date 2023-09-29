package com.cleanarch.blog.persistence.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@Configuration
@EnableMongoRepositories("com.cleanarch.blog.persistence.repository")
public class MongoRepositoryConfig {

}
