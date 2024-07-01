package com.softii.laborappbackend.dto;

import com.softii.laborappbackend.entities.Trabajo;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class TrabajoDTO {
    private Long idtrabajo;
    private String titulo;
    private String descripcion;
    private Double presupuesto;
    private String fechaLimite;
    private String estado;
    private String categoria;
    private String ubicacion;
    private String imagenUrl;
    private Long idcliente;
    private String nombreCliente;
    private Long idusuario;  // Añadir idusuario

    public TrabajoDTO() {
    }

    public TrabajoDTO(Trabajo trabajo) {
        this.idtrabajo = trabajo.getIdtrabajo();
        this.titulo = trabajo.getTitulo();
        this.descripcion = trabajo.getDescripcion();
        this.presupuesto = trabajo.getPresupuesto();
        this.fechaLimite = trabajo.getFechaLimite() != null ? trabajo.getFechaLimite().toString() : null;
        this.estado = trabajo.getEstado() != null ? trabajo.getEstado().name() : null;
        this.categoria = trabajo.getCategoria();
        this.ubicacion = trabajo.getUbicacion();
        this.imagenUrl = trabajo.getImagen() != null ? generarImagenUrl(trabajo.getIdtrabajo()) : null;
        this.idcliente = trabajo.getCliente().getIdcliente();
        this.nombreCliente = trabajo.getCliente().getNombre();
        this.idusuario = trabajo.getCliente().getUsuario().getIdusuario();  // Añadir esta línea
    }

    // Getters y setters para todos los campos

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

    public Double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public String getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(String fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public Long getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Long idcliente) {
        this.idcliente = idcliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Long getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Long idusuario) {
        this.idusuario = idusuario;
    }

    private String generarImagenUrl(Long trabajoId) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/trabajos/")
                .path(trabajoId.toString())
                .path("/imagen")
                .toUriString();
    }
}
