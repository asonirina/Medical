package com.by.iason.service;

import com.by.iason.config.BlockChain;
import com.by.iason.model.CreateClinicRequest;
import com.by.iason.model.Permissions;
import multichain.command.MultichainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by iason
 * on 10/3/2017.
 */
@Component
public class MinistryAdminService {

    @Autowired
    private BlockchainService blockchainService;

    public void createClinic(CreateClinicRequest request) throws MultichainException {
        blockchainService.grantPermissions(request.getAddress(), Permissions.DOCTOR_ADMIN);
        String nodeId = blockchainService.addNode(request.getHost(), request.getRpcPort());
        blockchainService.addAddress(request.getAddress(), nodeId);
    }
}
