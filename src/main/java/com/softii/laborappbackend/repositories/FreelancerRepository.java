package com.softii.laborappbackend.repositories;

import com.softii.laborappbackend.entities.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreelancerRepository extends JpaRepository<Freelancer, Long> {
    // Puedes agregar métodos personalizados aquí si es necesario
}
