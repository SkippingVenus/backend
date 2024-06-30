package com.softii.laborappbackend.dto;

public class ClienteCreationDTO {
    private Long idusuario;
    private String nombre;

    // Getters y setters
    public Long getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Long idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
