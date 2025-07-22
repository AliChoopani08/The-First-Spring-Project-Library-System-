package com.ali.First.spring.project;

import com.ali.First.spring.project.exceptionHandler.ErrorResponse;

import java.time.LocalDateTime;

public interface FunctionInterface {
    ErrorResponse create(LocalDateTime time, int status,String error, String message, String urlAddres);
}
