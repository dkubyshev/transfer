package com.revolut.exception;

/**
 * Required param missing exception
 */
public class RequiredParamMissingException extends Exception {

    private String paramName;


    public RequiredParamMissingException(String paramName) {
        super(String.format("Required param '%s' is missing", paramName));
        this.paramName = paramName;
    }
}
