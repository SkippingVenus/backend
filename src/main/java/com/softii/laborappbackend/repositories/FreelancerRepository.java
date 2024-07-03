package com.softii.laborappbackend.repositories;

import com.softii.laborappbackend.entities.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreelancerRepository extends JpaRepository<Freelancer, Long> {
    Freelancer findByUsuario_Idusuario(Long idusuario);
}
