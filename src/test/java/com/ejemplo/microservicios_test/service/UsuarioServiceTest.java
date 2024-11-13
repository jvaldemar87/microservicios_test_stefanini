package com.ejemplo.microservicios_test.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        usuario.setEmail("juan@ejemplo.com");
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

    @Test
    void obtenerTodosLosUsuarios_deberiaRetornarListaDeUsuarios() {
        Usuario usuario1 = new Usuario();
        usuario1.setNombre("Juan");
        usuario1.setEmail("juan@ejemplo.com");

        Usuario usuario2 = new Usuario();
        usuario2.setNombre("Ana");
        usuario2.setEmail("ana@ejemplo.com");

        List<Usuario> usuarios = Arrays.asList(usuario1, usuario2);

        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<Usuario> resultado = usuarioService.obtenerTodosLosUsuarios();

        assertEquals(2, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
        assertEquals("Ana", resultado.get(1).getNombre());
        verify(usuarioRepository, times(1)).findAll(); 
    }

    @Test
    void obtenerTodosLosUsuarios_deberiaRetornarListaVacia_siNoHayUsuarios() {

        when(usuarioRepository.findAll()).thenReturn(Collections.emptyList());

        List<Usuario> resultado = usuarioService.obtenerTodosLosUsuarios();

        assertEquals(0, resultado.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void obtenerUsuarioPorId_deberiaRetornarUsuarioSiExiste() {

        Long userId = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(userId);
        usuario.setNombre("Juan");
        usuario.setEmail("juan@example.com");

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.obtenerUsuarioPorId(userId);

        assertEquals("Juan", resultado.getNombre());
        assertEquals("juan@example.com", resultado.getEmail());
        verify(usuarioRepository, times(1)).findById(userId);
    }

    @Test
    void obtenerUsuarioPorId_deberiaLanzarUsuarioNoEncontradoException_siNoExiste() {

        Long userId = 1L;
        when(usuarioRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UsuarioNoEncontradoException.class, () -> usuarioService.obtenerUsuarioPorId(userId));
        verify(usuarioRepository, times(1)).findById(userId);
    }
}