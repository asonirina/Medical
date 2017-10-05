package com.by.iason.controller;

import com.by.iason.model.CreateDoctorRequest;
import com.by.iason.service.DoctorAdminService;
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
 * on 10/5/2017.
 */

@RestController
@RequestMapping("v1/doctor/admin/")
@Api(basePath = "/v2/doctor/admin")
public class DoctorAdminController {

    @Autowired
    DoctorAdminService doctorAdminService;

    @ApiOperation(value = "Create a Doctor", notes = "Create new Doctor")
    @RequestMapping(value = "doctors", method = RequestMethod.POST)
    public ResponseEntity<?> createDoctorAdmin(@RequestBody CreateDoctorRequest request) {
        try {
            doctorAdminService.createDoctor(request);
            return new ResponseEntity(HttpStatus.OK);
        } catch (MultichainException ex) {
            return new ResponseEntity<String>("Something went wrong...", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
