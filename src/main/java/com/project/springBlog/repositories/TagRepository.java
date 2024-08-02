package com.project.springBlog.repositories;

import com.project.springBlog.models.TagModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository  extends JpaRepository<TagModel, Long> {
}
