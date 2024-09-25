package com.cleber.financeiro.model.repository;

import com.cleber.financeiro.model.entity.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
