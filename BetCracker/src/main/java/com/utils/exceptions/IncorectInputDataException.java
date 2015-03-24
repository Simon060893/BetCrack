package com.utils.exceptions;

/**
 * Created by Катя on 30.01.2015.
 */
public class IncorectInputDataException extends Exception {
    public IncorectInputDataException(String text) {
        super(text);
    }

    public IncorectInputDataException(String text, Exception innerEx) {
        super(text, innerEx);

    }
}
