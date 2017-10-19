package com.by.iason.service;

import com.by.iason.Utils;
import com.by.iason.config.BlockChain;
import com.by.iason.exception.NodeNotFoundException;
import com.by.iason.model.Permissions;
import com.by.iason.model.entity.Clinic;
import com.by.iason.model.entity.Doctor;
import com.by.iason.model.entity.Patient;
import com.by.iason.model.request.CreateClinicRequest;
import com.by.iason.model.request.CreateDoctorRequest;
import com.by.iason.model.request.CreatePatientRequest;
import com.by.iason.model.response.ClinicResponse;
import com.by.iason.model.response.DoctorResponse;
import com.by.iason.model.response.PatientResponse;
import com.google.common.collect.Lists;
import multichain.command.MultichainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.by.iason.model.Streams.*;

/**
 * Created by iason
 * on 10/5/2017.
 */

@Component
public class DoctorAdminService {

    @Autowired
    private AddressGenerator addressGenerator;

    public DoctorResponse createDoctor(CreateDoctorRequest request, String pwd) throws Exception {
        MedChainManager manager = new MedChainManager(Utils.context().getNode());
        MedChainManager defaultManager = new MedChainManager(BlockChain.defaultNode());

        try {
            String address = addressGenerator.generate(defaultManager, manager, pwd, Permissions.DOCTOR);
            Doctor doctor = request.getData();
            doctor.setId(address);
            manager.addDoctor(doctor);

            String nodeId = manager.getNodeId(Utils.context().getAddress());
            manager.addAddress(address, nodeId);

            return new DoctorResponse(Lists.newArrayList(doctor));
        } finally {
            manager.kill();
            defaultManager.kill();
        }
    }

    public PatientResponse createPatient(CreatePatientRequest request, String pwd) throws Exception {
        MedChainManager manager = new MedChainManager(Utils.context().getNode());
        MedChainManager defaultManager = new MedChainManager(BlockChain.defaultNode());
        try {
            String address = addressGenerator.generate(defaultManager, manager, pwd, Permissions.PATIENT);

            String streamName = manager.createPatientStream(address);
            manager.subscribeTo(streamName);

            Patient patient = request.getData();
            patient.setId(address);
            manager.addPatient(patient);

            String nodeId = manager.getNodeId(Utils.context().getAddress());
            manager.addAddress(address, nodeId);

            return new PatientResponse(Lists.newArrayList(patient));
        } finally {
            manager.kill();
            defaultManager.kill();
        }
    }

    public ClinicResponse registerClinic(CreateClinicRequest request, String pwd) throws Exception {
        MedChainManager defaultManager = new MedChainManager(BlockChain.defaultNode());
        MedChainManager clinicManager = new MedChainManager(request.getNode());
        try {
            clinicManager.subscribeTo(NODES, ADDRESSES, CLINICS, DOCTORS, PATIENTS, PRIVATE_KEYS, PUBLIC_KEYS, ACCESS);

            String nodeId = defaultManager.addNode(request.getNode());

            String address = addressGenerator.generate(defaultManager, clinicManager, pwd, Permissions.INIT);
            defaultManager.addAddress(address, nodeId);
            Clinic clinic = request.getData();
            clinic.setId(address);
            defaultManager.addClinic(clinic);

            return new ClinicResponse(Lists.newArrayList(clinic));
        } finally {
            defaultManager.kill();
            clinicManager.kill();
        }
    }

    public void assignDoctorToAPatient(String doctorId, String patientId) throws MultichainException, NodeNotFoundException, IOException {
        String stream = patientId.substring(0, 31);
        MedChainManager adminManager = new MedChainManager(Utils.context().getNode());

        try {
            adminManager.grantStreamPermission(doctorId, stream, MedChainManager.StreamPermission.write);
        } finally {
            adminManager.kill();
        }
    }
}
