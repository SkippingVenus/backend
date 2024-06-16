package com.softii.laborappbackend.repositories;

import com.softii.laborappbackend.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo); // Método para buscar usuario por correo
    boolean existsByCorreo(String correo); // Método para verificar si existe un usuario por correo
}
