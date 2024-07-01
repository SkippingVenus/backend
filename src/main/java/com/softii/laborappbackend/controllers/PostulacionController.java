package com.softii.laborappbackend.controllers;

import com.softii.laborappbackend.dto.PostulacionDTO;
import com.softii.laborappbackend.entities.Cliente;
import com.softii.laborappbackend.entities.EstadoPropuesta;
import com.softii.laborappbackend.entities.Freelancer;
import com.softii.laborappbackend.entities.Postulacion;
import com.softii.laborappbackend.entities.Trabajo;
import com.softii.laborappbackend.repositories.ClienteRepository;
import com.softii.laborappbackend.repositories.FreelancerRepository;
import com.softii.laborappbackend.repositories.PostulacionRepository;
import com.softii.laborappbackend.repositories.TrabajoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/postulaciones")
public class PostulacionController {

    @Autowired
    private PostulacionRepository postulacionRepository;

    @Autowired
    private FreelancerRepository freelancerRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TrabajoRepository trabajoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> crearPostulacion(@RequestBody PostulacionDTO postulacionDTO) {
        System.out.println("Freelancer ID: " + postulacionDTO.getFreelancerId());
        System.out.println("Cliente ID: " + postulacionDTO.getClienteId());
        System.out.println("Trabajo ID: " + postulacionDTO.getTrabajoId());

        Optional<Freelancer> freelancerOptional = freelancerRepository.findById(postulacionDTO.getFreelancerId());
        Optional<Cliente> clienteOptional = clienteRepository.findById(postulacionDTO.getClienteId());
        Optional<Trabajo> trabajoOptional = trabajoRepository.findById(postulacionDTO.getTrabajoId());

        if (freelancerOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Freelancer not found with ID: " + postulacionDTO.getFreelancerId()));
        }
        if (clienteOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Cliente not found with ID: " + postulacionDTO.getClienteId()));
        }
        if (trabajoOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Trabajo not found with ID: " + postulacionDTO.getTrabajoId()));
        }

        Freelancer freelancer = freelancerOptional.get();
        Cliente cliente = clienteOptional.get();
        Trabajo trabajo = trabajoOptional.get();

        Postulacion postulacion = new Postulacion();
        postulacion.setFreelancer(freelancer);
        postulacion.setCliente(cliente);
        postulacion.setTrabajo(trabajo);
        postulacion.setMensaje(postulacionDTO.getMensaje());
        postulacion.setPresupuesto(postulacionDTO.getPresupuesto());
        postulacion.setEstado(EstadoPropuesta.EN_REVISION);

        postulacionRepository.save(postulacion);

        return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("message", "Postulación creada exitosamente."));
    }

    @GetMapping("/freelancer/{id}")
    public ResponseEntity<List<PostulacionDTO>> obtenerPostulacionesPorFreelancer(@PathVariable Long id) {
        List<Postulacion> postulaciones = postulacionRepository.findByFreelancer_Usuario_Idusuario(id);
        if (postulaciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<PostulacionDTO> postulacionDTOs = postulaciones.stream().map(PostulacionDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(postulacionDTOs);
    }

    @GetMapping("/estado/enviada")
    public ResponseEntity<List<PostulacionDTO>> obtenerPostulacionesEnviadas() {
        List<Postulacion> postulaciones = postulacionRepository.findByEstado(EstadoPropuesta.ENVIADA);
        if (postulaciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<PostulacionDTO> postulacionDTOs = postulaciones.stream().map(PostulacionDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(postulacionDTOs);
    }

    @GetMapping("/estado/revision")
    public ResponseEntity<List<PostulacionDTO>> obtenerPostulacionesEnRevision() {
        List<Postulacion> postulaciones = postulacionRepository.findByEstado(EstadoPropuesta.EN_REVISION);
        if (postulaciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<PostulacionDTO> postulacionDTOs = postulaciones.stream().map(PostulacionDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(postulacionDTOs);
    }

    @PutMapping("/{id}/enviar")
    public ResponseEntity<?> enviarPostulacion(@PathVariable Long id) {
        Optional<Postulacion> postulacionOptional = postulacionRepository.findById(id);
        if (postulacionOptional.isPresent()) {
            Postulacion postulacion = postulacionOptional.get();
            postulacion.setEstado(EstadoPropuesta.ENVIADA);
            postulacionRepository.save(postulacion);
            return ResponseEntity.ok(Collections.singletonMap("message", "Postulación enviada exitosamente."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Postulación no encontrada."));
        }
    }

    @PutMapping("/{id}/aprobar")
    public ResponseEntity<?> aprobarPostulacion(@PathVariable Long id) {
        Optional<Postulacion> postulacionOptional = postulacionRepository.findById(id);
        if (postulacionOptional.isPresent()) {
            Postulacion postulacion = postulacionOptional.get();
            postulacion.setEstado(EstadoPropuesta.ENVIADA);
            postulacionRepository.save(postulacion);
            return ResponseEntity.ok(Collections.singletonMap("message", "Postulación aprobada exitosamente."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Postulación no encontrada."));
        }
    }

    @PutMapping("/{id}/desaprobar")
    public ResponseEntity<?> desaprobarPostulacion(@PathVariable Long id) {
        Optional<Postulacion> postulacionOptional = postulacionRepository.findById(id);
        if (postulacionOptional.isPresent()) {
            Postulacion postulacion = postulacionOptional.get();
            postulacion.setEstado(EstadoPropuesta.RECHAZADA);
            postulacionRepository.save(postulacion);
            return ResponseEntity.ok(Collections.singletonMap("message", "Postulación desaprobada exitosamente."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Postulación no encontrada."));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPostulacion(@PathVariable Long id) {
        Optional<Postulacion> postulacionOptional = postulacionRepository.findById(id);
        if (postulacionOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        postulacionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
