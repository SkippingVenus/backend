package com.softii.laborappbackend.controllers;

import com.softii.laborappbackend.dto.FreelancerCreationDTO;
import com.softii.laborappbackend.entities.Freelancer;
import com.softii.laborappbackend.entities.Usuario;
import com.softii.laborappbackend.repositories.FreelancerRepository;
import com.softii.laborappbackend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/freelancers")
public class FreelancerController {

    @Autowired
    private FreelancerRepository freelancerRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<List<Freelancer>> obtenerTodosLosFreelancers() {
        List<Freelancer> freelancers = freelancerRepository.findAll();
        return ResponseEntity.ok(freelancers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Freelancer> obtenerFreelancerPorId(@PathVariable Long id) {
        Optional<Freelancer> freelancerOptional = freelancerRepository.findById(id);
        return freelancerOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Freelancer> crearFreelancer(@RequestBody FreelancerCreationDTO freelancerDTO) {
        try {
            Optional<Usuario> usuarioOptional = usuarioRepository.findById(freelancerDTO.getIdusuario());
            if (usuarioOptional.isEmpty()) {
                throw new jakarta.persistence.EntityNotFoundException("Usuario no encontrado con ID: " + freelancerDTO.getIdusuario());
            }

            Usuario usuario = usuarioOptional.get();
            Freelancer nuevoFreelancer = new Freelancer();
            nuevoFreelancer.setUsuario(usuario);
            nuevoFreelancer.setCalificacion(freelancerDTO.getCalificacion());
            nuevoFreelancer.setDescripcion(freelancerDTO.getDescripcion());
            nuevoFreelancer.setHabilidades(freelancerDTO.getHabilidades());

            Freelancer freelancerGuardado = freelancerRepository.save(nuevoFreelancer);
            return ResponseEntity.status(HttpStatus.CREATED).body(freelancerGuardado);
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Freelancer> actualizarFreelancer(@PathVariable Long id, @RequestBody Freelancer freelancer) {
        if (!freelancerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        freelancer.setIdfreelancer(id);
        Freelancer freelancerActualizado = freelancerRepository.save(freelancer);
        return ResponseEntity.ok(freelancerActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFreelancer(@PathVariable Long id) {
        if (!freelancerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        freelancerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
