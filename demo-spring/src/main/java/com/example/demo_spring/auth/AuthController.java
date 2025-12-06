// src/main/java/com/example/demo_spring/auth/AuthController.java
package com.example.demo_spring.auth;

import com.example.demo_spring.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
//  importa dependencias necesarias
@RestController
@RequestMapping("/api/auth")
public class AuthController { // Controlador para autenticación y registro

    @Autowired
    private AuthenticationManager authenticationManager; // Gestor de autenticación

    @Autowired
    private AuthService authService; // Servicio de autenticación y registro

    @Autowired
    private JwtUtil jwtUtil;// Utilidad para manejo de JWT

    // Registro de nuevo usuario 
    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
        try {
            Usuario nuevo = authService.registrarUsuario(usuario);
            return ResponseEntity.ok("Usuario registrado exitosamente: " + nuevo.getCorreo());// Respuesta exitosa
        } catch (Exception e) { 
            return ResponseEntity.badRequest().body(e.getMessage()); // Manejo de errores
        }
    }

    // Login de usuario
    @PostMapping("/login") 
public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) { // Solicitud de login
    try {
        Authentication authentication = authenticationManager.authenticate( // Autenticación
            new UsernamePasswordAuthenticationToken( // Token de autenticación
                loginRequest.getCorreo(),// correo del usuario
                loginRequest.getContrasena() // contraseña del usuario
            )
        ); // Token de autenticación

        SecurityContextHolder.getContext().setAuthentication(authentication); // Establece el contexto de seguridad

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();// Detalles del usuario autenticado
        String jwt = jwtUtil.generateToken(userDetails);// Genera el token JWT

        return ResponseEntity.ok(new JwtResponse(jwt));// Respuesta exitosa con el token

    } catch (Exception e) {
        return ResponseEntity.badRequest().body("Credenciales inválidas");// Manejo de errores
    }
}


    // Clase interna para recibir el JSON de login
    public static class LoginRequest { // Solicitud de login
        private String correo;// Correo del usuario
        private String contrasena; // Contraseña del usuario

        // Getters y Setters
        public String getCorreo() { return correo; } // Obtiene el correo del usuario
        public void setCorreo(String correo) { this.correo = correo; }
        public String getContrasena() { return contrasena; } // Obtiene la contraseña del usuario   
        public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    }

    // Clase interna para la respuesta del token
    public static class JwtResponse { // Respuesta con el token JWT
        private String token; // Token JWT

        public JwtResponse(String token) { //Constructor que recibe el token
            this.token = token; //
        }

        public String getToken() { return token; } // Obtiene el token JWT
        public void setToken(String token) { this.token = token; } // Establece el token JWT
    }
} // Fin de la clase AuthController