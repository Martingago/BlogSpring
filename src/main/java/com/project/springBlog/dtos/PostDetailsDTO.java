package com.project.springBlog.dtos;

import com.project.springBlog.mapper.UserMapper;
import com.project.springBlog.models.UserModel;

import java.util.Date;

public class PostDetailsDTO {
    private Date fechaCreacion;
    private UserDTO creador;

    public PostDetailsDTO(Date fechaCreacion, UserModel creador) {
        this.fechaCreacion = fechaCreacion;
        this.creador = (creador != null) ? UserMapper.toDTO(creador) : new UserDTO();
    }

    public PostDetailsDTO(Date fechaCreacion, UserDTO creador) {
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

    public UserDTO getCreador() {
        return creador;
    }

    public void setCreador(UserDTO creador) {
        this.creador = creador;
    }

    public void setCreador(UserModel creador){
        this.creador = UserMapper.toDTO(creador);
    }
}
