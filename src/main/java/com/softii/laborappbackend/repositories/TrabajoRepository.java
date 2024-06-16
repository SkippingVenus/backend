package com.softii.laborappbackend.repositories;

import com.softii.laborappbackend.entities.Trabajo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrabajoRepository extends JpaRepository<Trabajo, Long> {
    // Buscar trabajos por categoría
    List<Trabajo> findByCategoria(String categoria);

    // Buscar trabajos por ubicación
    List<Trabajo> findByUbicacion(String ubicacion);

    // Buscar trabajos por estado
    List<Trabajo> findByEstado(String estado);
}
