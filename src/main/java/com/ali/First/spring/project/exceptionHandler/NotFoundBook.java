package com.ali.First.spring.project.exceptionHandler;

public class NotFoundBook extends RuntimeException{

    public NotFoundBook(String message) {
        super(message);
    }
}
