package com.ali.First.spring.project.DTO;

import com.ali.First.spring.project.model.Category;

public record CategorySummary (String name){

    public static CategorySummary mapperToSummary(Category category) {
        return new CategorySummary(category.getName());
    }
}
