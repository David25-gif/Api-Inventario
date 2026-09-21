package org.Api_Inventario.dtos.Categoria;

import java.io.Serializable;

public record CategoriaSalida(

        Integer idCategoria,
        String nombre,
        String descripcion,
        Integer idEstado,
        Integer creadoPorUsuario

) implements Serializable {
}