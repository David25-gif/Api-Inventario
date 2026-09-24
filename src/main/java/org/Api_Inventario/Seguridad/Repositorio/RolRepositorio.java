package org.Api_Inventario.Seguridad.Repositorio;

import org.Api_Inventario.Seguridad.Modelos.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepositorio extends JpaRepository<Rol, Integer> {
}