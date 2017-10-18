package com.by.iason.controller;

import com.by.iason.model.request.CreateAdminRequest;
import com.by.iason.model.response.AbstractResponse;
import com.by.iason.model.response.AdminResponse;
import com.by.iason.model.response.ClinicResponse;
import com.by.iason.service.MinistryAdminService;
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
 * on 10/3/2017.
 */
@RestController
@RequestMapping("v1/ministry/admin/")
@Api(basePath = "/v1/ministry/admin")
public class MinistryAdminController {

    @Autowired
    private MinistryAdminService ministryAdminService;

    @ApiOperation(value = "Approve Clinic", notes = "Approve Clinic", response = Void.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Address", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "clinics", method = RequestMethod.PUT)
    public ResponseEntity<?> createDoctorAdmin(@RequestParam(name = "id") String id) throws Exception {
        ministryAdminService.approveClinic(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Get Clinics", notes = "Clinics", response = ClinicResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Address", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "clinics", method = RequestMethod.GET)
    public ResponseEntity<AbstractResponse> getClinics() throws Exception {
        return new ResponseEntity<>(ministryAdminService.getClinics(), HttpStatus.OK);
    }

    @ApiOperation(value = "Register new admin", notes = "Register new admin", response = AdminResponse.class)
    @RequestMapping(value = "addresses", method = RequestMethod.POST)
    public ResponseEntity<AbstractResponse> registerNode(@RequestBody CreateAdminRequest request) throws Exception {

        String pwd = RandomStringUtils.randomNumeric(5);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Password", pwd);
        return new ResponseEntity<>(ministryAdminService.registerAdmin(request, pwd), headers, HttpStatus.OK);

    }
}
