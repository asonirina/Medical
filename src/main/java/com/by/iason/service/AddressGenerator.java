package com.by.iason.service;

import com.by.iason.crypto.MessageEncoder;
import com.by.iason.crypto.PKManager;
import multichain.command.MultichainException;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;


/**
 * Created by iason
 * on 10/17/2017.
 */
public class AddressGenerator {

    public static String generate(MedChainManager manager, String pwd) throws MultichainException, GeneralSecurityException, UnsupportedEncodingException {

        String address = manager.newAddress();
        KeyPair keyPair = new PKManager().generateKeyPair();
        manager.addPublicKey(address, keyPair.getPublic().getEncoded());

        String privateKey = new MessageEncoder(pwd).encodeByte(keyPair.getPrivate().getEncoded());
        manager.addPrivateKey(address, privateKey.getBytes("UTF-8"));

        return address;

    }

}
