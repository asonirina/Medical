package com.by.iason.controller;

import com.by.iason.model.response.AbstractResponse;
import com.by.iason.model.response.ErrorResponse;
import com.by.iason.model.response.PatientResponse;
import com.by.iason.service.PatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by iason
 * on 10/13/2017.
 */

@RestController
@RequestMapping("v1/patient/")
@Api(basePath = "/v1/patient/")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @ApiOperation(value = "Get Documents", notes = "Documents", response = PatientResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Address", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "documents", method = RequestMethod.GET)
    public ResponseEntity<AbstractResponse> getDocuments() {
        try {
            return new ResponseEntity<>(patientService.listDocuments(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ErrorResponse("Something went wrong...", HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Get Documents", notes = "Documents", response = PatientResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Address", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "Password", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "documents/{docID}", method = RequestMethod.GET)
    public ResponseEntity<AbstractResponse> getDocuments(@PathVariable("docID") String docId) {
        try {
            return new ResponseEntity<>(patientService.getDocument(docId), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ErrorResponse("Something went wrong...", HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
