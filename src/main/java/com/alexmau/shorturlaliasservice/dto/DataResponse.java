package com.alexmau.shorturlaliasservice.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DataResponse<T extends Serializable> {

    private T data;

    private List<ResponseMessage> messages;

    private StatusType status;

    public DataResponse() {
        super();
        this.status = StatusType.SUCCESSFUL;
    }

    public ResponseMessage addMessage(ResponseMessage responseMessage) {
        if (messages == null) {
            messages = new ArrayList<>();
        }
        messages.add(responseMessage);
        return responseMessage;
    }

    public ResponseMessage addMessage(Integer code, String message) {
        if (messages == null) {
            messages = new ArrayList<>();
        }
        final ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessage(message);
        responseMessage.setCode(code);
        messages.add(responseMessage);
        return responseMessage;
    }
}
