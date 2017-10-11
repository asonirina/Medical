package com.by.iason.model.request;

import com.by.iason.model.entity.Node;

import java.util.List;

/**
 * Created by iason
 * on 10/6/2017.
 */
public abstract class AbstractRequest<T> {
    private T data;

    private Node node;

    public AbstractRequest(T data, Node node) {
        this.data = data;
        this.node = node;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
