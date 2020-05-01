package tech.NSquare.N2.models.enums;


public enum GeneralErrorEnum {
    SUCCESS(10000, "SUCCESS", "Success"),
    FAILED(10001, "FAILED", "Failed"),
    USER_NOT_FOUND(10003,"USER_NOT_FOUND","User Not Found in DataBase"),
    AUTH_TOKEN_NOT_PRESENT(10004,"AUTH_TOKEN_NOT_PRESENT", "Authenticator Token Not Found in Header"),
    EMAIL_ALREADY_EXISTS(10005,"EMAIL_EXISTS_IN_DB","Email Id Already in DB cannot create User"),
    USER_REGISTRATION_ERROR(10006,"USER_REGISTRATION_ERROR","User Cannot be Registered")
    ;


    private int code;
    private String errorCode;
    private String errorMessage;
    private int httpStatusErrorCode;

    GeneralErrorEnum(int code, String errorCode, String errorMessage) {
        this.code = code;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    GeneralErrorEnum(int code, String errorCode, String errorMessage, int httpStatusErrorCode) {
        this.code = code;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatusErrorCode = httpStatusErrorCode;
    }

    public int getCode() {
        return code;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getHttpStatusErrorCode() {
        return httpStatusErrorCode;
    }

    public static GeneralErrorEnum getEnumByErrorCode(String errorCode) {
        GeneralErrorEnum enumObj = null;
        for (GeneralErrorEnum item : GeneralErrorEnum.values()) {
            if (item.getErrorCode().equalsIgnoreCase(errorCode)) {
                enumObj = item;
                break;
            }
        }
        return enumObj;

    }
}


