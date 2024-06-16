package com.softii.laborappbackend.controllers;

import com.softii.laborappbackend.entities.Usuario;
import com.softii.laborappbackend.dto.FreelancerCreationDTO;
import com.softii.laborappbackend.entities.Freelancer;
import com.softii.laborappbackend.repositories.FreelancerRepository;
import com.softii.laborappbackend.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Permite solicitudes desde este origen
@RequestMapping("/freelancers")
public class FreelancerController {

    @Autowired
    private FreelancerRepository freelancerRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Obtener todos los freelancers
    @GetMapping
    public ResponseEntity<List<Freelancer>> obtenerTodosLosFreelancers() {
        List<Freelancer> freelancers = freelancerRepository.findAll();
        return ResponseEntity.ok(freelancers);
    }

    // Obtener un freelancer por ID
    @GetMapping("/{id}")
    public ResponseEntity<Freelancer> obtenerFreelancerPorId(@PathVariable Long id) {
        Optional<Freelancer> freelancerOptional = freelancerRepository.findById(id);
        return freelancerOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo freelancer (usando el DTO)
    @PostMapping
    public ResponseEntity<Freelancer> crearFreelancer(@RequestBody FreelancerCreationDTO freelancerDTO) {
        // Verificar si el usuario existe
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(freelancerDTO.getIdusuario());
        if (usuarioOptional.isEmpty()) {
            throw new EntityNotFoundException("Usuario no encontrado con ID: " + freelancerDTO.getIdusuario());
        }

        Freelancer nuevoFreelancer = new Freelancer();
        nuevoFreelancer.setUsuario(usuarioOptional.get());

        // Asignar el resto de atributos
        nuevoFreelancer.setCalificacion(freelancerDTO.getCalificacion());
        nuevoFreelancer.setDescripcion(freelancerDTO.getDescripcion());
        nuevoFreelancer.setHabilidades(freelancerDTO.getHabilidades());

        nuevoFreelancer = freelancerRepository.save(nuevoFreelancer);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoFreelancer);
    }


    // Actualizar un freelancer existente
    @PutMapping("/{id}")
    public ResponseEntity<Freelancer> actualizarFreelancer(@PathVariable Long id, @RequestBody Freelancer freelancer) {
        if (!freelancerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        freelancer.setIdfreelancer(id);
        Freelancer freelancerActualizado = freelancerRepository.save(freelancer);
        return ResponseEntity.ok(freelancerActualizado);
    }

    // Eliminar un freelancer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFreelancer(@PathVariable Long id) {
        if (!freelancerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        freelancerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

