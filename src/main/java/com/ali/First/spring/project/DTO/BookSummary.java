package com.ali.First.spring.project.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookSummary {
    private String title;
    private String author;
    private int price;
    private String category;
}
