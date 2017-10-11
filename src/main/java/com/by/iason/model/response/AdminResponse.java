package com.by.iason.model.response;

import com.by.iason.model.entity.Admin;
import com.by.iason.model.entity.Clinic;

import java.util.List;

/**
 * Created by iason
 * on 10/6/2017.
 */
public class AdminResponse extends AbstractResponse<Admin> {
    public AdminResponse(List<Admin> data) {
        super(data);
    }
}
