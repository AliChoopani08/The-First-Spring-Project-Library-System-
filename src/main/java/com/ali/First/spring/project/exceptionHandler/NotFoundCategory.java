package com.ali.First.spring.project.exceptionHandler;

import com.ali.First.spring.project.model.Category;

public class NotFoundCategory extends RuntimeException{

    public NotFoundCategory(String message) {
        super(message);
    }
}
