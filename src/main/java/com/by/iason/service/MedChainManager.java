package com.by.iason.service;

import com.by.iason.Utils;
import com.by.iason.config.BlockChain;
import com.by.iason.exception.AddressNotFoundException;
import com.by.iason.exception.NodeNotFoundException;
import com.by.iason.model.entity.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import multichain.command.MultiChainCommand;
import multichain.command.MultichainException;
import multichain.object.StreamKeyItem;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * Created by iason
 * on 10/4/2017.
 */

public class MedChainManager {

    private MultiChainCommand cmd;

    public MedChainManager(Node node) {
        cmd = cmd(node);
    }

    public void grantPermissions(String address, int ... permissions) throws MultichainException {
        for (int p: permissions) {
            cmd.getGrantCommand().grant(address, p);
        }
    }

    public String addNode(Node node) throws MultichainException, JsonProcessingException {
        String nodeId = UUID.randomUUID().toString();
        cmd.getStreamCommand().publish("nodes", nodeId, HexBin.encode(new ObjectMapper().writeValueAsBytes(node)));
        return nodeId;
    }

    public void addDefaultNode() throws MultichainException, JsonProcessingException {
        if (cmd.getStreamCommand().listStreamKeyItems("nodes", "default_node").isEmpty()) {
            String nodeId = "default_node";
            cmd.getStreamCommand().publish("nodes", nodeId, HexBin.encode(new ObjectMapper().writeValueAsBytes(BlockChain.defaultNode())));
        }
    }

    public void addAddress(String address, String nodeId) throws MultichainException {
        cmd.getStreamCommand().publish("addresses", address, HexBin.encode(nodeId.getBytes()));
    }

    public void addDoctor(Doctor doctor) throws MultichainException {
        cmd.getStreamCommand().publish("doctors", doctor.getId(), HexBin.encode(doctor.getName().getBytes()));
    }

    public void addPatient(Patient patient) throws MultichainException {
        cmd.getStreamCommand().publish("patients", patient.getId(), HexBin.encode(patient.getName().getBytes()));
    }

    /**
     * Get real node by address
     * At this time user in not logged in
     * We use default address to fetch user node to connect
     */
    public Node getNode(String address) throws MultichainException, NodeNotFoundException, IOException {
        if (address == null) {
            return null;
        }
        List<StreamKeyItem> items = cmd.getStreamCommand().listStreamKeyItems("addresses", address, false, 1);
        if (items.isEmpty()) {
            return null;
        }
        String nodeId = new String(HexBin.decode(items.get(0).getData()));

        items = cmd.getStreamCommand().listStreamKeyItems("nodes", nodeId, false, 1);
        if (items.isEmpty()) {
            throw new NodeNotFoundException();
        }

        return new ObjectMapper().readValue(HexBin.decode(items.get(0).getData()), Node.class);
    }

    public void addClinic(Clinic clinic) throws MultichainException {
        cmd.getStreamCommand().publish("clinics", clinic.getId(), HexBin.encode(clinic.getName().getBytes()));
    }

    public void addAdmin(Admin admin) throws MultichainException {
        cmd.getStreamCommand().publish("admins", admin.getId(), HexBin.encode(admin.getName().getBytes()));
    }

    public List<Clinic> getClinics() throws MultichainException {
        List<StreamKeyItem> items = cmd.getStreamCommand().listStreamKeyItems("clinics", "*");

        return items.stream().map(i -> {
            Clinic c = new Clinic();
            c.setName(new String(HexBin.decode(i.getData())));
            c.setId(i.getKey());
            return c;

        }).collect(Collectors.toList());
    }

    public boolean isClinicExists(String id) throws MultichainException {
        return !cmd.getStreamCommand().listStreamKeyItems("clinics", id, false, 1).isEmpty();
    }

    private MultiChainCommand cmd(Node node) {
        return new MultiChainCommand(node.getRpcHost(), node.getRpcPort(), node.getRpcUser(), node.getRpcPwd());
    }

    public String newAddress() throws MultichainException {
        return cmd.getAddressCommand().getNewAddress();
    }

    public void subscribeTo(Node node, String... streams) throws MultichainException {
        MultiChainCommand cmd1 = cmd(node);
        for (String stream : streams) {
            cmd1.getStreamCommand().subscribe(stream);
        }
    }

    public void createPatientStream(String address) throws MultichainException {
        cmd.getStreamCommand().create(address, false);
    }

    public String getNodeId(String address) throws MultichainException, AddressNotFoundException {
        List<StreamKeyItem> items = cmd.getStreamCommand().listStreamKeyItems("addresses", address, false, 1);
        if (items.isEmpty()) {
            throw new AddressNotFoundException();
        }

        return new String(HexBin.decode(items.get(0).getData()));
    }
}
