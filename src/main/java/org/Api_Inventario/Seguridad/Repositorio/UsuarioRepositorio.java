package org.Api_Inventario.Seguridad.Repositorio;

import org.Api_Inventario.Seguridad.Modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByLogin(String username);
}