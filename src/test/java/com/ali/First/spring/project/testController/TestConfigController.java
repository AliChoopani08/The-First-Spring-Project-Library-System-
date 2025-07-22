package com.ali.First.spring.project.testController;

import com.ali.First.spring.project.service.BookServiceInterface;
import com.ali.First.spring.project.service.ServiceCategoryInterface;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@TestConfiguration
public class TestConfigController {
    @Bean
    public BookServiceInterface service() {
        return Mockito.mock(BookServiceInterface.class);
    }
    @Bean
    public ModelMapper modelMapper() {
        return Mockito.mock(ModelMapper.class);
    }
    @Bean
    public ServiceCategoryInterface serviceCategoryInterface() {
        return Mockito.mock(ServiceCategoryInterface.class);
    }
}
