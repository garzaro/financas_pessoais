package com.cleber.financeiro.exception;

public class ErroDeAutenticacao extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public ErroDeAutenticacao(String messageErroAutenticacao) {
        super(messageErroAutenticacao);
    }
}
