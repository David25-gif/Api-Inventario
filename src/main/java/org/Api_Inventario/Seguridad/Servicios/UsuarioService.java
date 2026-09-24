package org.Api_Inventario.Seguridad.Servicios;

import org.Api_Inventario.Seguridad.Modelos.Usuario;
import org.Api_Inventario.Seguridad.Repositorio.UsuarioRepositorio;
import org.Api_Inventario.Seguridad.dtos.UsuarioLogin;
import org.Api_Inventario.Seguridad.dtos.UsuarioRegistro;
import org.Api_Inventario.Seguridad.dtos.UsuarioToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepositorio userRepository;

    @Autowired
    private RolService rolService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public UsuarioToken login(UsuarioLogin loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        Usuario usuario = userRepository.findByLogin(loginRequest.getEmail()).orElseThrow();
        String token = jwtService.getToken(usuario);

        // ANTES: solo se seteaba .token(...), tipoToken y email quedaban en null
        return UsuarioToken.builder()
                .token(token)
                .tipoToken("Bearer")
                .email(usuario.getLogin())
                .build();
    }

    public UsuarioToken registro(UsuarioRegistro registroRequest) {
        
        if (userRepository.findByLogin(registroRequest.getEmail()).isPresent()) {
            throw new DataIntegrityViolationException("El correo ya está registrado");
        }


        Usuario usuario = Usuario.builder()
                .nombre(registroRequest.getNombre())
                .apellido(registroRequest.getApellido())
                .telefono(registroRequest.getTelefono())
                .login(registroRequest.getEmail())
                .clave(passwordEncoder.encode(registroRequest.getPassword()))
                .rol(rolService.obtenerPorId(registroRequest.getRolId()))
                .build();

        userRepository.save(usuario);

        return UsuarioToken.builder()
                .token(jwtService.getToken(usuario))
                .tipoToken("Bearer")
                .email(usuario.getLogin())
                .build();
    }
}