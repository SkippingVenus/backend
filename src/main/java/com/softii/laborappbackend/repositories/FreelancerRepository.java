package com.softii.laborappbackend.repositories;

import com.softii.laborappbackend.entities.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FreelancerRepository extends JpaRepository<Freelancer, Long> {
    Optional<Freelancer> findByUsuario_Idusuario(Long idusuario);
}
