package com.project.springBlog.repositories;

import com.project.springBlog.models.PostDetailsModel;
import org.springframework.data.repository.CrudRepository;

public interface PostDetailsRepository  extends CrudRepository<PostDetailsModel, Long> {}
