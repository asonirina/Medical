package com.by.iason.service;

import com.by.iason.Utils;
import com.by.iason.crypto.MessageEncoder;
import com.by.iason.crypto.PKManager;
import com.by.iason.model.entity.Document;
import com.by.iason.model.request.CreateDocumentRequest;
import com.by.iason.model.response.DocumentResponse;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.UUID;

/**
 * Created by iason
 * on 10/17/2017.
 */

@Component
public class DoctorService {

    public DocumentResponse writeToPatient(CreateDocumentRequest request, String patientId) throws Exception {
        MedChainManager doctorManager = new MedChainManager(Utils.context().getNode());
        PKManager pkManager = new PKManager();

        try {

            Document doc = request.getData();
            doc.setId(UUID.randomUUID().toString());

            String pwd = RandomStringUtils.randomNumeric(5);
            String encodedMessage = new MessageEncoder(pwd).encode(doc.getData());

            doctorManager.writeToPatient(patientId, doc.getId(), encodedMessage);

            PublicKey pk = pkManager.readPublicKey(doctorManager.getPublicKey(patientId));
            String encryptedPwd = pkManager.encrypt(pk, pwd);

            doctorManager.writeToAccess(patientId, doc.getId(), encryptedPwd);

            return new DocumentResponse(Lists.newArrayList(doc));
        }finally {
            doctorManager.kill();
        }

    }
}
