package com.project.springBlog.repositories;

import com.project.springBlog.models.PostModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository <PostModel, Long>{}