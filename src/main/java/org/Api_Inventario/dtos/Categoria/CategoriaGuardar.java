package org.Api_Inventario.dtos.Categoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CategoriaGuardar(

        @NotBlank(message = "El nombre es obligatorio.")
        @Size(min = 3, max = 50,
                message = "El nombre debe tener entre 3 y 50 caracteres."
        )
        String nombre,

        @Size(max = 255,message = "La descripción no puede exceder los 255 caracteres."
        )
        String descripcion

) {
}