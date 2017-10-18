package com.by.iason.model.request;

import com.by.iason.model.entity.Admin;

/**
 * Created by iason
 * on 10/6/2017.
 */
public class CreateAdminRequest extends AbstractNodeRequest<Admin> {

    public CreateAdminRequest() {
        super(null, null);
    }

    public CreateAdminRequest(Admin data) {
        super(data, null);
    }

}
