package com.softii.laborappbackend.dto;

import com.softii.laborappbackend.entities.Trabajo;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;

public class TrabajoDTO {
    private Long idtrabajo;
    private String titulo;
    private String descripcion;
    private String categoria;
    private String ubicacion;
    private Date fechaLimite;
    private String estado;
    private Long idcliente;
    private String imagenUrl;

    public TrabajoDTO() {
        // Constructor vacío
    }

    public TrabajoDTO(Trabajo trabajo) {
        this.idtrabajo = trabajo.getIdtrabajo();
        this.titulo = trabajo.getTitulo();
        this.descripcion = trabajo.getDescripcion();
        this.categoria = trabajo.getCategoria();
        this.ubicacion = trabajo.getUbicacion();
        this.fechaLimite = trabajo.getFechaLimite();
        this.estado = trabajo.getEstado().name();
        this.idcliente = trabajo.getCliente().getIdcliente();
        this.imagenUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/trabajos/")
                .path(trabajo.getIdtrabajo().toString())
                .path("/imagen")
                .toUriString();
    }

    // Getters y Setters
    public Long getIdtrabajo() {
        return idtrabajo;
    }

    public void setIdtrabajo(Long idtrabajo) {
        this.idtrabajo = idtrabajo;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Long idcliente) {
        this.idcliente = idcliente;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }
}
