package com.cleber.financeiro.exception;

public class RegraDeNegocioException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public RegraDeNegocioException(String mensagemDeErro) {
        super(mensagemDeErro);
    }
}
