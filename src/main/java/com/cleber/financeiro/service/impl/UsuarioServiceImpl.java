package com.cleber.financeiro.service.impl;

import com.cleber.financeiro.exception.RegraDeNegocioException;
import com.cleber.financeiro.exception.ErroDeAutenticacao;
import com.cleber.financeiro.model.entity.Usuario;
import com.cleber.financeiro.model.repository.UsuarioRepository;
import com.cleber.financeiro.service.UsuarioService;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@NoArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;
    
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        super();
        this.usuarioRepository = usuarioRepository;
    }
    
    @Override
    public Usuario autenticarUsuario(String email, String senha) {
        
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (!usuario.isPresent()) {
            throw new ErroDeAutenticacao("Usuario não encontrado pelo email informado");
        }
        if (!usuario.get().getSenha().equals(senha)) {
            throw new ErroDeAutenticacao("Senha inválida");
        }
        return usuario.get();
    }
    
    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        validarEmailAndCpf(usuario.getEmail(), usuario.getCpf());
        return usuarioRepository.save(usuario);
    }
    
    @Override
    public void validarEmailAndCpf(String email, String cpf) {
        /*ver se o email existe*/
        boolean existeUsuarioComEmail = usuarioRepository.existsByEmail(email);
        boolean existeUsuarioComCpf = usuarioRepository.existsByCpf(cpf);
        
        if (existeUsuarioComEmail) {
            throw new RegraDeNegocioException("Já existe um usuário com esse email.");
        }
        if (existeUsuarioComCpf) {
            throw new RegraDeNegocioException("Já existe um usuário com esse CPF");
        }
    }
    
    @Override
    public Optional<Usuario> obterUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }
}
