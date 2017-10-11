package com.by.iason.config;

import com.by.iason.model.entity.Node;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by iason
 * on 10/3/2017.
 */
@Configuration("BlockChainConfig")
@ConfigurationProperties(locations = "classpath:application.properties", prefix = "blockchain", ignoreUnknownFields = true)
public class BlockChain {

    private static String name;
    private static String rpcHost;
    private static String rpcPort;

    private static String rpcUser;
    private static String rpcPwd;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        BlockChain.name = name;
    }

    public static String getRpcHost() {
        return rpcHost;
    }
    
    public static String rpcHost() {
        return getRpcHost();
    }

    public static void setRpcHost(String rpcHost) {
        BlockChain.rpcHost = rpcHost;
    }

    public static String getRpcPort() {
        return rpcPort;
    }

    public static String rpcPort() {
        return getRpcPort();
    }

    public static void setRpcPort(String rpcPort) {
        BlockChain.rpcPort = rpcPort;
    }

    public static String getRpcUser() {
        return rpcUser;
    }

    public static String rpcUser() {
        return getRpcUser();
    }

    public static void setRpcUser(String rpcUser) {
        BlockChain.rpcUser = rpcUser;
    }

    public static String getRpcPwd() {
        return rpcPwd;
    }

    public static String rpcPwd() {
        return getRpcPwd();
    }

    public static void setRpcPwd(String rpcPwd) {
        BlockChain.rpcPwd = rpcPwd;
    }

    public static Node defaultNode() {
        Node node =  new Node();
        node.setRpcHost(rpcHost());
        node.setRpcPort(rpcPort());
        node.setRpcUser(rpcUser());
        node.setRpcPwd(rpcPwd());
        return node;
    }
}
