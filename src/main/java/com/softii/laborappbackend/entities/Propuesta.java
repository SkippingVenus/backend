package com.softii.laborappbackend.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "propuesta")
public class Propuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idpropuesta;

    @ManyToOne
    @JoinColumn(name = "idtrabajo", nullable = false)
    private Trabajo trabajo;

    @ManyToOne
    @JoinColumn(name = "idfreelancer", nullable = false)
    private Freelancer freelancer;

    private Float monto;

    @Column(length = 1000)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPropuesta estado;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    // Constructor vacío (requerido por JPA)
    public Propuesta() {}

    // Constructor con parámetros (puedes personalizarlo según tus necesidades)
    public Propuesta(Trabajo trabajo, Freelancer freelancer, Float monto, String descripcion, EstadoPropuesta estado, Date fechaCreacion) {
        this.trabajo = trabajo;
        this.freelancer = freelancer;
        this.monto = monto;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
    }

    // Getters
    public Long getIdpropuesta() {
        return idpropuesta;
    }

    public Trabajo getTrabajo() {
        return trabajo;
    }

    public Freelancer getFreelancer() {
        return freelancer;
    }

    public Float getMonto() {
        return monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public EstadoPropuesta getEstado() {
        return estado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    // Setters
    public void setIdpropuesta(Long idpropuesta) {
        this.idpropuesta = idpropuesta;
    }

    public void setTrabajo(Trabajo trabajo) {
        this.trabajo = trabajo;
    }

    public void setFreelancer(Freelancer freelancer) {
        this.freelancer = freelancer;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setEstado(EstadoPropuesta estado) {
        this.estado = estado;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
