package tech.nocountry.c1832ftjavaangular.model;

import lombok.Data;

@Data
public class StringResponse {
    private String message;

    public StringResponse(String message, Object... args) {
        this.message = String.format(message, args);
    }
}
