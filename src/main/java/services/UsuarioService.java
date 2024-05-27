package services;

import entities.Usuario;

import java.util.List;

public interface UsuarioService {
    List<Usuario> findAll();
    Usuario save(Usuario usuario);
    boolean existByNombre(String nombre);
}