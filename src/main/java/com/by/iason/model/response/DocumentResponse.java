package com.by.iason.model.response;

import com.by.iason.model.entity.Document;

import java.util.List;

/**
 * Created by iason
 * on 10/6/2017.
 */
public class DocumentResponse extends AbstractResponse<Document> {
    public DocumentResponse(List<Document> data) {
        super(data);
    }
}
