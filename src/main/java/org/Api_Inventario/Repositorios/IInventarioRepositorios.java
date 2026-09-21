package org.Api_Inventario.Repositorios;

import org.Api_Inventario.Modelos.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IInventarioRepositorios
        extends JpaRepository<Inventario, Integer> {

    Optional<Inventario> findByIdProducto(Integer idProducto);
}