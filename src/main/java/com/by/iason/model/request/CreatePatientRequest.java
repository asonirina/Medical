package com.by.iason.model.request;

import com.by.iason.model.entity.Patient;

/**
 * Created by iason
 * on 10/6/2017.
 */
public class CreatePatientRequest extends AbstractRequest<Patient> {

    public CreatePatientRequest () {
        super(null, null);
    }

    public CreatePatientRequest(Patient data) {
        super(data, null);
    }
}
