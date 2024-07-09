package com.softii.laborappbackend.repositories;

import com.softii.laborappbackend.entities.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    List<Calificacion> findByFreelancerIdfreelancer(Long idfreelancer);
    List<Calificacion> findByTrabajo_Idtrabajo(Long idtrabajo);
    Optional<Calificacion> findFirstByTrabajo_Idtrabajo(Long idtrabajo);
}
