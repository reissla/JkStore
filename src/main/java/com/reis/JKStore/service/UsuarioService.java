package com.reis.JKStore.service;

import com.reis.JKStore.domain.Role;
import com.reis.JKStore.domain.Usuario;
import com.reis.JKStore.domain.dtos.UsuarioCreateDto;
import com.reis.JKStore.mappers.UsuarioMapper;
import com.reis.JKStore.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioMapper usuarioMapper;

    public void salvarUsuario(UsuarioCreateDto dto){

        if(verificarUsuarioExistente(dto.login())){
            throw new RuntimeException();
        }

        try {
            Usuario usuario = usuarioMapper.UsuarioCreateDtoToUsuario(dto);
            String encryptedPassword = new BCryptPasswordEncoder().encode(dto.senha());

            usuario.setSenha(encryptedPassword);
            usuario.setRole(Role.USUARIO);
            usuario.setDataCriacao(LocalDate.now());

            usuarioRepository.save(usuario);

        } catch (Exception e){
            throw new RuntimeException(dto.nome());
        }

    }

    public void excluirUsuarioPorId(Long id){
        if(id == null){
            throw new RuntimeException();
        }
        usuarioRepository.deleteById(id);
    }

    public boolean verificarUsuarioExistente(String usuarioLogin){
        if(usuarioLogin == null){
            throw new RuntimeException();
        }
        return usuarioRepository.findUserByLogin(usuarioLogin).isPresent();
    }

    @Transactional(readOnly = true)
    public Usuario usuarioLogado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String login = ((UserDetails) principal).getUsername();
            return procurarPorId(login);
        }

        throw new EntityNotFoundException();
    }

    public Usuario procurarPorId(String login){
        return usuarioRepository.findUserByLogin(login).orElseThrow(() -> new EntityNotFoundException("Usuário com id informado não foi encontrado."));
    }
}
