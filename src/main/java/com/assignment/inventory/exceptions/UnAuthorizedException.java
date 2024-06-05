package com.assignment.inventory.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnAuthorizedException extends Exception{
    private String accessToken;
    private String errorMessage;

    public UnAuthorizedException(String errorMessage, String accessToken) {
        super(errorMessage);
        this.accessToken = accessToken;
    }
}
