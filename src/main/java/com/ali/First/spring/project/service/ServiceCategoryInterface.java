package com.ali.First.spring.project.service;

import com.ali.First.spring.project.DTO.RequestCategory;
import com.ali.First.spring.project.model.Category;

public interface ServiceCategoryInterface {
    Category save(RequestCategory requestCategory);
}
