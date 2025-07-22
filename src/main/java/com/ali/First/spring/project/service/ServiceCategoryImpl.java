package com.ali.First.spring.project.service;

import com.ali.First.spring.project.DTO.RequestCategory;
import com.ali.First.spring.project.model.Category;
import com.ali.First.spring.project.reposirory.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceCategoryImpl implements ServiceCategoryInterface{

    @Autowired
    private CategoryRepository repository;


    @Override
    public Category save(RequestCategory requestCategory) {
        Category category = new Category(requestCategory.getName());

        return repository.save(category);
    }
}
