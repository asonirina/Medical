package com.by.iason.service;

import com.by.iason.crypto.MessageEncoder;
import com.by.iason.crypto.PKManager;
import multichain.command.MultichainException;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;


/**
 * Created by iason
 * on 10/17/2017.
 */

@Component
public class AddressGenerator {

    public String generate(MedChainManager defaultManager, MedChainManager manager, String pwd, int[] permissions) throws MultichainException, GeneralSecurityException, UnsupportedEncodingException {

        String address = manager.newAddress();
        defaultManager.grantPermissions(address, permissions);
        KeyPair keyPair = new PKManager().generateKeyPair();
        defaultManager.addPublicKey(address, keyPair.getPublic().getEncoded());

        String privateKey = new MessageEncoder(pwd).encodeByte(keyPair.getPrivate().getEncoded());
        defaultManager.addPrivateKey(address, privateKey.getBytes("UTF-8"));

        return address;

    }

}
