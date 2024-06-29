package com.softii.laborappbackend.repositories;

import com.softii.laborappbackend.entities.Postulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostulacionRepository extends JpaRepository<Postulacion, Long> {
    List<Postulacion> findByFreelancer_Idfreelancer(Long freelancerId);
    List<Postulacion> findByCliente_Idcliente(Long clienteId);
}
