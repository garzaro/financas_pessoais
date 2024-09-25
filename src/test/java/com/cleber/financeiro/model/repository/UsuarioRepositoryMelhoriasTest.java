package com.cleber.financeiro.model.repository;

import com.cleber.financeiro.model.entity.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

@RunWith(SpringRunner.class)
//@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryMelhoriasTest {
    
    /*Inicio teste com Dougllas Sousa (Udemy)*/
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TestEntityManager entityManager;
    
    @Test
    public void deveVerificarAExistenciaDeUmEmailNaBaseDeDados() {
        /*cenario*/
        Usuario usuarioDeTeste = criarUsuario();
        entityManager.persist(usuarioDeTeste);
        /*execução/ação*/
        boolean verficarSeExisteEmail = usuarioRepository
                .existsByEmail("laislabonita@gmail.com");
        /*verficação*/
        Assertions.assertThat(verficarSeExisteEmail).isTrue();
        
    }
    @Test
    public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {
        /*cenario*/
        /*usuarioRepository.deleteAll();*/
        boolean verificarSeExisteUsuarioCadastradoComEmail = usuarioRepository
                .existsByEmail("laislabonita@gmail.com");
        /*verificação*/
        Assertions.assertThat(verificarSeExisteUsuarioCadastradoComEmail).isFalse();
    }
    @Test
    public void devePersistirUsuarioNaBaseDeDados(){
        /*cenario*/
        Usuario persistindoUsuario = criarUsuario();
        /*ação*/
        Usuario usuarioPersistido = usuarioRepository.save(persistindoUsuario);
        /*verificação*/
        Assertions.assertThat(usuarioPersistido.getId()).isNotNull();
    }
    @Test
    public void deveBuscarUmUsuarioPeloEmail(){
        /*cenario*/
        Usuario salvandoUsuario = criarUsuario();
        usuarioRepository.save(salvandoUsuario);
        /*ação*/
        Optional<Usuario> pesquisarUsuarioPeloEmail = usuarioRepository
                .findByEmail("laislabonita@gmail.com");
        /*verificação*/
        Assertions.assertThat(pesquisarUsuarioPeloEmail.isPresent()).isTrue();
    }
    @Test
    public void deveRetornarVazioAoBuscarUsuarioPorEmailQueNaoExisteNaBase() {
        /*cenario*/
        /*usuarioRepository.deleteAll();*/
        /*ação*/
        Optional<Usuario> usuarioInexistente =usuarioRepository
                .findByEmail("laislabonita@gmail.com");
        /*verificação*/
        Assertions.assertThat(usuarioInexistente.isPresent()).isFalse();
    }

    private static Usuario criarUsuario(){
        return Usuario.builder()
                .nomeCompleto("Madonna da Silva")
                .nomeUsuario("La isla Bonita")
                .cadastroPessoaFisica("123.456.789.00")
                .email("laislabonita@gmail.com")
                .senha("senha")
                .dataCadastro(LocalDate.now())
                .build();
    }
}
