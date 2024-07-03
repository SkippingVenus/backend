package com.softii.laborappbackend.repositories;

import com.softii.laborappbackend.entities.Postulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostulacionRepository extends JpaRepository<Postulacion, Long> {
    List<Postulacion> findByFreelancerIdfreelancer(Long idfreelancer);
}
