package org.Api_Inventario.Repositorios;

import org.Api_Inventario.Modelos.MovimientoInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMovimientoInventarioRepositorios
        extends JpaRepository<MovimientoInventario, Integer> {

    List<MovimientoInventario> findByIdInventario(Integer idInventario);
}