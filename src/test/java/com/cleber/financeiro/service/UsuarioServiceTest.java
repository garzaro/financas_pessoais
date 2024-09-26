package com.cleber.financeiro.service;

import com.cleber.financeiro.exception.RegraDeNegocioException;
import com.cleber.financeiro.model.entity.Usuario;
import com.cleber.financeiro.model.repository.UsuarioRepository;
import com.cleber.financeiro.service.UsuarioService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {
    /*RESOLVER PROBLEMA DE DEPENDENCIA*/
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Test(expected = Test.None.class)
    public void deveValidarEmail(){
        /*cenario*/
        usuarioRepository.deleteAll();
        /*ação, sem verificação, só olha se existe o email*/
        usuarioService.validarEmail("cleber@gmail.com");
    }
    @Test(expected = RegraDeNegocioException.class)
    public void deveLancarErroAoValidarQuandoExistirEmaiLCadastrado(){
        /*cenario*/
        Usuario cadastrarEmail = Usuario.builder()
                .nomeUsuario("garzaro74")
                .email("cleber@gmail.com")
                .build();
        usuarioRepository.save(cadastrarEmail);
        /*ação*/
        usuarioService.validarEmail("cleber@gmail.com");
    }
    @Test(expected = Test.None.class)
    public void deveAutenticarUmUsuarioComSucesso(){
        /*cenario*/
        Usuario usuario = Usuario.builder()
                .email("clebergarzaro@gmail.com")
                .senha("senha123456")
                .build();
        /*ação*/
        Usuario salvarUsuario = usuarioRepository.save(usuario);

        // Verificar se o método autenticarUsuario retorna o usuário autenticado corretamente
        Usuario usuarioAutenticado = usuarioService.autenticarUsuario(salvarUsuario.getEmail(), salvarUsuario.getSenha());

        /*verificação*/
        Assertions.assertThat(usuarioAutenticado).isNotNull();
        Assertions.assertThat(usuarioAutenticado.getEmail()).isEqualTo(salvarUsuario.getEmail());
        Assertions.assertThat(usuarioAutenticado.getSenha()).isEqualTo(salvarUsuario.getSenha());
    }
    
}
