package com.softii.laborappbackend.controllers;

import com.softii.laborappbackend.dto.PropuestaCreationDTO;
import com.softii.laborappbackend.entities.Propuesta;
import com.softii.laborappbackend.repositories.FreelancerRepository;
import com.softii.laborappbackend.repositories.PropuestaRepository;
import com.softii.laborappbackend.repositories.TrabajoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping
    public ResponseEntity<List<PropuestaCreationDTO>> obtenerTodasLasPropuestas() {
        List<Propuesta> propuestas = propuestaRepository.findAll();
        List<PropuestaCreationDTO> propuestaDTOs = new ArrayList<>();
        for (Propuesta propuesta : propuestas) {
            PropuestaCreationDTO propuestaDTO = new PropuestaCreationDTO();
            propuestaDTO.setIdtrabajo(propuesta.getTrabajo().getIdtrabajo());
            propuestaDTO.setIdfreelancer(propuesta.getFreelancer().getIdfreelancer());
            propuestaDTO.setMonto(propuesta.getMonto());
            propuestaDTO.setDescripcion(propuesta.getDescripcion());
            propuestaDTO.setEstado(propuesta.getEstado().toString());
            propuestaDTOs.add(propuestaDTO);
        }
        return ResponseEntity.ok(propuestaDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropuestaCreationDTO> obtenerPropuestaPorId(@PathVariable Long id) {
        Optional<Propuesta> propuestaOptional = propuestaRepository.findById(id);
        if (propuestaOptional.isPresent()) {
            Propuesta propuesta = propuestaOptional.get();
            PropuestaCreationDTO propuestaDTO = new PropuestaCreationDTO();
            propuestaDTO.setIdtrabajo(propuesta.getTrabajo().getIdtrabajo());
            propuestaDTO.setIdfreelancer(propuesta.getFreelancer().getIdfreelancer());
            propuestaDTO.setMonto(propuesta.getMonto());
            propuestaDTO.setDescripcion(propuesta.getDescripcion());
            propuestaDTO.setEstado(propuesta.getEstado().toString());
            return ResponseEntity.ok(propuestaDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

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
