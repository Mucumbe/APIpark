package com.blandino.demo_park_api.web.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.client.HttpServerErrorException;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
public class ErrorMessage {
    private String path;
    private String method;
    private int status;
    private String message;
    private  String statusText;
    private Map<String,String> error;

    public ErrorMessage(HttpServletRequest request, HttpStatus status,String message) {

        this.path=request.getRequestURI();
        this.method=request.getMethod();
        this.status=status.value();
        this.statusText=status.getReasonPhrase();
        this.message=message;

    }

    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message, BindingResult result) {

        this.path=request.getRequestURI();
        this.method=request.getMethod();
        this.status=status.value();
        this.statusText=status.getReasonPhrase();
        this.message=message;
        addErros(result);

    }

    private void addErros(BindingResult result) {
        error= new HashMap<>();
        for (FieldError fieldError: result.getFieldErrors())
            error.put(fieldError.getField(), fieldError.getDefaultMessage());
    }
}
