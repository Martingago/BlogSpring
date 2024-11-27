package com.project.springBlog.mapper;

import com.project.springBlog.dtos.UserDTO;
import com.project.springBlog.dtos.user.UserResponseDTO;
import com.project.springBlog.models.UserModel;

public class UserMapper {

    /**
     * Crea un UserDTO simple que envia al front la información de: username + roles(STRING) + nombre;
     * @param usuario
     * @return UserDTO simple
     */
    public static UserDTO toDTO(UserModel usuario){
        UserDTO dto = new UserDTO();
        dto.setId(usuario.getId());
        dto.setUsername(usuario.getUsername());
        dto.setName(usuario.getName());
        dto.setUserRoles(usuario.getRoles());
        return dto;
    }

    /**
     * Convierte un UserModel en un UserResponseDTO para enviar al front con los datos de un usuario del servidor
     * @param usuario
     * @return UserResponseDTO
     */
    public static UserResponseDTO toUserResponseDTO(UserModel usuario){
        return new UserResponseDTO(usuario.getId(), usuario.getUsername(), usuario.getName(), usuario.getRoles());
    }

    public static UserDTO toDetailDTO(UserModel usuario){
        UserDTO dto = new UserDTO(usuario.getUsername(),usuario.getRoles(), usuario.getName(), usuario.getPublicaciones());
        return dto;
    }
}
