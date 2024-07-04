package com.softii.laborappbackend.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Trabajo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idtrabajo;

    @ManyToOne
    @JoinColumn(name = "idcliente")
    private Cliente cliente;

    private String titulo;
    private String descripcion;
    private String categoria;
    private String ubicacion;
    private Date fechaLimite;

    @Enumerated(EnumType.STRING)
    private EstadoTrabajo estado;

    private Double presupuesto;

    @Lob
    private byte[] imagen;

    // Getters y Setters
    public Long getIdtrabajo() {
        return idtrabajo;
    }

    public void setIdtrabajo(Long idtrabajo) {
        this.idtrabajo = idtrabajo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public EstadoTrabajo getEstado() {
        return estado;
    }

    public void setEstado(EstadoTrabajo estado) {
        this.estado = estado;
    }

    public Double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
}
