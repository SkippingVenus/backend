package com.softii.laborappbackend.dto;

import com.softii.laborappbackend.entities.Postulacion;
import java.util.Base64;

public class PostulacionDTO {
    private Long id;
    private Long trabajoId;
    private Long clienteId;
    private Long freelancerId;
    private String mensaje;
    private Double presupuesto;
    private String tituloTrabajo;
    private String descripcionTrabajo;
    private String estadoTrabajo;
    private String imagenBase64;
    private String nombreFreelancer;

    public PostulacionDTO() {}

    public PostulacionDTO(Postulacion postulacion) {
        this.id = postulacion.getId();
        this.trabajoId = postulacion.getTrabajo().getIdtrabajo();
        this.clienteId = postulacion.getCliente().getIdcliente();
        this.freelancerId = postulacion.getFreelancer().getIdfreelancer();
        this.mensaje = postulacion.getMensaje();
        this.presupuesto = postulacion.getPresupuesto();
        this.tituloTrabajo = postulacion.getTrabajo().getTitulo();
        this.descripcionTrabajo = postulacion.getTrabajo().getDescripcion();
        this.estadoTrabajo = postulacion.getEstado().name();
        this.nombreFreelancer = postulacion.getFreelancer().getUsuario().getNombre();
        if (postulacion.getTrabajo().getImagen() != null) {
            this.imagenBase64 = Base64.getEncoder().encodeToString(postulacion.getTrabajo().getImagen());
        }
    }

    // Getters and setters for all fields

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTrabajoId() {
        return trabajoId;
    }

    public void setTrabajoId(Long trabajoId) {
        this.trabajoId = trabajoId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getFreelancerId() {
        return freelancerId;
    }

    public void setFreelancerId(Long freelancerId) {
        this.freelancerId = freelancerId;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public String getTituloTrabajo() {
        return tituloTrabajo;
    }

    public void setTituloTrabajo(String tituloTrabajo) {
        this.tituloTrabajo = tituloTrabajo;
    }

    public String getDescripcionTrabajo() {
        return descripcionTrabajo;
    }

    public void setDescripcionTrabajo(String descripcionTrabajo) {
        this.descripcionTrabajo = descripcionTrabajo;
    }

    public String getEstadoTrabajo() {
        return estadoTrabajo;
    }

    public void setEstadoTrabajo(String estadoTrabajo) {
        this.estadoTrabajo = estadoTrabajo;
    }

    public String getImagenBase64() {
        return imagenBase64;
    }

    public void setImagenBase64(String imagenBase64) {
        this.imagenBase64 = imagenBase64;
    }

    public String getNombreFreelancer() {
        return nombreFreelancer;
    }

    public void setNombreFreelancer(String nombreFreelancer) {
        this.nombreFreelancer = nombreFreelancer;
    }
}
