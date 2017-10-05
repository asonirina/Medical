package com.by.iason.service;

import com.by.iason.config.BlockChain;
import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import multichain.command.MultiChainCommand;
import multichain.command.MultichainException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static multichain.command.GrantCommand.CONNECT;
import static multichain.command.GrantCommand.SEND;
import static multichain.command.GrantCommand.RECEIVE;
import static multichain.command.GrantCommand.ACTIVATE;

/**
 * Created by iason
 * on 10/4/2017.
 */

@Component
public class BlockchainService {

    public void grantPermissions(String address, Integer permission) throws MultichainException {
        cmd().getGrantCommand().grant(address, permission);
    }

    public String addNode(String host, String rpcPort) throws MultichainException {
        String nodeId = UUID.randomUUID().toString();
        cmd().getStreamCommand().publish("nodes", nodeId, HexBin.encode((host + ":" + rpcPort).getBytes()));
        return nodeId;
    }

    public void addAddress(String address, String nodeId) throws MultichainException {
        cmd().getStreamCommand().publish("addresses", address, HexBin.encode(nodeId.getBytes()));
    }

    private MultiChainCommand cmd() {
        return new MultiChainCommand(BlockChain.rpcHost(), BlockChain.rpcPort(), BlockChain.rpcUser(), BlockChain.rpcPwd());
    }

    public String newAddress() throws MultichainException {
        return cmd().getAddressCommand().getNewAddress();
    }
}
