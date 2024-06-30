package com.softii.laborappbackend.repositories;

import com.softii.laborappbackend.entities.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FreelancerRepository extends JpaRepository<Freelancer, Long> {
    Optional<Freelancer> findByUsuario_Idusuario(Long idusuario);
}
