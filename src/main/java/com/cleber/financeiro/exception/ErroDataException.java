package com.cleber.financeiro.exception;

public class ErroDataException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public ErroDataException(String erroData) {
        super(erroData);
    }
}
