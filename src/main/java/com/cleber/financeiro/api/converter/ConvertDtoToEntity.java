package com.cleber.financeiro.api.converter;

import com.cleber.financeiro.api.dto.LancamentoDTO;
import com.cleber.financeiro.exception.RegraDeNegocioException;
import com.cleber.financeiro.model.entity.Lancamento;
import com.cleber.financeiro.model.entity.StatusLancamento;
import com.cleber.financeiro.model.entity.TipoLancamento;
import com.cleber.financeiro.model.entity.Usuario;
import com.cleber.financeiro.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConvertDtoToEntity {
    
    @Autowired
    private UsuarioService usuarioService;
    
    public Lancamento converterDtoParaEntidade(LancamentoDTO dto) {
        Lancamento lancamento = new Lancamento();
        lancamento.setId(dto.getId());
        lancamento.setDescricao(dto.getDescricao());
        lancamento.setAno(dto.getAno());
        lancamento.setMes(dto.getMes());
        lancamento.setDataCadastro(dto.getDataCadastro);
        lancamento.setValor(dto.getValor());
        
        Usuario receberUsuario = usuarioService.obterUsuarioPorId(dto.getUsuario())
                .orElseThrow(() -> new RegraDeNegocioException(
                        "Usuario não encontrado com o id " + "(" + dto.getUsuario() + ")"));
        lancamento.setUsuario(receberUsuario);
        
        if (dto.getTipo() != null) {
            lancamento.setTipoLancamento(TipoLancamento.valueOf(dto.getTipo()));
        }
        if (dto.getStatus() != null) {
            lancamento.setStatusLancamento(StatusLancamento.valueOf(dto.getStatus()));
        }
        return lancamento;
    }
}

