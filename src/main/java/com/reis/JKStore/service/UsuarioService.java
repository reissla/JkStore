package com.reis.JKStore.service;

import com.reis.JKStore.domain.dtos.UsuarioCreateDto;
import com.reis.JKStore.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;


    public void salvarUsuario(UsuarioCreateDto dto){
        
    }
}
