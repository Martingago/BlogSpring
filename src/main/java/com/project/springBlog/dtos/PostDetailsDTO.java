package com.project.springBlog.dtos;

import com.project.springBlog.models.UserModel;

import java.util.Date;

public class PostDetailsDTO {
    private Date fechaCreacion;
    private UserModel creador;


    public PostDetailsDTO(Date fechaCreacion, UserModel creador) {
        this.fechaCreacion = fechaCreacion;
        this.creador = creador;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public UserModel getCreador() {
        return creador;
    }

    public void setCreador(UserModel creador) {
        this.creador = creador;
    }
}
