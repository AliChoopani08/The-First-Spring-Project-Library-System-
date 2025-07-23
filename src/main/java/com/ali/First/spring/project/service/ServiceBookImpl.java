package com.ali.First.spring.project.service;

import com.ali.First.spring.project.DTO.BookRequest;
import com.ali.First.spring.project.DTO.RequestCategory;
import com.ali.First.spring.project.exceptionHandler.NotFoundBook;
import com.ali.First.spring.project.exceptionHandler.NotFoundCategory;
import com.ali.First.spring.project.model.Book;
import com.ali.First.spring.project.model.Category;
import com.ali.First.spring.project.reposirory.BookRepository;
import com.ali.First.spring.project.reposirory.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class ServiceBookImpl implements BookServiceInterface{

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;



    public List<Book> getAllBooksByCategory(String categoryKind) {
       return bookRepository.findBooksByCategory(categoryKind)
                .orElseThrow(() -> new NotFoundCategory("Not found this category: " + categoryKind));
    }

    @Override
    public Book saveBook(BookRequest bookRequest) {
        final Book originalBook = mapper.apply(bookRequest);
        final Category category = getCategoryByName(bookRequest);

        category.addBook(originalBook);

        return bookRepository.save(originalBook);
    }

    @Override
    public Book getById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundBook("User not found with this id: " + id));
    }

    @Override
    @Transactional
    public void deleteById(Long id, Long categoryId) {
        final Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundCategory("Not found category !"));
        final Book foundBook = getById(id);

        category.removeBook(foundBook);
    }

    @Override
    public Book update(BookRequest bookRequest, Long id) {
        final Book foundBook = getById(id);
        final Category category = getCategoryByName(bookRequest);

        Book originalBook = mapper.apply(bookRequest);
        originalBook.setId(foundBook.getId());
        category.addBook(originalBook);

        return bookRepository.save(originalBook);
    }

    private Category getCategoryByName(BookRequest bookRequest) {
        return categoryRepository.findByNameIgnoreCase(bookRequest.getCategory())
                .orElseThrow(() -> new NotFoundCategory("Category not found"));
    }

    Function<BookRequest, Book> mapper = bookRequest -> new Book(bookRequest.getTitle()
             , bookRequest.getAuthor()
             , Integer.parseInt(bookRequest.getPrice())
    );
}
