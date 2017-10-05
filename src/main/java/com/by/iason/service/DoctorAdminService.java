package com.by.iason.service;

import com.by.iason.model.CreateDoctorRequest;
import com.by.iason.model.Permissions;
import multichain.command.MultichainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by iason
 * on 10/5/2017.
 */

@Component
public class DoctorAdminService {

    @Autowired
    private BlockchainService blockchainService;

    public void createDoctor(CreateDoctorRequest request) throws MultichainException {
        String address = blockchainService.newAddress();
        blockchainService.grantPermissions(address, Permissions.DOCTOR);
//        blockchainService.addAddress();
    }

//    public void requestAccess
}
