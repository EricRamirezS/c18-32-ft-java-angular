package tech.nocountry.c1832ftjavaangular.utils;

import lombok.Data;

@Data
public class StringResponse {
    private final String message;

    public StringResponse(String message, Object... args) {
        this.message = String.format(message, args);
    }
}
