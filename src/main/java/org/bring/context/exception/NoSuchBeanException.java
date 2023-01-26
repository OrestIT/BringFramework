package org.bring.context.exception;

public class NoSuchBeanException extends BaseApplicationContextException {
    public NoSuchBeanException(String message) {
        super(message);
    }
}
