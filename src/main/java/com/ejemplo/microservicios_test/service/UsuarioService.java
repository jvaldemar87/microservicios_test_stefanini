package com.ejemplo.microservicios_test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ejemplo.microservicios_test.exeption.UsuarioNoEncontradoException;
import com.ejemplo.microservicios_test.exeption.UsuarioYaExisteException;
import com.ejemplo.microservicios_test.model.Usuario;
import com.ejemplo.microservicios_test.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario crearUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new UsuarioYaExisteException("El email ya está en uso");
        }
        return usuarioRepository.save(usuario);
    }

    public void eliminarUsuarioPorId(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new UsuarioNoEncontradoException("Usuario con ID " + id + " no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario con ID " + id + " no encontrado"));
    }
}
