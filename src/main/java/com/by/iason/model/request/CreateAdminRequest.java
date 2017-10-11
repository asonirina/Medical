package com.by.iason.model.request;

import com.by.iason.model.entity.Admin;
import com.by.iason.model.entity.Clinic;
import com.by.iason.model.entity.Node;

/**
 * Created by iason
 * on 10/6/2017.
 */
public class CreateAdminRequest extends AbstractRequest<Admin> {

    public CreateAdminRequest() {
        super(null, null);
    }

    public CreateAdminRequest(Admin data) {
        super(data, null);
    }

}
