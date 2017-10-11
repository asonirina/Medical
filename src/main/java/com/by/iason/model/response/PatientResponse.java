package com.by.iason.model.response;

import com.by.iason.model.entity.Patient;

import java.util.List;

/**
 * Created by iason
 * on 10/6/2017.
 */
public class PatientResponse extends AbstractResponse<Patient> {
    public PatientResponse(List<Patient> data) {
        super(data);
    }
}
