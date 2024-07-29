package com.project.springBlog.dtos;

public class RoleDTO {

    private long id;
    private String rol;

    public RoleDTO(long id, String rol) {
        this.rol = rol;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
