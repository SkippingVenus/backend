package com.softii.laborappbackend.repositories;

import com.softii.laborappbackend.entities.Postulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostulacionRepository extends JpaRepository<Postulacion, Long> {
    List<Postulacion> findByFreelancer_Idfreelancer(Long freelancerId);
    List<Postulacion> findByCliente_Idcliente(Long clienteId);
    List<Postulacion> findByTrabajo_Idtrabajo(Long trabajoId);
    List<Postulacion> findByFreelancer_Usuario_Idusuario(Long idusuario);
}
