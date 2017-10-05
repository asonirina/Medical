package com.by.iason.controller;

import com.by.iason.model.CreateClinicRequest;
import com.by.iason.service.MinistryAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import multichain.command.MultichainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation(value = "Create a Doctor Admin", notes = "Create new Doctor Admin")
    @RequestMapping(value = "clinics", method = RequestMethod.POST)
    public ResponseEntity<?> createDoctorAdmin(@RequestBody CreateClinicRequest request) {
        try {
            ministryAdminService.createClinic(request);
            return new ResponseEntity(HttpStatus.OK);
        } catch (MultichainException ex) {
            return new ResponseEntity<String>("Something went wrong...", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
