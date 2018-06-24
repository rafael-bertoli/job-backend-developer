package com.intelipost.webfront.exception;

/**
 *
 * @author Rafael
 */
public class ServiveUnavailableException extends Exception {

    /**
     * Creates a new instance of <code>ServiveUnavailableException</code>
     * without detail message.
     */
    public ServiveUnavailableException() {
    }

    /**
     * Constructs an instance of <code>ServiveUnavailableException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public ServiveUnavailableException(String msg) {
        super(msg);
    }
}
