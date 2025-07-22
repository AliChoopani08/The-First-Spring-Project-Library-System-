package com.ali.First.spring.project.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BookRequest {

    @NotBlank(message = "Title of book mustn't be empty !")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Title should be combination of ((a-z) - (A-Z)")
    private String title;

    @NotBlank(message = "author of book mustn't be empty !")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "author should be combination of ((a-z) - (A-Z)")
    private String author;

    @NotBlank(message = "Price of book mustn't be empty !")
    @Pattern(regexp = "^\\d+$" , message = "Price is invalid")
    private String price;

    @NotBlank(message = "category of book mustn't be empty !")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "category should be combination of ((a-z) - (A-Z)")
    private String category;
}
