package com.softii.laborappbackend.dtos;

public class PostulacionDTO {
    private Long idfreelancer;
    private Long idcliente;
    private Long idtrabajo;
    private String mensaje;
    private Double presupuesto;

    // Getters y Setters
    public Long getIdfreelancer() {
        return idfreelancer;
    }

    public void setIdfreelancer(Long idfreelancer) {
        this.idfreelancer = idfreelancer;
    }

    public Long getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Long idcliente) {
        this.idcliente = idcliente;
    }

    public Long getIdtrabajo() {
        return idtrabajo;
    }

    public void setIdtrabajo(Long idtrabajo) {
        this.idtrabajo = idtrabajo;
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
}
