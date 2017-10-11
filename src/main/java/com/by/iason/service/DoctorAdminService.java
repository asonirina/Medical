package com.by.iason.service;

import com.by.iason.Utils;
import com.by.iason.config.BlockChain;
import com.by.iason.exception.AddressNotFoundException;
import com.by.iason.model.entity.Doctor;
import com.by.iason.model.entity.Patient;
import com.by.iason.model.request.CreateDoctorRequest;
import com.by.iason.model.entity.Node;
import com.by.iason.model.Permissions;
import com.by.iason.model.entity.Clinic;
import com.by.iason.model.request.CreateClinicRequest;
import com.by.iason.model.request.CreatePatientRequest;
import com.by.iason.model.response.ClinicResponse;
import com.by.iason.model.response.DoctorResponse;
import com.by.iason.model.response.PatientResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import multichain.command.MultichainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by iason
 * on 10/5/2017.
 */

@Component
public class DoctorAdminService {

    public DoctorResponse createDoctor(CreateDoctorRequest request) throws MultichainException {
        MedChainManager manager = new MedChainManager(Utils.context().getNode());

        String address = manager.newAddress();
        manager.grantPermissions(address, Permissions.DOCTOR);
        Doctor doctor = request.getData();
        doctor.setId(address);
        manager.addDoctor(doctor);

        return new DoctorResponse(Lists.newArrayList(doctor));
    }

    public PatientResponse createPatient(CreatePatientRequest request) throws MultichainException, AddressNotFoundException {
        MedChainManager manager = new MedChainManager(Utils.context().getNode());
        String address = manager.newAddress();
        manager.grantPermissions(address, Permissions.PATIENT);
        manager.createPatientStream(address);
        Patient patient = request.getData();
        patient.setId(address);
        manager.addPatient(patient);

        String nodeId = manager.getNodeId(Utils.context().getAddress());
        manager.addAddress(address, nodeId);

        return new PatientResponse(Lists.newArrayList(patient));
    }

    public ClinicResponse registerClinic(CreateClinicRequest request) throws MultichainException, JsonProcessingException  {
        MedChainManager manager = new MedChainManager(BlockChain.defaultNode());

        manager.subscribeTo(request.getNode(), "nodes", "addresses", "clinics", "doctors", "patients");

        String nodeId = manager.addNode(request.getNode());
        manager.addAddress(Utils.context().getAddress(), nodeId);
        Clinic clinic = request.getData();
        clinic.setId(Utils.context().getAddress());
        manager.addClinic(clinic);

        return new ClinicResponse(Lists.newArrayList(clinic));
    }
}
