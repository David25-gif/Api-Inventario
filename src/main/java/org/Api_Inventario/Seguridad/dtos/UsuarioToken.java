package org.Api_Inventario.Seguridad.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioToken {

    private String token;
    private String tipoToken; // Ejemplo: "Bearer"
    private String email;
}
