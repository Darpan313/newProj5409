package com.service.api;

abstract class GenericAPIResponse {

    private int status = 200;
    private String message = "SUCCESS";
    private int requestId = -1;

    public int getStatus() {
        return this.status;
    }

    public Object getMessage() {
        return this.message;
    }

    public int getRequestId() {return this.requestId;}

}