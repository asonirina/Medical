package com.by.iason.model.request;

/**
 * Created by iason
 * on 10/6/2017.
 */
public abstract class AbstractRequest<T> {
    private T data;


    public AbstractRequest(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
