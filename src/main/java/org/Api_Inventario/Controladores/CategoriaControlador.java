package org.Api_Inventario.Controladores;

import jakarta.validation.Valid;
import org.Api_Inventario.Seguridad.Modelos.Usuario;
import org.Api_Inventario.Servicios.Interfaces.ICategoriaServicios;
import org.Api_Inventario.dtos.Categoria.CategoriaGuardar;
import org.Api_Inventario.dtos.Categoria.CategoriaModificar;
import org.Api_Inventario.dtos.Categoria.CategoriaSalida;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaControlador {

    private final ICategoriaServicios categoriaServicios;

    public CategoriaControlador(
            ICategoriaServicios categoriaServicios) {

        this.categoriaServicios = categoriaServicios;
    }

    // Obtiene todas las categorías registradas.
    @GetMapping
    public ResponseEntity<List<CategoriaSalida>> obtenerTodas() {

        return ResponseEntity.ok(
                categoriaServicios.obtenerTodas()
        );
    }

    // Busca una categoría específica por su identificador.
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaSalida> obtenerPorId(
            @PathVariable Integer id) {

        return categoriaServicios.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build());
    }

    // Registra una nueva categoría.
    @PostMapping
    public ResponseEntity<CategoriaSalida> guardar(
            @Valid @RequestBody CategoriaGuardar dto,
            @AuthenticationPrincipal Usuario usuario) {

        CategoriaSalida respuesta =
                categoriaServicios.guardar(dto,
                        usuario.getIdUsuario()
                );

        return new ResponseEntity<>(
                respuesta,
                HttpStatus.CREATED
        );
    }

    // Modifica los datos y el estado de una categoría existente.
    @PutMapping
    public ResponseEntity<CategoriaSalida> modificar(
            @Valid @RequestBody CategoriaModificar dto) {

        return ResponseEntity.ok(
                categoriaServicios.modificar(dto)
        );
    }

    // Eliminación física. Para uso normal es preferible
    // desactivar la categoría modificando su estado.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Integer id) {

        categoriaServicios.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}