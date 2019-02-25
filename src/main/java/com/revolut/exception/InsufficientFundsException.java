package com.revolut.exception;

/**
 * Insufficient funds exception
 */
public class InsufficientFundsException extends Exception {

  public InsufficientFundsException(String message) {
    super(message);
  }

}
