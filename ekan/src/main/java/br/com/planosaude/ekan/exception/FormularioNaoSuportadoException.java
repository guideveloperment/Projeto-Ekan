package br.com.planosaude.ekan.exception;

public class FormularioNaoSuportadoException extends RuntimeException {
    public FormularioNaoSuportadoException(String message) {
        super(message);
    }
}
