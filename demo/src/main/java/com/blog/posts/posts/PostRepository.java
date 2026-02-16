package com.blog.posts.posts;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.blog.posts.model.BlogPost;

public interface PostRepository extends MongoRepository<BlogPost, String> {

}