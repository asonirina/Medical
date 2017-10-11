package com.by.iason.model.response;

import com.by.iason.model.entity.Clinic;
import com.by.iason.model.entity.Doctor;

import java.util.List;

/**
 * Created by iason
 * on 10/6/2017.
 */
public class DoctorResponse extends AbstractResponse<Doctor> {
    public DoctorResponse(List<Doctor> data) {
        super(data);
    }
}
