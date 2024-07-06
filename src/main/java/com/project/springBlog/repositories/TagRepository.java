package com.project.springBlog.repositories;

import com.project.springBlog.models.TagModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository  extends CrudRepository<TagModel, Long> {
}
