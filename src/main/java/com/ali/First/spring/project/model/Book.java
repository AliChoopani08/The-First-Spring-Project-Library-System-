package com.ali.First.spring.project.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private int price;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "categoryId")
    private Category category;


    public Book() {}

    public Book(String title, String author, int price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }
}
