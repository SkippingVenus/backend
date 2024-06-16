package com.softii.laborappbackend.dto;

import com.softii.laborappbackend.entities.EstadoPropuesta; // Asegúrate de importar EstadoPropuesta

public class PropuestaCreationDTO {
    private Long idtrabajo;
    private Long idfreelancer;
    private Float monto;
    private String descripcion;
    private EstadoPropuesta estado; // Utiliza el enum directamente

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

    public EstadoPropuesta getEstado() {
        return estado;
    }

    public void setEstado(EstadoPropuesta estado) {
        this.estado = estado;
    }
}
