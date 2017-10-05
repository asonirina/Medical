package com.by.iason.config;

import com.google.common.base.Predicate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.context.SecurityContext;

import java.util.Collection;
import java.util.List;

/**
 * Created by iason
 * on 10/5/2017.
 */
public class SimpleContext implements SecurityContext {

    private String address;
    private String node;

    public SimpleContext(String address, String node) {
        this.address = address;
        this.node = node;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
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
