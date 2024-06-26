package com.softii.laborappbackend.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "trabajo")
public class Trabajo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idtrabajo;

    @ManyToOne
    @JoinColumn(name = "idcliente", referencedColumnName = "idcliente")
    private Cliente cliente;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, length = 1000)
    private String descripcion;

    private Float presupuesto;

    @Temporal(TemporalType.DATE)
    private Date fechaLimite;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoTrabajo estado;

    private String categoria;
    private String ubicacion;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    // Constructor vacío
    public Trabajo() {}

    // Constructor con parámetros
    public Trabajo(Cliente cliente, String titulo, String descripcion, Float presupuesto, Date fechaLimite, EstadoTrabajo estado, String categoria, String ubicacion, byte[] imagen) {
        this.cliente = cliente;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.presupuesto = presupuesto;
        this.fechaLimite = fechaLimite;
        this.estado = estado;
        this.categoria = categoria;
        this.ubicacion = ubicacion;
        this.imagen = imagen;
    }

    // Getters
    public Long getIdtrabajo() {
        return idtrabajo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Float getPresupuesto() {
        return presupuesto;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public EstadoTrabajo getEstado() {
        return estado;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public byte[] getImagen() {
        return imagen;
    }

    // Setters
    public void setIdtrabajo(Long idtrabajo) {
        this.idtrabajo = idtrabajo;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPresupuesto(Float presupuesto) {
        this.presupuesto = presupuesto;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public void setEstado(EstadoTrabajo estado) {
        this.estado = estado;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }


}

