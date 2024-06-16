package com.softii.laborappbackend.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mensaje")
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idmensaje;

    @ManyToOne
    @JoinColumn(name = "idpropuesta", nullable = false)
    private Propuesta propuesta;

    @ManyToOne
    @JoinColumn(name = "remitente_id", nullable = false)
    private Usuario remitente;

    @ManyToOne
    @JoinColumn(name = "destinatario_id", nullable = false)
    private Usuario destinatario;

    @Column(nullable = false, length = 1000)
    private String contenido;

    @Column(nullable = false)
    private LocalDateTime fechaHora;

    // Constructor vacío (requerido por JPA)
    public Mensaje() {}

    // Constructor con parámetros (puedes personalizarlo según tus necesidades)
    public Mensaje(Propuesta propuesta, Usuario remitente, Usuario destinatario, String contenido, LocalDateTime fechaHora) {
        this.propuesta = propuesta;
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.contenido = contenido;
        this.fechaHora = fechaHora;
    }

    // Getters
    public Long getIdmensaje() {
        return idmensaje;
    }

    public Propuesta getPropuesta() {
        return propuesta;
    }

    public Usuario getRemitente() {
        return remitente;
    }

    public Usuario getDestinatario() {
        return destinatario;
    }

    public String getContenido() {
        return contenido;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    // Setters
    public void setIdmensaje(Long idmensaje) {
        this.idmensaje = idmensaje;
    }

    public void setPropuesta(Propuesta propuesta) {
        this.propuesta = propuesta;
    }

    public void setRemitente(Usuario remitente) {
        this.remitente = remitente;
    }

    public void setDestinatario(Usuario destinatario) {
        this.destinatario = destinatario;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
}
