package com.by.iason.service;

import com.by.iason.Utils;
import com.by.iason.config.BlockChain;
import com.by.iason.exception.AddressNotFoundException;
import com.by.iason.exception.ClinicNotFoundException;
import com.by.iason.model.Permissions;
import com.by.iason.model.entity.Admin;
import com.by.iason.model.entity.Clinic;
import com.by.iason.model.entity.Patient;
import com.by.iason.model.request.CreateAdminRequest;
import com.by.iason.model.response.AdminResponse;
import com.by.iason.model.response.ClinicResponse;
import com.by.iason.model.response.PatientResponse;
import com.google.common.collect.Lists;
import multichain.command.MultichainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by iason
 * on 10/3/2017.
 */
@Component
public class MinistryAdminService {

    public void approveClinic(String clinicId) throws MultichainException, ClinicNotFoundException{
        MedChainManager manager = new MedChainManager(Utils.context().getNode());
        if(!manager.isClinicExists(clinicId)) {
            throw new ClinicNotFoundException();
        }
        manager.grantPermissions(clinicId, Permissions.DOCTOR_ADMIN);
    }

    public ClinicResponse getClinics() throws MultichainException {
        MedChainManager manager = new MedChainManager(Utils.context().getNode());
        return new ClinicResponse(manager.getClinics());
    }

    public AdminResponse registerAdmin(CreateAdminRequest request) throws MultichainException, AddressNotFoundException {

        MedChainManager manager = new MedChainManager(BlockChain.defaultNode());

        String address = manager.newAddress();
        manager.grantPermissions(address, Permissions.MINISTRY_ADMIN);

        Admin admin  = request.getData();
        admin.setId(address);
        manager.addAdmin(admin);

        String nodeId = "default_node";
        manager.addAddress(address, nodeId);

        return new AdminResponse(Lists.newArrayList(admin));
    }
}
