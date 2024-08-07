package com.softii.laborappbackend.repositories;

import com.softii.laborappbackend.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByUsuario_Idusuario(Long idusuario); // Método para encontrar cliente por usuario.idusuario
}
