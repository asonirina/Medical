package com.by.iason.model.entity;

/**
 * Created by iason
 * on 10/4/2017.
 */
public class Node {
    private String rpcHost;
    private String rpcPort;
    private String rpcUser;
    private String rpcPwd;

    public String getRpcHost() {
        return rpcHost;
    }

    public void setRpcHost(String rpcHost) {
        this.rpcHost = rpcHost;
    }

    public String getRpcPort() {
        return rpcPort;
    }

    public void setRpcPort(String rpcPort) {
        this.rpcPort = rpcPort;
    }

    public String getRpcUser() {
        return rpcUser;
    }

    public void setRpcUser(String rpcUser) {
        this.rpcUser = rpcUser;
    }

    public String getRpcPwd() {
        return rpcPwd;
    }

    public void setRpcPwd(String rpcPwd) {
        this.rpcPwd = rpcPwd;
    }
}
