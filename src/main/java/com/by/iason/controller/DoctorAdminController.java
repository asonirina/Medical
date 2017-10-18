package com.by.iason.controller;

import com.by.iason.model.request.CreateClinicRequest;
import com.by.iason.model.request.CreateDoctorRequest;
import com.by.iason.model.request.CreatePatientRequest;
import com.by.iason.model.response.AbstractResponse;
import com.by.iason.model.response.ClinicResponse;
import com.by.iason.model.response.DoctorResponse;
import com.by.iason.model.response.PatientResponse;
import com.by.iason.service.DoctorAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by iason
 * on 10/5/2017.
 */

@RestController
@RequestMapping("v1/doctor/admin/")
@Api(basePath = "/v1/doctor/admin")
public class DoctorAdminController {

    @Autowired
    DoctorAdminService doctorAdminService;

    @ApiOperation(value = "Create a Doctor", notes = "Create new Doctor", response = DoctorResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Address", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "doctors", method = RequestMethod.POST)
    public ResponseEntity<AbstractResponse> createDoctor(@RequestBody CreateDoctorRequest request) throws Exception {
        String pwd = RandomStringUtils.randomNumeric(5);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Password", pwd);
        return new ResponseEntity<>(doctorAdminService.createDoctor(request, pwd), headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Create a Patient", notes = "Create new Patient", response = PatientResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Address", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "patients", method = RequestMethod.POST)
    public ResponseEntity<AbstractResponse> createPatient(@RequestBody CreatePatientRequest request) throws Exception {
        String pwd = RandomStringUtils.randomNumeric(5);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Password", pwd);
        return new ResponseEntity<>(doctorAdminService.createPatient(request, pwd), headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Register new clinic node", notes = "Register new clinic node", response = ClinicResponse.class)
    @RequestMapping(value = "nodes", method = RequestMethod.POST)
    public ResponseEntity<AbstractResponse> registerNode(@RequestBody CreateClinicRequest request) throws Exception {
        String pwd = RandomStringUtils.randomNumeric(5);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Password", pwd);
        return new ResponseEntity<>(doctorAdminService.registerClinic(request, pwd), headers, HttpStatus.OK);
    }

    @ApiOperation(value = "Assign Doctor", notes = "Assign Doctor", response = Void.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Address", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "doctor", method = RequestMethod.PUT)
    public ResponseEntity<?> createDoctorAdmin(@RequestParam(name = "doctorID") String doctorId,
                                               @RequestParam(name = "patientID") String patientId) throws Exception {
        doctorAdminService.assignDoctorToAPatient(doctorId, patientId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }
}
