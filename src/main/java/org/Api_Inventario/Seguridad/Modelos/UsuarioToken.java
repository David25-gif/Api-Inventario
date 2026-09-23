package org.Api_Inventario.Seguridad.Modelos;

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
    private String tipoToken; // "Bearer"
    private String email;
}
