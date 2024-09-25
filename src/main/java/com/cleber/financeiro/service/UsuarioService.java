package com.cleber.financeiro.service;

import com.cleber.financeiro.model.entity.Usuario;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public interface UsuarioService {

    Usuario autenticarUsuario(String email, String senha);
    
    Usuario salvarUsuario(Usuario usuario);
    
    void validarEmail(String email);
    
    Optional<Usuario> obterUsuarioPorId(Long id);
}
