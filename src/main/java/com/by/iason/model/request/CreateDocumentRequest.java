package com.by.iason.model.request;

import com.by.iason.model.entity.Document;

/**
 * Created by iason
 * on 10/6/2017.
 */
public class CreateDocumentRequest extends AbstractRequest<Document> {

    public CreateDocumentRequest() {
        super(null);
    }

    public CreateDocumentRequest(Document data) {
        super(data);
    }

}
