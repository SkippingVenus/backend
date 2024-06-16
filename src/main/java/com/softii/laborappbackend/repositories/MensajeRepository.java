package com.softii.laborappbackend.repositories;

import com.softii.laborappbackend.entities.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    // Puedes agregar métodos personalizados aquí si es necesario
}
