package br.com.luisrb.desafiovr.exception;

public class LimiteCreditoExcedidoException extends RuntimeException {
    public LimiteCreditoExcedidoException(String message) {
        super(message);
    }
}