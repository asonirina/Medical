package com.by.iason.service;

import com.by.iason.exception.AddressNotFoundException;
import com.by.iason.exception.DocumentNotFoundException;
import com.by.iason.exception.NodeNotFoundException;
import com.by.iason.model.entity.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import multichain.command.MultiChainCommand;
import multichain.command.MultichainException;
import multichain.object.StreamKeyItem;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.by.iason.model.Streams.*;

/**
 * Created by iason
 * on 10/4/2017.
 */

public class MedChainManager {

    public enum StreamPermission {
        write, issue, admin
    }

    private MultiChainCommand cmd;

    public MedChainManager(Node node) {
        cmd = cmd(node);
    }

    public void grantPermissions(String address, int ... permissions) throws MultichainException {
        cmd.getGrantCommand().grant(address, Arrays.stream(permissions).sum());
    }

    public void grantStreamPermission(String address, String stream, StreamPermission permission) throws MultichainException {
        cmd.getGrantCommand().grant(address, stream + "." + permission);
    }

    public String addNode(Node node) throws MultichainException, JsonProcessingException {
        String nodeId = UUID.randomUUID().toString();
        cmd.getStreamCommand().publish(NODES, nodeId, HexBin.encode(new ObjectMapper().writeValueAsBytes(node)));
        return nodeId;
    }

    public void addAddress(String address, String nodeId) throws MultichainException {
        cmd.getStreamCommand().publish(ADDRESSES, address, HexBin.encode(nodeId.getBytes()));
    }

    public String get1stAddress() throws MultichainException {
        return cmd.getAddressCommand().getAddresses().get(0);
    }

    public void addDoctor(Doctor doctor) throws MultichainException {
        cmd.getStreamCommand().publish(DOCTORS, doctor.getId(), HexBin.encode(doctor.getName().getBytes()));
    }

    public void addPatient(Patient patient) throws MultichainException {
        cmd.getStreamCommand().publish(PATIENTS, patient.getId(), HexBin.encode(patient.getName().getBytes()));
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
        List<StreamKeyItem> items = cmd.getStreamCommand().listStreamKeyItems(ADDRESSES, address, false, 1);
        if (items.isEmpty()) {
            return null;
        }
        String nodeId = new String(HexBin.decode(items.get(0).getData()));

        items = cmd.getStreamCommand().listStreamKeyItems(NODES, nodeId, false, 1);
        if (items.isEmpty()) {
            throw new NodeNotFoundException();
        }

        return new ObjectMapper().readValue(HexBin.decode(items.get(0).getData()), Node.class);
    }

    public byte[] getPrivateKey(String address) throws MultichainException {
        if (address == null) {
            return null;
        }
        List<StreamKeyItem> items = cmd.getStreamCommand().listStreamKeyItems(PRIVATE_KEYS, address, false, 1);
        if (items.isEmpty()) {
            return null;
        }
        return HexBin.decode(items.get(0).getData());
    }

    public byte[] getPublicKey(String address) throws MultichainException {
        if (address == null) {
            return null;
        }
        List<StreamKeyItem> items = cmd.getStreamCommand().listStreamKeyItems(PUBLIC_KEYS, address, false, 1);
        if (items.isEmpty()) {
            return null;
        }
        return HexBin.decode(items.get(0).getData());
    }

    public void addClinic(Clinic clinic) throws MultichainException {
        cmd.getStreamCommand().publish(CLINICS, clinic.getId(), HexBin.encode(clinic.getName().getBytes()));
    }

    public void addPrivateKey(String address, byte content[]) throws MultichainException {
        cmd.getStreamCommand().publish(PRIVATE_KEYS, address, HexBin.encode(content));
    }

    public void addPublicKey(String address, byte content[]) throws MultichainException {
        cmd.getStreamCommand().publish(PUBLIC_KEYS, address, HexBin.encode(content));
    }

    public void addAdmin(Admin admin) throws MultichainException {
        cmd.getStreamCommand().publish(ADMINS, admin.getId(), HexBin.encode(admin.getName().getBytes()));
    }

    public List<Clinic> getClinics() throws MultichainException {
        List<StreamKeyItem> items = cmd.getStreamCommand().listStreamKeyItems(CLINICS, "*");

        return items.stream().map(i -> {
            Clinic c = new Clinic();
            c.setName(new String(HexBin.decode(i.getData())));
            c.setId(i.getKey());
            return c;

        }).collect(Collectors.toList());
    }

    public boolean isClinicExists(String id) throws MultichainException {
        return !cmd.getStreamCommand().listStreamKeyItems(CLINICS, id, false, 1).isEmpty();
    }

    private MultiChainCommand cmd(Node node) {
        return new MultiChainCommand(node.getRpcHost(), node.getRpcPort(), node.getRpcUser(), node.getRpcPwd());
    }

    public String newAddress() throws MultichainException {
        return cmd.getAddressCommand().getNewAddress();
    }

    public void subscribeTo(String... streams) throws MultichainException {
        for (String stream : streams) {
            cmd.getStreamCommand().subscribe(stream);
        }
    }

    public String createPatientStream(String address) throws MultichainException {
        String streamName = address.substring(0, 31);
        cmd.getStreamCommand().create(streamName, false);
        return streamName;
    }

    public List<Document> getPatientDocuments(String patientId) throws MultichainException {
        return cmd.getStreamCommand()
                .listStreamKeys(patientId.substring(0, 31)).stream()
                .map(key -> {
                    Document d = new Document();
                    d.setId(key.getKey());
                    d.setData("Data is hidden");
                    return d;
                }).collect(Collectors.toList());
    }

    public String getDocument(String patientId, String docId) throws DocumentNotFoundException, MultichainException {
        List<StreamKeyItem> items = cmd.getStreamCommand().listStreamKeyItems(patientId.substring(0, 31), docId, false, 1);
        if (items.isEmpty()) {
            throw new DocumentNotFoundException();
        }

        return new String(HexBin.decode(items.get(0).getData()));
    }

    public void writeToPatient(String patientId, String key, String data) throws MultichainException {
        cmd.getStreamCommand().publish(patientId.substring(0, 31), key, HexBin.encode(data.getBytes()));
    }

    public void writeToAccess(String patientId, String docId, String data) throws MultichainException {
        cmd.getStreamCommand().publish(ACCESS, patientId + "_" + docId, HexBin.encode(data.getBytes()));
    }

    public String readAccess(String patientId, String docId) throws MultichainException, DocumentNotFoundException {
        List<StreamKeyItem> items = cmd.getStreamCommand().listStreamKeyItems(ACCESS, patientId + "_" + docId, false, 1);
        if (items.isEmpty()) {
            throw new DocumentNotFoundException();
        }

        return new String(HexBin.decode(items.get(0).getData()));
    }

    public String getNodeId(String address) throws MultichainException, AddressNotFoundException {
        List<StreamKeyItem> items = cmd.getStreamCommand().listStreamKeyItems(ADDRESSES, address, false, 1);
        if (items.isEmpty()) {
            throw new AddressNotFoundException();
        }

        return new String(HexBin.decode(items.get(0).getData()));
    }

    public void kill() {
        cmd.release();
    }
}
