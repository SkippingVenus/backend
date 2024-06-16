package com.softii.laborappbackend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "freelancer")
public class Freelancer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idfreelancer;

    @OneToOne
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuario usuario;

    private Float calificacion;
    @Column(length = 1000)
    private String descripcion;
    @Column(length = 1000)
    private String habilidades;

    // Constructor vacío
    public Freelancer() {}

    // Constructor con parámetros
    public Freelancer(Usuario usuario, Float calificacion, String descripcion, String habilidades) {
        this.usuario = usuario;
        this.calificacion = calificacion;
        this.descripcion = descripcion;
        this.habilidades = habilidades;
    }

    // Getters
    public Long getIdfreelancer() {
        return idfreelancer;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Float getCalificacion() {
        return calificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getHabilidades() {
        return habilidades;
    }

    // Setters
    public void setIdfreelancer(Long idfreelancer) {
        this.idfreelancer = idfreelancer;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setCalificacion(Float calificacion) {
        this.calificacion = calificacion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setHabilidades(String habilidades) {
        this.habilidades = habilidades;
    }
}
