package org.eclipse.basyx.wrapper.exception;

/**
 * Exception that is thrown when the wrapper cannot request a value from its datasource
 * 
 * @author espen
 *
 */
public class WrapperRequestException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public WrapperRequestException(String message) {
        super(message);
    }

    public WrapperRequestException(String message, Throwable error) {
        super(message, error);
    }

}