package com.softii.laborappbackend.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idusuario;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private String contrasenia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    // Constructor vacío
    public Usuario() {}

    // Constructor con parámetros
    public Usuario(String nombre, String correo, String contrasenia, Rol rol) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }

    // Getters
    public Long getIdusuario() {
        return idusuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public Rol getRol() {
        return rol;
    }

    // Setters
    public void setIdusuario(Long idusuario) {
        this.idusuario = idusuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
