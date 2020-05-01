package tech.NSquare.N2.models;

import lombok.Data;

@Data
public class BaseResponse<T> {

    private ResponseHeader responseHeader;
    private T responseBody;
}
