package com.angeles.demo.controller;

import com.angeles.demo.dto.AuthResponse;
import com.angeles.demo.dto.LoginRequest;
import com.angeles.demo.dto.RegisterRequest;
import com.angeles.demo.entity.Usuario;
import com.angeles.demo.security.JwtTokenProvider;
import com.angeles.demo.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private final JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        // 1. Autenticar con Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        // 2. Establecer contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Generar Token JWT
        String token = tokenProvider.generarToken(authentication);

        // 4. Obtener rol para que el frontend sepa a dónde redirigir
        String rol = authentication.getAuthorities().iterator().next().getAuthority();

        return ResponseEntity.ok(new AuthResponse(token, rol));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        // Verificar si ya existe el email (básico)
        if(usuarioService.buscarPorEmail(registerRequest.getEmail()).isPresent()){
            return ResponseEntity.badRequest().build(); // O manejar una excepción personalizada
        }

        // Crear nueva entidad Usuario desde el DTO
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(registerRequest.getNombre());
        nuevoUsuario.setEmail(registerRequest.getEmail());
        nuevoUsuario.setPassword(registerRequest.getPassword()); // El servicio lo encriptará
        nuevoUsuario.setRol(registerRequest.getRol());

        usuarioService.registrar(nuevoUsuario);

        // Auto-login después del registro (Opcional, pero útil para UX móvil)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registerRequest.getEmail(),
                        registerRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generarToken(authentication);

        return ResponseEntity.ok(new AuthResponse(token, registerRequest.getRol().name()));
    }
}