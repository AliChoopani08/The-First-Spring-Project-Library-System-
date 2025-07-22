package com.ali.First.spring.project.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"name"})
@ToString(of = {"name"})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "category",
    cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books = new LinkedList<>();

    public Category(String kind) {
        this.name = kind;
    }

    public void addBook(Book book) {
        books.add(book);
        book.setCategory(this);
    }
    public void removeBook(Book book) {
        books.remove(book);
        book.setCategory(null);
    }
}
