package com.softii.laborappbackend.dto;

import java.util.Date;

public class PropuestaCreationDTO {
    private Long idpropuesta;
    private Long idtrabajo;
    private Long idfreelancer;
    private Float monto;
    private String descripcion;
    private String estado;
    private Date fechaCreacion;

    // Getters y Setters
    public Long getIdpropuesta() {
        return idpropuesta;
    }

    public void setIdpropuesta(Long idpropuesta) {
        this.idpropuesta = idpropuesta;
    }

    public Long getIdtrabajo() {
        return idtrabajo;
    }

    public void setIdtrabajo(Long idtrabajo) {
        this.idtrabajo = idtrabajo;
    }

    public Long getIdfreelancer() {
        return idfreelancer;
    }

    public void setIdfreelancer(Long idfreelancer) {
        this.idfreelancer = idfreelancer;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
