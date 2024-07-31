package com.project.springBlog.repositories;

import com.project.springBlog.models.CommentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository  extends JpaRepository<CommentModel, Long> {
}
