package com.reis.JKStore.controller;

import com.reis.JKStore.domain.Usuario;
import com.reis.JKStore.domain.dtos.AuthenticationDTO;
import com.reis.JKStore.domain.dtos.UsuarioCreateDto;
import com.reis.JKStore.jwt.TokenService;
import com.reis.JKStore.repository.UsuarioRepository;
import com.reis.JKStore.service.UsuarioService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("http://localhost:5173")
public class AuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    @Autowired
    UsuarioService userService;
    @Autowired
    UsuarioRepository repository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO dto){
        try{
            var usernamePassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.senha());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((Usuario) auth.getPrincipal());
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + token)
                    .header("Access-Control-Expose-Headers", "Authorization")
                    .body(token);
        } catch(AuthenticationCredentialsNotFoundException exception){
            log.warn("BAD CREDENTIALS for username {}", dto.login());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> insertUser(@RequestBody @Valid UsuarioCreateDto dto){
        userService.salvarUsuario(dto);
        return ResponseEntity.ok("Usuário registrado com sucesso!");
    }
}
