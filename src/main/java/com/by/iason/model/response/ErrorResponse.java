package com.by.iason.model.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iason
 * on 10/6/2017.
 */
public class ErrorResponse extends AbstractResponse<String> {
    public ErrorResponse(String error, int code) {
        super(new ArrayList<>());
        setError(new Error(error, code));
    }
}
