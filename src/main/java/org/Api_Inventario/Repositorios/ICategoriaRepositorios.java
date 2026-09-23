package org.Api_Inventario.Repositorios;

import org.Api_Inventario.Modelos.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoriaRepositorios
        extends JpaRepository<Categoria, Integer> {

    boolean existsByNombre(String nombre);

    boolean existsByNombreAndIdCategoriaNot(
            String nombre,
            Integer idCategoria
    );
}