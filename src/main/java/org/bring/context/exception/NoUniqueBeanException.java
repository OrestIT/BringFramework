package org.bring.context.exception;

public class NoUniqueBeanException extends BaseApplicationContextException {
    public NoUniqueBeanException(String message) {
        super(message);
    }
}
