package com.softii.laborappbackend.controllers;

import com.softii.laborappbackend.entities.EstadoPropuesta;
import com.softii.laborappbackend.entities.EstadoTrabajo;
import com.softii.laborappbackend.entities.Postulacion;
import com.softii.laborappbackend.entities.Trabajo;
import com.softii.laborappbackend.entities.Usuario;
import com.softii.laborappbackend.repositories.PostulacionRepository;
import com.softii.laborappbackend.repositories.UsuarioRepository;
import com.softii.laborappbackend.repositories.TrabajoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/postulaciones")
public class PostulacionController {

    @Autowired
    private PostulacionRepository postulacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TrabajoRepository trabajoRepository;

    @PostMapping
    public ResponseEntity<?> crearPostulacion(@RequestBody Postulacion postulacion) {
        System.out.println("Recibido postulacion: " + postulacion);
        System.out.println("Trabajo ID: " + postulacion.getTrabajo().getIdtrabajo());
        System.out.println("Cliente ID: " + postulacion.getCliente().getIdcliente());
        System.out.println("Freelancer ID: " + postulacion.getFreelancer().getIdfreelancer());
        postulacion.setEstado(EstadoPropuesta.PENDIENTE); // O cualquier estado inicial que necesites
        postulacionRepository.save(postulacion);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Postulacion>> obtenerPostulaciones() {
        List<Postulacion> postulaciones = postulacionRepository.findAll();
        return ResponseEntity.ok(postulaciones);
    }

    @GetMapping("/freelancer/{idfreelancer}")
    public ResponseEntity<List<Postulacion>> getPostulacionesByFreelancer(@PathVariable Long idfreelancer) {
        List<Postulacion> postulaciones = postulacionRepository.findByFreelancer_Idfreelancer(idfreelancer);
        return ResponseEntity.ok(postulaciones);
    }

    @GetMapping("/trabajo/{trabajoId}")
    public ResponseEntity<List<Postulacion>> getPostulacionesByTrabajoId(@PathVariable Long trabajoId) {
        List<Postulacion> postulaciones = postulacionRepository.findByTrabajo_Idtrabajo(trabajoId);
        return ResponseEntity.ok(postulaciones);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Postulacion>> getPostulacionesByClienteId(@PathVariable Long clienteId) {
        List<Postulacion> postulaciones = postulacionRepository.findByCliente_Idcliente(clienteId);
        return ResponseEntity.ok(postulaciones);
    }

    @GetMapping("/freelancers/{idfreelancer}/imagen")
    public ResponseEntity<byte[]> getFreelancerImagen(@PathVariable Long idfreelancer) {
        Usuario usuario = usuarioRepository.findById(idfreelancer)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        byte[] imagen = usuario.getImagen(); // Asegúrate de que 'imagen' sea un byte array en tu entidad Usuario

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPostulacion(@PathVariable Long id) {
        postulacionRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/aceptar")
    public ResponseEntity<?> aceptarPropuesta(@PathVariable Long id) {
        Postulacion postulacion = postulacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Propuesta no encontrada"));
        postulacion.setEstado(EstadoPropuesta.ACEPTADO);
        postulacionRepository.save(postulacion);

        // Cambiar el estado del trabajo a ACEPTADO
        Trabajo trabajo = postulacion.getTrabajo();
        trabajo.setEstado(EstadoTrabajo.ACEPTADO);
        trabajoRepository.save(trabajo);

        // Rechazar otras propuestas para el mismo trabajo
        List<Postulacion> otrasPropuestas = postulacionRepository.findByTrabajo_Idtrabajo(trabajo.getIdtrabajo());
        for (Postulacion otraPropuesta : otrasPropuestas) {
            if (!otraPropuesta.getId().equals(postulacion.getId())) {
                otraPropuesta.setEstado(EstadoPropuesta.RECHAZADO);
                postulacionRepository.save(otraPropuesta);
            }
        }

        return ResponseEntity.ok().build();
    }
    @GetMapping("/cliente/{clienteId}/detalle")
    public ResponseEntity<Usuario> getClienteDetalle(@PathVariable Long clienteId) {
        Usuario cliente = usuarioRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return ResponseEntity.ok(cliente);
    }

    @PostMapping("/{id}/rechazar")
    public ResponseEntity<?> rechazarPropuesta(@PathVariable Long id) {
        Postulacion postulacion = postulacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Propuesta no encontrada"));
        postulacion.setEstado(EstadoPropuesta.RECHAZADO);
        postulacionRepository.save(postulacion);
        return ResponseEntity.ok().build();
    }
}
