package com.by.iason.controller;

import com.by.iason.exception.AddressNotFoundException;
import com.by.iason.exception.ClinicNotFoundException;
import com.by.iason.model.request.CreateAdminRequest;
import com.by.iason.model.response.AbstractResponse;
import com.by.iason.model.response.ErrorResponse;
import com.by.iason.service.MinistryAdminService;
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
 * on 10/3/2017.
 */
@RestController
@RequestMapping("v1/ministry/admin/")
@Api(basePath = "/v2/ministry/admin")
public class MinistryAdminController {

    @Autowired
    private MinistryAdminService ministryAdminService;

    @ApiOperation(value = "Approve Clinic", notes = "Approve Clinic")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Address", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "clinics", method = RequestMethod.PUT)
    public ResponseEntity<?> createDoctorAdmin(@RequestParam(name = "id") String id) {
        try {
            ministryAdminService.approveClinic(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (MultichainException | ClinicNotFoundException ex) {
            return new ResponseEntity<>(new ErrorResponse("Something went wrong...", HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Get Clinics", notes = "Clinics")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Address", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "clinics", method = RequestMethod.GET)
    public ResponseEntity<AbstractResponse> getClinics() {
        try {
            return new ResponseEntity<>(ministryAdminService.getClinics(), HttpStatus.OK);
        } catch (MultichainException ex) {
            return new ResponseEntity<>(new ErrorResponse("Something went wrong...", HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Register new admin", notes = "Register new admin")
    @RequestMapping(value = "addresses", method = RequestMethod.POST)
    public ResponseEntity<AbstractResponse> registerNode(@RequestBody CreateAdminRequest request) {
        try {
            return new ResponseEntity<>(ministryAdminService.registerAdmin(request), HttpStatus.OK);
        } catch (MultichainException | AddressNotFoundException |JsonProcessingException ex) {
            return new ResponseEntity<>(new ErrorResponse("Something went wrong...", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
