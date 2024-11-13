package com.ejemplo.microservicios_test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return usuarioRepository.save(usuario);
    }
}
