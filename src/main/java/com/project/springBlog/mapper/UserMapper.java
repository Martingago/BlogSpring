package com.project.springBlog.mapper;

import com.project.springBlog.dtos.UserDTO;
import com.project.springBlog.models.UserModel;

public class UserMapper {

    /**
     * Crea un UserDTO simple que envia al front la información de: username + roles(STRING) + nombre;
     * @param usuario
     * @return UserDTO simple
     */
    public static UserDTO toSimpleDTO(UserModel usuario){
        UserDTO dto = new UserDTO();
        dto.setUsername(usuario.getUsername());
        dto.setName(usuario.getName());
        dto.setUserRoles(usuario.getRoles());

        return dto;
    }
}
