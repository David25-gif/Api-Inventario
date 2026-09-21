package org.Api_Inventario.dtos.Producto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProductoGuardar(

        @NotBlank(message = "El nombre es obligatorio.")
        @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres.")
        String nombre,

        @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres.")
        String descripcion,

        @NotBlank(message = "El código de barras es obligatorio.")
        @Size(max = 100, message = "El código de barras no puede exceder los 100 caracteres.")
        String codigoBarras,

        @Size(max = 500, message = "La URL de la imagen no puede exceder los 500 caracteres.")
        String imagenUrl,

        @NotNull(message = "El idEstado es obligatorio.")
        @Positive(message = "El idEstado debe ser mayor a 0.")
        Integer idEstado,

        @NotNull(message = "El idCategoria es obligatorio.")
        @Positive(message = "El idCategoria debe ser mayor a 0.")
        Integer idCategoria,

        @NotNull(message = "El usuario es obligatorio.")
        @Positive(message = "El usuario debe ser mayor a 0.")
        Integer creadoPorUsuario
) {
}