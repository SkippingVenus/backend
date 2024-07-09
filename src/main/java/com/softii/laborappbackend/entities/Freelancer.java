package com.softii.laborappbackend.entities;

import jakarta.persistence.*;

@Entity
public class Freelancer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idfreelancer;

    private Float calificacion;
    private String descripcion;
    private String habilidades;

    @OneToOne
    @JoinColumn(name = "idusuario")
    private Usuario usuario;

    public Freelancer() {
    }

    public Freelancer(Long idfreelancer) {
        this.idfreelancer = idfreelancer;
    }

    // Getters y Setters
    public Long getIdfreelancer() {
        return idfreelancer;
    }

    public void setIdfreelancer(Long idfreelancer) {
        this.idfreelancer = idfreelancer;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
