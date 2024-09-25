package com.cleber.financeiro.service.impl;

import com.cleber.financeiro.exception.RegraDeNegocioException;
import com.cleber.financeiro.model.entity.Lancamento;
import com.cleber.financeiro.model.entity.StatusLancamento;
import com.cleber.financeiro.model.repository.LancamentoRepository;
import com.cleber.financeiro.service.LancamentoService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LancamentoServiceImpl implements LancamentoService {
    
    private LancamentoRepository lancamentoRepository;
    
    public LancamentoServiceImpl(LancamentoRepository lancamentoRepository) {
        this.lancamentoRepository = lancamentoRepository;
    }
    
    @Override
    @Transactional
    public Lancamento salvarLancamento(Lancamento lancamento) {

        validarLancamento(lancamento);
        lancamento.setStatusLancamento(StatusLancamento.PENTENDE);
        return lancamentoRepository.save(lancamento);
    }
    
    @Override
    @Transactional
    public Lancamento atualizarLancamento(Lancamento lancamento) {

        Objects.requireNonNull(lancamento.getId());
        validarLancamento(lancamento);
        return lancamentoRepository.save(lancamento);
    }
    
    @Override
    @Transactional
    public void deletarLancamento(Lancamento lancamento) {

        Objects.requireNonNull(lancamento.getId());
        lancamentoRepository.delete(lancamento);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Lancamento> buscarLancamento(Lancamento lancamentoFiltro) {
        Example example = Example.of(lancamentoFiltro, ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return lancamentoRepository.findAll(example);
    }
    
    @Override
    public void atualizarStatus(Lancamento lancamento, StatusLancamento status) {
        lancamento.setStatusLancamento(status);
        atualizarLancamento(lancamento);
    }
    
    @Override
    public void validarLancamento(Lancamento lancamento) {

        if (lancamento.getDescricao() == null || lancamento.getDescricao().trim().equals("")){
            throw new RegraDeNegocioException("Informar uma descrição válida.");
        }
        if (lancamento.getMes() == null || lancamento.getMes() < 1 || lancamento.getMes() >  12){
            throw new RegraDeNegocioException("Informar um mês válido.");
        }
        if (lancamento.getAno() == null || lancamento.getAno().toString().length() != 4 ){
            throw new RegraDeNegocioException("Informar um ano válido");
        }
        if (lancamento.getUsuario() == null || lancamento.getUsuario().getId() == null){
            throw new RegraDeNegocioException("Informar um Usuário.");
        }

        if (lancamento.getValor() == null || lancamento.getValor().compareTo(BigDecimal.ZERO) < 1){
            throw new RegraDeNegocioException("Informe um valor válido.");
        }
        if (lancamento.getTipoLancamento() == null){
            throw new RegraDeNegocioException("Informar um tipo de lancamento");
        }
    }
    
    @Override
    public Optional<Lancamento> obterLancamentoPorId(Long id) {
        return lancamentoRepository.findById(id);
    }
   
}

