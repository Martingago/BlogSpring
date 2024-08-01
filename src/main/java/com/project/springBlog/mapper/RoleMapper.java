package com.project.springBlog.mapper;

import com.project.springBlog.dtos.RoleDTO;
import com.project.springBlog.models.RoleModel;

public class RoleMapper {

    /**
     * Convierte un objeto rol en un rolDTO
     * @param rol
     * @return
     */
    public static RoleDTO toDTO(RoleModel rol){
        return new RoleDTO(rol.getId(), rol.getRoleName());
    }
}
