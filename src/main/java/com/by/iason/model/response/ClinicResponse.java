package com.by.iason.model.response;

import com.by.iason.model.entity.Clinic;

import java.util.List;

/**
 * Created by iason
 * on 10/6/2017.
 */
public class ClinicResponse extends AbstractResponse<Clinic> {
    public ClinicResponse(List<Clinic> data) {
        super(data);
    }
}
