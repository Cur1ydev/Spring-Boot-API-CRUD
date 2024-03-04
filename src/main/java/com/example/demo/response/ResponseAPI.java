package com.example.demo.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseAPI {
    private boolean status;
    private String message;
    private Object data;

    public ResponseAPI(boolean status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

}
