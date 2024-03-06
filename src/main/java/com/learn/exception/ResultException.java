package com.learn.exception;

/**
 * @author sksingh created on 06/03/24
 */
public class ResultException extends Exception {

    public ResultException() {
    }

    public ResultException(String message) {
        super(message);
    }

    public ResultException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResultException(Throwable cause) {
        super(cause);
    }
}
