package com.cleanarch.blog.persistence.repository;

import com.cleanarch.blog.persistence.entity.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
interface PostDataRepository extends MongoRepository<Post, String> {

}
