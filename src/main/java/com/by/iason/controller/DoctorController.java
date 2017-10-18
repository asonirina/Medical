package com.by.iason.controller;

import com.by.iason.model.request.CreateDocumentRequest;
import com.by.iason.model.response.AbstractResponse;
import com.by.iason.model.response.DocumentResponse;
import com.by.iason.service.DoctorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by iason
 * on 10/13/2017.
 */

@RestController
@RequestMapping("v1/doctor/")
@Api(basePath = "/v1/doctor/")
public class DoctorController {


    @Autowired
    private DoctorService doctorService;

    @ApiOperation(value = "Create Documents", notes = "Create", response = DocumentResponse.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Address", required = true, dataType = "string", paramType = "header")
    })
    @RequestMapping(value = "documents", method = RequestMethod.POST)
    public ResponseEntity<AbstractResponse> addDocument(@RequestParam(name = "patientID", required = true) String patientId,
                                                         @RequestBody CreateDocumentRequest request) throws Exception {
        return new ResponseEntity<>(doctorService.writeToPatient(request, patientId), HttpStatus.OK);
    }
}
