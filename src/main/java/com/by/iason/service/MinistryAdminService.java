package com.by.iason.service;

import com.by.iason.Utils;
import com.by.iason.config.BlockChain;
import com.by.iason.exception.ClinicNotFoundException;
import com.by.iason.model.Permissions;
import com.by.iason.model.entity.Admin;
import com.by.iason.model.entity.Clinic;
import com.by.iason.model.request.CreateAdminRequest;
import com.by.iason.model.response.AdminResponse;
import com.by.iason.model.response.ClinicResponse;
import com.google.common.collect.Lists;
import multichain.command.MultichainException;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.by.iason.model.Streams.*;

/**
 * Created by iason
 * on 10/3/2017.
 */
@Component
public class MinistryAdminService {

    public void approveClinic(String clinicId) throws MultichainException, ClinicNotFoundException {
        MedChainManager manager = new MedChainManager(Utils.context().getNode());
        try {

            if (!manager.isClinicExists(clinicId)) {
                throw new ClinicNotFoundException();
            }
            manager.grantPermissions(clinicId, Permissions.DOCTOR_ADMIN);
        } finally {
            manager.kill();
        }
    }

    public ClinicResponse getClinics() throws MultichainException {
        MedChainManager manager = new MedChainManager(Utils.context().getNode());
        try {
            List<Clinic> clinics = manager.getClinics();

            return new ClinicResponse(clinics);
        } finally {
            manager.kill();
        }
    }

    public AdminResponse registerAdmin(CreateAdminRequest request, String pwd) throws Exception {

        MedChainManager adminManager = new MedChainManager(request.getNode());
        MedChainManager defaultManager = new MedChainManager(BlockChain.defaultNode());
        try {
            adminManager.subscribeTo(NODES, ADDRESSES, CLINICS, DOCTORS, PATIENTS, ADMINS, PRIVATE_KEYS, PUBLIC_KEYS, ACCESS);
            String address = AddressGenerator.generate(defaultManager, adminManager, pwd);

            defaultManager.grantPermissions(address, Permissions.MINISTRY_ADMIN);

            Admin admin = request.getData();
            admin.setId(address);
            defaultManager.addAdmin(admin);

            String nodeId = defaultManager.addNode(request.getNode());
            defaultManager.addAddress(address, nodeId);

            return new AdminResponse(Lists.newArrayList(admin));
        } finally {

            adminManager.kill();
            defaultManager.kill();
        }
    }
}
