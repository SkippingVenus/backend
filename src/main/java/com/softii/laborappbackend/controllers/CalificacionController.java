package com.softii.laborappbackend.controllers;

import com.softii.laborappbackend.dto.CalificacionDTO;
import com.softii.laborappbackend.entities.Calificacion;
import com.softii.laborappbackend.entities.Freelancer;
import com.softii.laborappbackend.entities.Trabajo;
import com.softii.laborappbackend.entities.Usuario;
import com.softii.laborappbackend.repositories.CalificacionRepository;
import com.softii.laborappbackend.repositories.FreelancerRepository;
import com.softii.laborappbackend.repositories.TrabajoRepository;
import com.softii.laborappbackend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/calificaciones")
public class CalificacionController {

    @Autowired
    private CalificacionRepository calificacionRepository;

    @Autowired
    private TrabajoRepository trabajoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FreelancerRepository freelancerRepository;

    @PostMapping
    public ResponseEntity<Calificacion> createCalificacion(@RequestBody CalificacionDTO calificacionDTO) {
        Calificacion calificacion = new Calificacion();

        Optional<Trabajo> trabajoOpt = trabajoRepository.findById(calificacionDTO.getIdtrabajo());
        if (!trabajoOpt.isPresent()) {
            return ResponseEntity.badRequest().body(null);
        }
        calificacion.setTrabajo(trabajoOpt.get());

        Optional<Usuario> usuarioOpt = usuarioRepository.findById(calificacionDTO.getIdusuario());
        if (!usuarioOpt.isPresent()) {
            return ResponseEntity.badRequest().body(null);
        }
        calificacion.setUsuario(usuarioOpt.get());

        Optional<Freelancer> freelancerOpt = freelancerRepository.findById(calificacionDTO.getIdfreelancer());
        if (!freelancerOpt.isPresent()) {
            return ResponseEntity.badRequest().body(null);
        }
        calificacion.setFreelancer(freelancerOpt.get());

        calificacion.setCalificacion(calificacionDTO.getCalificacion());
        calificacion.setComentario(calificacionDTO.getComentario());

        Calificacion savedCalificacion = calificacionRepository.save(calificacion);
        return ResponseEntity.ok(savedCalificacion);
    }

    @GetMapping("/trabajo/{idtrabajo}")
    public ResponseEntity<List<Calificacion>> getCalificacionByTrabajoId(@PathVariable Long idtrabajo) {
        try {
            List<Calificacion> calificaciones = calificacionRepository.findByTrabajo_Idtrabajo(idtrabajo);
            if (!calificaciones.isEmpty()) {
                return ResponseEntity.ok(calificaciones);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
