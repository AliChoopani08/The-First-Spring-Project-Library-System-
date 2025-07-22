package com.ali.First.spring.project.service;
import com.ali.First.spring.project.DTO.BookRequest;
import com.ali.First.spring.project.DTO.RequestCategory;
import com.ali.First.spring.project.model.Book;
import com.ali.First.spring.project.model.Category;

import java.util.List;

public interface BookServiceInterface {
    List<Book> getAllBooksByCategory(String categoryKind);
    Book saveBook(BookRequest bookRequest);
    Book getById(Long id);
    void deleteById(Long id, Long categoryId);
   Book update(BookRequest bookRequest, Long id);
}
