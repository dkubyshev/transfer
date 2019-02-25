package com.revolut.response;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Response bean
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseBean {

    private Status status;

    private String msg;

    public enum Status {
        SUCCESS,
        BAD_REQUEST,
        NOT_FOUND,
        INSUFFICIENT_FUNDS,
        UNKNOWN
    }

    public ResponseBean(Status status) {
        this.status = status;
    }

    public ResponseBean(Status status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
