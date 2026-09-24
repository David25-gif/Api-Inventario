package org.Api_Inventario.Seguridad.Servicios;

import org.Api_Inventario.Seguridad.Modelos.Persona;
import org.Api_Inventario.Seguridad.Modelos.Usuario;
import org.Api_Inventario.Seguridad.Repositorio.PersonaRepositorio;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepositorio userRepository;

    @Autowired
    private PersonaRepositorio personaRepositorio;

    @Autowired
    private RolService rolService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;


    // =========================================================
    // INICIO DE SESIÓN
    // =========================================================

    public UsuarioToken login(UsuarioLogin loginRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        Usuario usuario = userRepository
                .findByLogin(loginRequest.getEmail())
                .orElseThrow();

        String token = jwtService.getToken(usuario);

        return UsuarioToken.builder()
                .token(token)
                .tipoToken("Bearer")
                .email(usuario.getLogin())
                .build();
    }


    // =========================================================
    // REGISTRO DE USUARIO
    // =========================================================

    @Transactional
    public UsuarioToken registro(UsuarioRegistro registroRequest) {

        // Evita registrar dos usuarios con el mismo correo.
        if (userRepository.findByLogin(registroRequest.getEmail()).isPresent()) {
            throw new DataIntegrityViolationException(
                    "El correo ya está registrado"
            );
        }


        // -----------------------------------------------------
        // 1. Crear primero el registro de la persona
        // -----------------------------------------------------

        Persona persona = Persona.builder()
                .nombre(registroRequest.getNombre())
                .apellido(registroRequest.getApellido())
                .direccion(registroRequest.getDireccion())
                .dui(registroRequest.getDui())
                .telefono(registroRequest.getTelefono())
                .fechaCreacion(LocalDateTime.now())
                .idEstado(1)
                .build();

        /*
         * Al guardar la persona, MySQL genera automáticamente
         * el PersonId que luego será utilizado por Users.
         */
        Persona personaGuardada =
                personaRepositorio.save(persona);


        // -----------------------------------------------------
        // 2. Crear el usuario asociado a la persona
        // -----------------------------------------------------

        Usuario usuario = Usuario.builder()

                // Por ahora el correo también será el UserName.
                .login(registroRequest.getEmail())

                .email(registroRequest.getEmail())

                // La contraseña nunca se guarda directamente.
                .clave(
                        passwordEncoder.encode(
                                registroRequest.getPassword()
                        )
                )

                .fechaCreacion(LocalDateTime.now())

                .rol(
                        rolService.obtenerPorId(
                                registroRequest.getRolId()
                        )
                )

                // Se relaciona con el PersonId recién generado.
                .idPersona(personaGuardada.getIdPersona())

                .idEstado(1)

                .build();


        Usuario usuarioGuardado =
                userRepository.save(usuario);


        // -----------------------------------------------------
        // 3. Generar JWT para el usuario registrado
        // -----------------------------------------------------

        return UsuarioToken.builder()
                .token(jwtService.getToken(usuarioGuardado))
                .tipoToken("Bearer")
                .email(usuarioGuardado.getLogin())
                .build();
    }
}