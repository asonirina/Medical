package com.by.iason.config;

import com.by.iason.model.entity.Node;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;


/**
 * Created by iason
 * on 10/5/2017.
 */
public class SimpleContext implements SecurityContext {

    private String address;
    private Node node;

    public SimpleContext(String address, Node node) {
        this.address = address;
        this.node = node;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    @Override
    public Authentication getAuthentication() {
        return null;
    }

    @Override
    public void setAuthentication(Authentication authentication) {

    }
}
