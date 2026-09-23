package org.Api_Inventario.Controladores;

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
    public ResponseEntity<UsuarioToken> login (@RequestBody usuariologin loginRequest){
    return ResponseEntity.ok(usuarioService.login(loginRequest));
}
}
