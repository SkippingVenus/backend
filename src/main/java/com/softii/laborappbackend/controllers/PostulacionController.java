package com.softii.laborappbackend.controllers;

import com.softii.laborappbackend.dto.PostulacionDTO;
import com.softii.laborappbackend.entities.Cliente;
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

import java.util.Base64;
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
        Optional<Freelancer> freelancerOptional = freelancerRepository.findByUsuario_Idusuario(postulacionDTO.getFreelancerId());
        Optional<Cliente> clienteOptional = clienteRepository.findById(postulacionDTO.getClienteId());
        Optional<Trabajo> trabajoOptional = trabajoRepository.findById(postulacionDTO.getTrabajoId());

        if (freelancerOptional.isEmpty() || clienteOptional.isEmpty() || trabajoOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Freelancer, Cliente, or Trabajo not found.");
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

        postulacionRepository.save(postulacion);

        return ResponseEntity.status(HttpStatus.CREATED).body("Postulación creada exitosamente.");
    }

    @Transactional
    @GetMapping("/freelancer/{id}")
    public ResponseEntity<List<PostulacionDTO>> obtenerPostulacionesPorFreelancer(@PathVariable Long id) {
        List<Postulacion> postulaciones = postulacionRepository.findByFreelancer_Usuario_Idusuario(id);
        if (postulaciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<PostulacionDTO> postulacionDTOs = postulaciones.stream().map(postulacion -> {
            PostulacionDTO dto = new PostulacionDTO();
            dto.setFreelancerId(postulacion.getFreelancer().getIdfreelancer());
            dto.setClienteId(postulacion.getCliente().getIdcliente());
            dto.setTrabajoId(postulacion.getTrabajo().getIdtrabajo());
            dto.setMensaje(postulacion.getMensaje());
            dto.setPresupuesto(postulacion.getPresupuesto());
            dto.setImagenBase64(postulacion.getTrabajo().getImagenBase64());  // Añadir la imagen en Base64
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(postulacionDTOs);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarPostulacion(@PathVariable Long id) {
        Optional<Postulacion> postulacionOptional = postulacionRepository.findById(id);
        if (postulacionOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        postulacionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
