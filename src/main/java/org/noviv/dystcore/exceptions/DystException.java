package org.noviv.dystcore.exceptions;

public class DystException extends RuntimeException {

    public DystException(String msg) {
        super(msg);
    }
    
    public DystException(Throwable t) {
        super(t);
    }
}
