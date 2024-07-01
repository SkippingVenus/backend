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
@RequestMapping("/freelancers")
public class FreelancerController {

    @Autowired
    private FreelancerRepository freelancerRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<Freelancer> crearFreelancer(@RequestBody FreelancerCreationDTO freelancerDTO) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(freelancerDTO.getIdusuario());
        if (!usuarioOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Freelancer freelancer = new Freelancer();
        freelancer.setUsuario(usuarioOptional.get());
        freelancer.setCalificacion(freelancerDTO.getCalificacion());
        freelancer.setDescripcion(freelancerDTO.getDescripcion());
        freelancer.setHabilidades(freelancerDTO.getHabilidades());

        Freelancer nuevoFreelancer = freelancerRepository.save(freelancer);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoFreelancer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Freelancer> obtenerFreelancer(@PathVariable Long id) {
        Optional<Freelancer> freelancerOptional = freelancerRepository.findById(id);
        if (freelancerOptional.isPresent()) {
            return ResponseEntity.ok(freelancerOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Freelancer>> obtenerTodosLosFreelancers() {
        List<Freelancer> freelancers = freelancerRepository.findAll();
        return ResponseEntity.ok(freelancers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Freelancer> actualizarFreelancer(@PathVariable Long id, @RequestBody FreelancerCreationDTO freelancerDTO) {
        Optional<Freelancer> freelancerOptional = freelancerRepository.findById(id);
        if (!freelancerOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Freelancer freelancer = freelancerOptional.get();
        freelancer.setCalificacion(freelancerDTO.getCalificacion());
        freelancer.setDescripcion(freelancerDTO.getDescripcion());
        freelancer.setHabilidades(freelancerDTO.getHabilidades());

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(freelancerDTO.getIdusuario());
        if (!usuarioOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        freelancer.setUsuario(usuarioOptional.get());

        Freelancer freelancerActualizado = freelancerRepository.save(freelancer);
        return ResponseEntity.ok(freelancerActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFreelancer(@PathVariable Long id) {
        if (!freelancerRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        freelancerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
