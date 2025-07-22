package com.ali.First.spring.project.controller;

import com.ali.First.spring.project.DTO.CategorySummary;
import com.ali.First.spring.project.DTO.RequestCategory;
import com.ali.First.spring.project.model.Category;
import com.ali.First.spring.project.service.ServiceCategoryImpl;
import com.ali.First.spring.project.service.ServiceCategoryInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Function;

import static com.ali.First.spring.project.DTO.CategorySummary.mapperToSummary;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/categories")
public class ControllerCategory {

    @Autowired
    private ServiceCategoryInterface service;


    @PostMapping
    public ResponseEntity<CategorySummary> save(@RequestBody @Valid RequestCategory requestCategory) {

        final Category savedCategory = service.save(requestCategory);
        final CategorySummary categorySummary = mapperToSummary(savedCategory);

        return status(CREATED).body(categorySummary);
    }
}
