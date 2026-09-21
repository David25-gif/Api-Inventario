package org.Api_Inventario.Repositorios;

import org.Api_Inventario.Modelos.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductoRepositorios extends JpaRepository<Producto, Integer> {

    List<Producto> findByIdCategoria(Integer idCategoria);

    boolean existsByCodigoBarras(String codigoBarras);

    boolean existsByCodigoBarrasAndIdProductoNot(String codigoBarras, Integer idProducto);
}