package com.cleber.financeiro.service;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.cleber.financeiro.exception.RegraDeNegocioException;
import com.cleber.financeiro.model.entity.Usuario;
import com.cleber.financeiro.model.repository.UsuarioRepository;

/*RESOLVER PROBLEMA DE DEPENDENCIA*/


@SpringBootTest
@RunWith(SpringRunner.class)
/*@ActiveProfiles("test")*/
public class UsuarioServiceMelhoriasTest {
    
    @Autowired
    UsuarioService usuarioService;
    
    @Autowired
    UsuarioRepository usuarioRepository;
    
    @Test(expected = Test.None.class)
    public void deveValidarEmail(){
        /*cenario*/
        usuarioRepository.deleteAll();
        /*ação*/
        usuarioService.validarEmail("cleber@gmail.com");
    }
    @Test(expected = RegraDeNegocioException.class)
    public void deveLancarErroAoValidarQuandoExistirEmaiLCadastrado(){
        /*cenario*/
        Usuario cadastrarEmail = criarUsuario();
        /*salvar*/
        usuarioRepository.save(cadastrarEmail);
        /*ação*/
        usuarioService.validarEmail("cleber@gmail.com");
    }
    @Test(expected = Test.None.class)
    public void deveAutenticarUmUsuarioComSucesso(){
        /*cenario*/
        Usuario persistirUsuario = criarUsuario();
        /*ação*/
        Usuario usuarioSalvo = usuarioRepository.save(persistirUsuario);
        Usuario usuarioAutenticado = usuarioService.autenticarUsuario(usuarioSalvo.getEmail(), usuarioSalvo.getSenha());
        /*verificação*/
        Assertions.assertThat(usuarioAutenticado).isNotNull();
        Assertions.assertThat(usuarioAutenticado.getEmail()).isEqualTo("cleber@gmail.com");
        Assertions.assertThat(usuarioAutenticado.getSenha()).isEqualTo("senha");
    }

    public static Usuario criarUsuario(){
        return Usuario.builder()
                .id(1l)
                .nomeCompleto("Cleber")
                .nomeUsuario("garzaro")
                .cadastroPessoaFisica("123.456.789-00")
                .email("cleber@gmail.com")
                .senha("senha")
                .dataCadastro(LocalDate.now())
                .build();
    
    }
    
    
}
