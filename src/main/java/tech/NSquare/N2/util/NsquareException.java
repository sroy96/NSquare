package tech.NSquare.N2.util;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class NsquareException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = -3094483518571578837L;
    private List<String> errorCodes;
    private Class objectType;
    private String errorCode;
    private String message;
    private UUID errorUUID;


    public NsquareException(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
        this.errorUUID = UUID.randomUUID();
    }

    public NsquareException(String errorCode, String message, Class objectType) {
        this(errorCode, message);
        this.setObjectType(objectType);
    }

    public String getMessage() {
        return this.message;
    }
}

