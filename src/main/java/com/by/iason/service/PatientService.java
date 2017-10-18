package com.by.iason.service;

import com.by.iason.Utils;
import com.by.iason.crypto.MessageEncoder;
import com.by.iason.crypto.PKManager;
import com.by.iason.model.entity.Document;
import com.by.iason.model.response.DocumentResponse;
import com.google.common.collect.Lists;
import multichain.command.MultichainException;
import org.springframework.stereotype.Component;

/**
 * Created by iason
 * on 10/17/2017.
 */

@Component
public class PatientService {

    public DocumentResponse listDocuments() throws MultichainException {
        MedChainManager patientManager = new MedChainManager(Utils.context().getNode());
        try {
            return new DocumentResponse(Lists.newArrayList(patientManager.getPatientDocuments(Utils.context().getAddress())));
        } finally {
            patientManager.kill();
        }
    }

    public DocumentResponse getDocument(String docId) throws Exception {
        MedChainManager patientManager = new MedChainManager(Utils.context().getNode());
        PKManager pkManager = new PKManager();
        try {
            String encryptedMessage = patientManager.getDocument(Utils.context().getAddress(), docId);
            String encryptedPassword = patientManager.readAccess(Utils.context().getAddress(), docId);

            String pwd = pkManager.decrypt(Utils.context().getPrivateKey(), encryptedPassword);
            String data = new MessageEncoder(pwd).decode(encryptedMessage);

            Document doc = new Document();
            doc.setId(docId);
            doc.setData(data);
            return new DocumentResponse(Lists.newArrayList(doc));
        } finally {
            patientManager.kill();
        }
    }
}
