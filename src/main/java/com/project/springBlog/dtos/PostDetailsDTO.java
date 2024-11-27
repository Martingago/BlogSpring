package com.project.springBlog.dtos;

import com.project.springBlog.dtos.user.UserResponseDTO;
import com.project.springBlog.mapper.UserMapper;
import com.project.springBlog.models.UserModel;

import java.util.Date;

public class PostDetailsDTO {
    private Date fechaCreacion;
    private UserResponseDTO creador;

    public PostDetailsDTO(Date fechaCreacion, UserModel creador) {
        this.fechaCreacion = fechaCreacion;
        this.creador = (creador != null) ? UserMapper.toUserResponseDTO(creador) : new UserResponseDTO();
    }

    public PostDetailsDTO(Date fechaCreacion, UserResponseDTO creador) {
        this.fechaCreacion = fechaCreacion;
        this.creador = creador;
    }

    public PostDetailsDTO() {
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public UserResponseDTO getCreador() {
        return creador;
    }

    public void setCreador(UserResponseDTO creador) {
        this.creador = creador;
    }

    public void setCreador(UserModel creador){
        this.creador = UserMapper.toUserResponseDTO(creador);
    }
}
