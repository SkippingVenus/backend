package com.softii.laborappbackend.repositories;

import com.softii.laborappbackend.entities.Propuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropuestaRepository extends JpaRepository<Propuesta, Long> {
    // Puedes agregar métodos personalizados aquí si es necesario
}
