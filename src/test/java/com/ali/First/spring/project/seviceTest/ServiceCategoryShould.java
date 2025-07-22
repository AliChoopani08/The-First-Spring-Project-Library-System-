package com.ali.First.spring.project.seviceTest;

import com.ali.First.spring.project.DTO.RequestCategory;
import com.ali.First.spring.project.model.Category;
import com.ali.First.spring.project.reposirory.CategoryRepository;
import com.ali.First.spring.project.service.ServiceCategoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceCategoryShould {

    @Mock
    private CategoryRepository repository;
    @InjectMocks
    private ServiceCategoryImpl service;

    @Test
    void save_category() {
        String newCategoryName = "Historical";
        final Category newCategoryObject = new Category(newCategoryName);

        when(repository.save(newCategoryObject)).thenReturn(new Category("Historical"));
        RequestCategory requestCategory = new RequestCategory("Historical");
        final Category savedCategory = service.save(requestCategory);

        assertThat(savedCategory).isEqualTo(new Category("Historical"));
    }
}
