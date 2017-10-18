package com.by.iason.config;

import com.by.iason.model.entity.Node;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import java.security.PrivateKey;


/**
 * Created by iason
 * on 10/5/2017.
 */
public class SimpleContext implements SecurityContext {

    private String address;
    private Node node;
    private PrivateKey privateKey;

    public SimpleContext(String address, Node node, PrivateKey privateKey) {
        this.address = address;
        this.node = node;
        this.privateKey = privateKey;
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

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    @Override
    public Authentication getAuthentication() {
        return null;
    }

    @Override
    public void setAuthentication(Authentication authentication) {

    }
}
