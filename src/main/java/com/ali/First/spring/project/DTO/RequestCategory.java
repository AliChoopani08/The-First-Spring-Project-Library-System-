package com.ali.First.spring.project.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RequestCategory {

    @NotBlank(message = "category name mustn't be empty !")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "category name should be combination of ((a-z) - (A-Z)")
    private String name;
}
