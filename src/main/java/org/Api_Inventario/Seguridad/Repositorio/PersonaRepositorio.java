package org.Api_Inventario.Seguridad.Repositorio;

import org.Api_Inventario.Seguridad.Modelos.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepositorio
        extends JpaRepository<Persona, Integer> {
}