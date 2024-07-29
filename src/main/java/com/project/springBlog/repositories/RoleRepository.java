package com.project.springBlog.repositories;

import com.project.springBlog.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository  extends JpaRepository<RoleModel, Long> {
    Optional<RoleModel> findByRoleName(String roleName);
}
