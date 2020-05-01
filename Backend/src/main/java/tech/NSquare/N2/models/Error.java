package tech.NSquare.N2.models;

import lombok.Data;

@Data
public class Error {

    private String errorCode;
    private String errorMessages;
    private String errorCodeUUID;

}
