package com.cleber.financeiro.service;

import com.cleber.financeiro.model.entity.Lancamento;
import com.cleber.financeiro.model.entity.StatusLancamento;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface LancamentoService {
VERIFCAR QUE RETORNA O LANCAMNETSERVICE NLLO AO EXECUTAR SALVARLANCAMNETO
    Lancamento salvarLancamento(Lancamento lancamento);

    Lancamento atualizarLancamento(Lancamento lancamento);
    
    void deletarLancamento(Lancamento lancamento);
    
    List<Lancamento> buscarLancamento(Lancamento lancamentoFiltro);
    
    void atualizarStatus(Lancamento lancamento, StatusLancamento status);
    
    void validarLancamento(Lancamento lancamento);
    
    Optional<Lancamento> obterLancamentoPorId(Long id);
}
