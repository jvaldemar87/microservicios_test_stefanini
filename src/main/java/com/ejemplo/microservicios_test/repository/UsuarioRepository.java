package com.ejemplo.microservicios_test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ejemplo.microservicios_test.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}