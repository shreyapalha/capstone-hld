package com.prat.capstonehld.dto;


public class ApiSingleRequest<T> {
    private T request;

    public ApiSingleRequest() {
    }

    public ApiSingleRequest(T request) {
        this.request = request;
    }

    public T getRequest() {
        return request;
    }

    public void setRequest(T request) {
        this.request = request;
    }
}
