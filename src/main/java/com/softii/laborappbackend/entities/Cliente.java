package com.softii.laborappbackend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idcliente;

    @OneToOne
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuario usuario;

    // Constructor vacío
    public Cliente() {}

    // Constructor con parámetros
    public Cliente(Usuario usuario) {
        this.usuario = usuario;
    }

    // Getters
    public Long getIdcliente() {
        return idcliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    // Setters
    public void setIdcliente(Long idcliente) {
        this.idcliente = idcliente;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
