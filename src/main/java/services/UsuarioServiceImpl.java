package services;


import Repositorios.RolRepo;
import Repositorios.UsuarioRepo;
import entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService{
    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private RolRepo rolRepo;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return (List<Usuario>) usuarioRepo.findAll();
    }

    @Override
    public Usuario save(Usuario usuario) {
        Optional<Rol> optionalRolUsuario = rolRepo.findByNombre("ROL_USER");
        List<Rol> roles = new ArrayList<>();

        optionalRolUsuario.ifPresent(roles::add);

        if (usuario.isAdmin()) {
            Optional<Rol> optionalRolAdmin = rolRepo.findByNombre("ROLE_ADMIN");
            optionalRolAdmin.ifPresent(roles::add);
        }

        usuario.setRoles(roles);
        usuario.setContrasenia(usuario.getContrasenia());
        return usuarioRepo.save(usuario);
    }

    @Override
    public boolean existByNombre(String nombre) {
        return usuarioRepo.existsByNombre(nombre);
    }
}