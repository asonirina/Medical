package com.by.iason.model.response;

import java.util.AbstractMap;
import java.util.List;

/**
 * Created by iason
 * on 10/6/2017.
 */
public abstract class AbstractResponse<T> {
    private List<T> data;
    private Error error;

    public AbstractResponse(List<T> data) {
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public class Error {
        private String name;
        private int code;

        public Error(String name, int code) {
            this.name = name;
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }
}
