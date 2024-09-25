package com.cleber.financeiro.service;

import com.cleber.financeiro.model.entity.Lancamento;
import com.cleber.financeiro.model.entity.StatusLancamento;

import java.util.List;
import java.util.Optional;

public interface LancamentoService {

    Lancamento salvarLancamento(Lancamento lancamento);

    Lancamento atualizarLancamento(Lancamento lancamento);
    
    void deletarLancamento(Lancamento lancamento);
    
    List<Lancamento> buscarLancamento(Lancamento lancamentoFiltro);
    
    void atualizarStatus(Lancamento lancamento, StatusLancamento status);
    
    void validarLancamento(Lancamento lancamento);
    
    Optional<Lancamento> obterLancamentoPorId(Long id);
}
