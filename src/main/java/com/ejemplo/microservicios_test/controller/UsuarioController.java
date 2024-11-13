package com.ejemplo.microservicios_test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplo.microservicios_test.model.Usuario;
import com.ejemplo.microservicios_test.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuarioPorId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content si se elimina correctamente
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK); // 200 OK con la lista de usuarios
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        return new ResponseEntity<>(usuario, HttpStatus.OK); // 200 OK si el usuario existe
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        Usuario usuario = usuarioService.actualizarUsuario(id, usuarioActualizado);
        return new ResponseEntity<>(usuario, HttpStatus.OK); // 200 OK con el usuario actualizado
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuarioParcial(@PathVariable Long id, @RequestBody Usuario usuarioParcial) {
        Usuario usuarioActualizado = usuarioService.actualizarUsuarioParcial(id, usuarioParcial);
        return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK); // 200 OK con el usuario actualizado
    }
}