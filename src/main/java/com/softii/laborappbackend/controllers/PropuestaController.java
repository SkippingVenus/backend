package com.softii.laborappbackend.controllers;

import com.softii.laborappbackend.dto.PropuestaCreationDTO;
import com.softii.laborappbackend.entities.EstadoPropuesta;
import com.softii.laborappbackend.entities.Freelancer;
import com.softii.laborappbackend.entities.Propuesta;
import com.softii.laborappbackend.entities.Trabajo;
import com.softii.laborappbackend.repositories.FreelancerRepository;
import com.softii.laborappbackend.repositories.PropuestaRepository;
import com.softii.laborappbackend.repositories.TrabajoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/propuestas")
public class PropuestaController {

    @Autowired
    private PropuestaRepository propuestaRepository;

    @Autowired
    private TrabajoRepository trabajoRepository;

    @Autowired
    private FreelancerRepository freelancerRepository;

    // Obtener todas las propuestas
    @GetMapping
    public ResponseEntity<List<Propuesta>> obtenerTodasLasPropuestas() {
        List<Propuesta> propuestas = propuestaRepository.findAll();
        return ResponseEntity.ok(propuestas);
    }

    // Obtener una propuesta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Propuesta> obtenerPropuestaPorId(@PathVariable Long id) {
        Optional<Propuesta> propuestaOptional = propuestaRepository.findById(id);
        return propuestaOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DTO para la creación de una propuesta
    public static class PropuestaCreationDTO {
        private Long idtrabajo;
        private Long idfreelancer;
        private Float monto;
        private String descripcion;
        private String estado;

        // Getters y Setters (generados automáticamente o con Lombok)
        public Long getIdtrabajo() {
            return idtrabajo;
        }

        public void setIdtrabajo(Long idtrabajo) {
            this.idtrabajo = idtrabajo;
        }

        public Long getIdfreelancer() {
            return idfreelancer;
        }

        public void setIdfreelancer(Long idfreelancer) {
            this.idfreelancer = idfreelancer;
        }

        public Float getMonto() {
            return monto;
        }

        public void setMonto(Float monto) {
            this.monto = monto;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }
    }

    // Crear una nueva propuesta
    @PostMapping
    public ResponseEntity<?> crearPropuesta(@RequestBody PropuestaCreationDTO propuestaDTO) {
        try {
            // Verificar si el trabajo y el freelancer existen
            Trabajo trabajo = trabajoRepository.findById(propuestaDTO.getIdtrabajo())
                    .orElseThrow(() -> new EntityNotFoundException("Trabajo no encontrado"));

            Freelancer freelancer = freelancerRepository.findById(propuestaDTO.getIdfreelancer())
                    .orElseThrow(() -> new EntityNotFoundException("Freelancer no encontrado"));

            // Crear y guardar la propuesta
            Propuesta nuevaPropuesta = new Propuesta();
            nuevaPropuesta.setTrabajo(trabajo);
            nuevaPropuesta.setFreelancer(freelancer);
            nuevaPropuesta.setMonto(propuestaDTO.getMonto());
            nuevaPropuesta.setDescripcion(propuestaDTO.getDescripcion());
            nuevaPropuesta.setEstado(EstadoPropuesta.valueOf(propuestaDTO.getEstado())); // Asignar estado desde el DTO
            nuevaPropuesta.setFechaCreacion(new Date());

            Propuesta propuestaGuardada = propuestaRepository.save(nuevaPropuesta);
            return ResponseEntity.status(HttpStatus.CREATED).body(propuestaGuardada);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (IllegalArgumentException ex) { // Capturar IllegalArgumentException si el estado no es válido
            return ResponseEntity.badRequest().body("Estado de propuesta no válido");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    // Actualizar una propuesta existente
    @PutMapping("/{id}")
    public ResponseEntity<Propuesta> actualizarPropuesta(@PathVariable Long id, @RequestBody Propuesta propuesta) {
        if (propuestaRepository.existsById(id)) {
            propuesta.setIdpropuesta(id);
            Propuesta propuestaActualizada = propuestaRepository.save(propuesta);
            return ResponseEntity.ok(propuestaActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar una propuesta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPropuesta(@PathVariable Long id) {
        if (propuestaRepository.existsById(id)) {
            propuestaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
