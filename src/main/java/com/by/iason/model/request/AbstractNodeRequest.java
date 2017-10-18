package com.by.iason.model.request;

import com.by.iason.model.entity.Node;

/**
 * Created by iason
 * on 10/13/2017.
 */
public class AbstractNodeRequest<T> extends AbstractRequest<T> {

    private Node node;

    public AbstractNodeRequest(T data, Node node) {
        super(data);
        this.node = node;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
