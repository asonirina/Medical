package com.by.iason.model.request;

import com.by.iason.model.entity.Clinic;
import com.by.iason.model.entity.Node;

/**
 * Created by iason
 * on 10/6/2017.
 */
public class CreateClinicRequest extends AbstractRequest<Clinic> {

    public CreateClinicRequest() {
        super(null, null);
    }

    public CreateClinicRequest(Clinic data, Node node) {
        super(data, node);
    }

}
