package org.Api_Inventario.dtos.Categoria;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CategoriaSalida implements Serializable {

    private Integer idCategoria;
    private String nombre;
    private String descripcion;
    private Integer idEstado;
    private Integer creadoPorUsuario;

    public CategoriaSalida() {
    }

    public CategoriaSalida(Integer idCategoria, String nombre, String descripcion, Integer idEstado, Integer creadoPorUsuario) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idEstado = idEstado;
        this.creadoPorUsuario = creadoPorUsuario;
    }

    public CategoriaSalida(Integer idCategoria, String nombre, String descripcion) {
    }
}