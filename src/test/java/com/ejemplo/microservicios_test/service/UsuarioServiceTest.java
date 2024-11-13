package com.ejemplo.microservicios_test.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ejemplo.microservicios_test.exeption.UsuarioNoEncontradoException;
import com.ejemplo.microservicios_test.exeption.UsuarioYaExisteException;
import com.ejemplo.microservicios_test.model.Usuario;
import com.ejemplo.microservicios_test.repository.UsuarioRepository;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearUsuario_deberiaGuardarUsuarioCorrectamente() {

        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setEmail("juan@example.com");
        usuario.setPassword("password123");

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario usuarioGuardado = usuarioService.crearUsuario(usuario);

        assertEquals(usuario.getEmail(), usuarioGuardado.getEmail());
        verify(usuarioRepository, times(1)).save(usuario); 
    }

    @Test
    void crearUsuario_deberiaLanzarUsuarioYaExisteException_siEmailYaExiste() {

        Usuario usuario = new Usuario();
        usuario.setNombre("Ana");
        usuario.setEmail("ana@ejemplo.com");
        usuario.setPassword("password123");

        when(usuarioRepository.existsByEmail(usuario.getEmail())).thenReturn(true);

        assertThrows(UsuarioYaExisteException.class, () -> usuarioService.crearUsuario(usuario));

        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void eliminarUsuarioPorId_deberiaEliminarUsuarioSiExiste() {
        Long userId = 1L;
        when(usuarioRepository.existsById(userId)).thenReturn(true);

        usuarioService.eliminarUsuarioPorId(userId);

        verify(usuarioRepository, times(1)).deleteById(userId);
    }

    @Test
    void eliminarUsuarioPorId_deberiaLanzarUsuarioNoEncontradoException_siNoExiste() {
        Long userId = 1L;
        when(usuarioRepository.existsById(userId)).thenReturn(false);

        assertThrows(UsuarioNoEncontradoException.class, () -> usuarioService.eliminarUsuarioPorId(userId));

        verify(usuarioRepository, never()).deleteById(anyLong());
    }
}