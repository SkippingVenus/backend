package com.softii.laborappbackend.dto;

public class FreelancerCreationDTO {
    private Long idusuario;
    private Float calificacion;
    private String descripcion;
    private String habilidades;

    // Constructor vacío
    public FreelancerCreationDTO() { }

    // Constructor con parámetros
    public FreelancerCreationDTO(Long idusuario, Float calificacion, String descripcion, String habilidades) {
        this.idusuario = idusuario;
        this.calificacion = calificacion;
        this.descripcion = descripcion;
        this.habilidades = habilidades;
    }

    // Getters y setters

    public Long getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Long idusuario) {
        this.idusuario = idusuario;
    }

    public Float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Float calificacion) {
        this.calificacion = calificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(String habilidades) {
        this.habilidades = habilidades;
    }
}
