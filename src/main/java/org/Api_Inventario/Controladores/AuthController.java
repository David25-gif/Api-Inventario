package org.Api_Inventario.Controladores;

import jakarta.validation.Valid;
import org.Api_Inventario.Seguridad.dtos.UsuarioLogin;
import org.Api_Inventario.Seguridad.dtos.UsuarioRegistro;
import org.Api_Inventario.Seguridad.dtos.UsuarioToken;
import org.Api_Inventario.Seguridad.Servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<UsuarioToken> login(@Valid @RequestBody UsuarioLogin loginRequest)
    {
        return ResponseEntity.ok(usuarioService.login(loginRequest));
    }

    @PostMapping("/registro")
    public ResponseEntity<UsuarioToken> registro(@Valid @RequestBody UsuarioRegistro usuarioRequest)
    {
        return ResponseEntity.ok(usuarioService.registro(usuarioRequest));
    }
}