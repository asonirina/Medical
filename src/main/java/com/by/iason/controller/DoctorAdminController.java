package com.by.iason.controller;

import com.by.iason.exception.AddressNotFoundException;
import com.by.iason.model.entity.Clinic;
import com.by.iason.model.request.CreateClinicRequest;
import com.by.iason.model.request.CreateDoctorRequest;
import com.by.iason.model.entity.Node;
import com.by.iason.model.request.CreatePatientRequest;
import com.by.iason.model.response.AbstractResponse;
import com.by.iason.model.response.ErrorResponse;
import com.by.iason.service.DoctorAdminService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import multichain.command.MultichainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by iason
 * on 10/5/2017.
 */

@RestController
@RequestMapping("v1/doctor/admin/")
@Api(basePath = "/v2/doctor/admin")
public class DoctorAdminController {

    @Autowired
    DoctorAdminService doctorAdminService;

    @ApiOperation(value = "Create a Doctor", notes = "Create new Doctor")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Address", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "doctors", method = RequestMethod.POST)
    public ResponseEntity<AbstractResponse> createDoctor(@RequestBody CreateDoctorRequest request) {
        try {
            return new ResponseEntity<>(doctorAdminService.createDoctor(request), HttpStatus.OK);
        } catch (MultichainException ex) {
            return new ResponseEntity<>(new ErrorResponse("Something went wrong...", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Create a Patient", notes = "Create new Patient")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Address", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "patients", method = RequestMethod.POST)
    public ResponseEntity<AbstractResponse> createPatient(@RequestBody CreatePatientRequest request) {
        try {
            return new ResponseEntity<>(doctorAdminService.createPatient(request), HttpStatus.OK);
        } catch (MultichainException | AddressNotFoundException ex) {
            return new ResponseEntity<>(new ErrorResponse("Something went wrong...", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Register new clinic node", notes = "Register new clinic node")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Address", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "nodes", method = RequestMethod.POST)
    public ResponseEntity<AbstractResponse> registerNode(@RequestBody CreateClinicRequest request) {
        try {
            return new ResponseEntity<>(doctorAdminService.registerClinic(request), HttpStatus.OK);
        } catch (MultichainException | JsonProcessingException ex) {
            return new ResponseEntity<>(new ErrorResponse("Something went wrong...", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
