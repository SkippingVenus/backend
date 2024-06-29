package com.softii.laborappbackend.controllers;

import com.softii.laborappbackend.dtos.PostulacionDTO;
import com.softii.laborappbackend.entities.Postulacion;
import com.softii.laborappbackend.entities.Freelancer;
import com.softii.laborappbackend.entities.Cliente;
import com.softii.laborappbackend.entities.Trabajo;
import com.softii.laborappbackend.repositories.PostulacionRepository;
import com.softii.laborappbackend.repositories.FreelancerRepository;
import com.softii.laborappbackend.repositories.ClienteRepository;
import com.softii.laborappbackend.repositories.TrabajoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public Postulacion crearPostulacion(@RequestBody PostulacionDTO postulacionDTO) {
        if (postulacionDTO.getIdfreelancer() == null) {
            throw new IllegalArgumentException("Freelancer ID must not be null");
        }
        if (postulacionDTO.getIdcliente() == null) {
            throw new IllegalArgumentException("Cliente ID must not be null");
        }
        if (postulacionDTO.getIdtrabajo() == null) {
            throw new IllegalArgumentException("Trabajo ID must not be null");
        }

        // Obtener el freelancer, cliente y trabajo desde la base de datos
        Optional<Freelancer> freelancerOpt = freelancerRepository.findById(postulacionDTO.getIdfreelancer());
        Optional<Cliente> clienteOpt = clienteRepository.findById(postulacionDTO.getIdcliente());
        Optional<Trabajo> trabajoOpt = trabajoRepository.findById(postulacionDTO.getIdtrabajo());

        if (freelancerOpt.isPresent() && clienteOpt.isPresent() && trabajoOpt.isPresent()) {
            Postulacion postulacion = new Postulacion();
            postulacion.setFreelancer(freelancerOpt.get());
            postulacion.setCliente(clienteOpt.get());
            postulacion.setTrabajo(trabajoOpt.get());
            postulacion.setMensaje(postulacionDTO.getMensaje());
            postulacion.setPresupuesto(postulacionDTO.getPresupuesto());
            return postulacionRepository.save(postulacion);
        } else {
            StringBuilder errorMessage = new StringBuilder("Freelancer, Cliente, or Trabajo not found. ");
            if (!freelancerOpt.isPresent()) {
                errorMessage.append("Freelancer not found. ");
            }
            if (!clienteOpt.isPresent()) {
                errorMessage.append("Cliente not found. ");
            }
            if (!trabajoOpt.isPresent()) {
                errorMessage.append("Trabajo not found. ");
            }
            throw new IllegalArgumentException(errorMessage.toString());
        }
    }

    @GetMapping("/freelancer/{freelancerId}")
    public List<Postulacion> getPostulacionesByFreelancerId(@PathVariable Long freelancerId) {
        return postulacionRepository.findByFreelancer_Idfreelancer(freelancerId);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Postulacion> getPostulacionesByClienteId(@PathVariable Long clienteId) {
        return postulacionRepository.findByCliente_Idcliente(clienteId);
    }
}
