package com.by.iason.model.request;

import com.by.iason.model.entity.Doctor;

/**
 * Created by iason
 * on 10/6/2017.
 */
public class CreateDoctorRequest extends AbstractRequest<Doctor> {

    public CreateDoctorRequest () {
        super(null);
    }

    public CreateDoctorRequest(Doctor data) {
        super(data);
    }
}
