package com.cleber.financeiro.exception;

public class ValorInvalidoException extends RuntimeException{

    public ValorInvalidoException(String valorInvalido){
        super(valorInvalido);
    }
}
