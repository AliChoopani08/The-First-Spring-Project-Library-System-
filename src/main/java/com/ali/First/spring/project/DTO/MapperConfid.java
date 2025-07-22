package com.ali.First.spring.project.DTO;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfid {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
