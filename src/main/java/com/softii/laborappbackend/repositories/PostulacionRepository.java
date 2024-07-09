package com.softii.laborappbackend.repositories;

import com.softii.laborappbackend.entities.Postulacion;
import com.softii.laborappbackend.entities.EstadoPropuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface PostulacionRepository extends JpaRepository<Postulacion, Long> {
    List<Postulacion> findByFreelancer_Idfreelancer(Long idfreelancer);
    List<Postulacion> findByTrabajo_Idtrabajo(Long trabajoId);
    List<Postulacion> findByCliente_Idcliente(Long clienteId);
    Optional<Postulacion> findByTrabajoIdtrabajoAndEstado(Long idtrabajo, EstadoPropuesta estado);
}
