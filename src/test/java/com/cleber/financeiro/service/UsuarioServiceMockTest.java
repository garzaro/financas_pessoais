package com.cleber.financeiro.service;

import com.cleber.financeiro.exception.ErroDeAutenticacao;
import com.cleber.financeiro.exception.RegraDeNegocioException;
import com.cleber.financeiro.model.entity.Usuario;
import com.cleber.financeiro.model.repository.UsuarioRepository;
import com.cleber.financeiro.service.impl.UsuarioServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceMockTest {

    @MockBean
    UsuarioRepository usuarioRepository;

    UsuarioService usuarioService;

    @Before
    public void setUp() {

        usuarioRepository = Mockito.mock(UsuarioRepository.class);
        usuarioService = new UsuarioServiceImpl(usuarioRepository);
    }

    @Test(expected = Test.None.class)
    public void deveValidarEmail() {
        /*cenario*/
        Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
        /*ação*/
        usuarioService.validarEmail("cleber@gmail.com");
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveLancarErroAoValidarQuandoExistirEmaiLCadastrado() {
        /*cenario*/
        Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(true);
        /*ação*/
        usuarioService.validarEmail("cleber@gmail.com");
    }

    @Test(expected = Test.None.class)
    public void deveAutenticarUmUsuarioComSucesso() {
        /*cenario*/
        String email = "cleber@gmail.com";
        String senha = "senha";

        Usuario criarUsuario = Usuario.builder()
                .email(email)
                .senha(senha)
                .build();
        Mockito.when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(criarUsuario));

        /*ação*/
        Usuario resultadoAutenticacao = usuarioService.autenticarUsuario(email, senha);
        /*verificacao*/
        Assertions.assertThat(resultadoAutenticacao).isNotNull();
    }

    @Test(expected = ErroDeAutenticacao.class)
    public void DeveLancarErroQuandoNaoEncontrarUsuarioCadastradoComOEmailInformado() {
        /*cenario*/
        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        String email = "cleber@gmail.com";
        String senha = "senha123";
        /*ação*/
        usuarioService.autenticarUsuario(email, senha);
    }

    @Test
    public void deveLancarErroQuandoSenhaNaoBater() {

        String senha = "senha";
        Usuario usuario = Usuario.builder()
                .email("cleber@gmail.com")
                .senha(senha)
                .build();
        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));
        /*ação*/
        Throwable exception = Assertions.catchThrowable(() -> usuarioService
                .autenticarUsuario("cleber@gmail.com", "123"));
        Assertions.assertThat(exception).isInstanceOf(ErroDeAutenticacao.class).hasMessage("Senha inválida");
    }
}
