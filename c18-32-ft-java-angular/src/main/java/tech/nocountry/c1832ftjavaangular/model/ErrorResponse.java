package tech.nocountry.c1832ftjavaangular.model;

import lombok.Getter;

@SuppressWarnings("unused")
@Getter
public abstract class ErrorResponse {
    private long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    private ErrorResponse() {}
}
