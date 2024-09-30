package com.cleber.financeiro.model.repository;

import com.cleber.financeiro.model.entity.Usuario;
import com.cleber.financeiro.model.repository.UsuarioRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
//@ActiveProfiles("test")
public class UsuarioRepositoryTest {

    /*teste, com Dougllas Sousa (Udemy)*/

    @Autowired
    UsuarioRepository usuarioRepository;

    @Test
    public void deveVerificarAExistenciaDeUmEmailNaBaseDeDados() {
        /*cenario*/
        Usuario usuarioDeTeste = Usuario.builder()
                .nomeCompleto("Madonna da Silva")
                .email("laislabonita@gmail.com")
                .build();
        usuarioRepository.save(usuarioDeTeste);
        
        /*execução/ação*/
       boolean verficarSeExisteEmail = usuarioRepository.existsByEmail("laislabonita@gmail.com");
       
       /*verficação*/
       Assertions.assertThat(verficarSeExisteEmail).isTrue();
       
    }
    
    @Test
    public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {
       /*cenario, não deve existir email na base*/
        usuarioRepository.deleteAll();
        boolean verificarSeExisteUsuarioCadastradoComEmail =usuarioRepository
                .existsByEmail("laislabonita@gmail.com");
        /*verificação*/
        Assertions.assertThat(verificarSeExisteUsuarioCadastradoComEmail).isFalse();
    }
    
    @Test
    public void devePersistirUsuarioNaBaseDeDados(){
       /*cenario*/
        Usuario persistindoUsuario = Usuario.builder()
                .nomeCompleto("Madonna da Silva")
                .nomeUsuario("laislabonita")
                .email("laislabonita@gmail.com")
                .senha("senha")
                .dataCadastro(LocalDate.now())
                .build();
        /*ação*/
        Usuario usuarioPersistido = usuarioRepository.save(persistindoUsuario);
        
        /*verificação*/
        Assertions.assertThat(usuarioPersistido.getId()).isNotNull();
    }
    
    @Test
    public void deveBuscarUmUsuarioPeloEmail(){
        /*cenario*/
        Usuario salvandoUsuario = Usuario.builder()
                .nomeUsuario("laislabonita")
                .email("laislabonita@gmail.com")
                .build();
        usuarioRepository.save(salvandoUsuario);
        /*ação*/
        Optional<Usuario> pesquisarUsuarioPeloEmail = usuarioRepository
                .findByEmail("laislabonita@gmail.com");
        /*verificação*/
        Assertions.assertThat(pesquisarUsuarioPeloEmail.isPresent()).isTrue();
    }
    
    @Test
    public void deveRetornarVazioAoBuscarUsuarioPorEmailQueNaoExisteNaBase() {
        /*cenario - nao presença de email*/
        usuarioRepository.deleteAll();
        /*ação*/
        Optional<Usuario> usuarioInexistente =usuarioRepository
                .findByEmail("laislabonita@gmail.com");
        /*verificação*/
        Assertions.assertThat(usuarioInexistente.isPresent()).isFalse();
    }
}
