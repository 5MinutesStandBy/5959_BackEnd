package com.sparta.backend5959.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class RestApiException {
    private String errorMessage;
    private HttpStatus HttpStatus;
}
