package com.softii.laborappbackend.repositories;

import com.softii.laborappbackend.entities.EstadoTrabajo;
import com.softii.laborappbackend.entities.Trabajo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TrabajoRepository extends JpaRepository<Trabajo, Long> {
    List<Trabajo> findByCliente_Idcliente(Long idCliente);
    List<Trabajo> findByEstado(EstadoTrabajo estado);

}
