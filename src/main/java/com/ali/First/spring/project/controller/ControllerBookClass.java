package com.ali.First.spring.project.controller;

import com.ali.First.spring.project.DTO.BookRequest;
import com.ali.First.spring.project.DTO.CategorySummary;
import com.ali.First.spring.project.DTO.RequestCategory;
import com.ali.First.spring.project.model.Book;
import com.ali.First.spring.project.DTO.BookSummary;
import com.ali.First.spring.project.service.BookServiceInterface;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.function.Function;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.*;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/books")
public class ControllerBookClass {

    @Autowired
    private BookServiceInterface service;

    @Autowired
    private ModelMapper modelMapper;

    private final Function<Book, BookSummary> mapperToSummaryBook = book -> modelMapper.map(book, BookSummary.class);


    @GetMapping("/category/{category}")
    public ResponseEntity<List<BookSummary>> findAllBooksByCategory(@PathVariable @Valid String category){
        final List<Book> allBooks = service.getAllBooksByCategory(category);

        final List<BookSummary> booksInRequestedCategory = allBooks.stream()
                .map(mapperToSummaryBook)
                .toList();

        return ok(booksInRequestedCategory); // state = 200
    }


    @PostMapping
    public ResponseEntity<BookSummary> save(@RequestBody @Valid BookRequest book ) {
        final Book savedBook = service.saveBook(book);

        final BookSummary bookSummary = mapperToSummaryBook.apply(savedBook);

        return status(CREATED) // state = 201 (created)
                    .body(bookSummary);
    }


    @GetMapping("/id/{id}")
    public ResponseEntity<BookSummary> findById(@PathVariable Long id) {
            final Book foundBook = service.getById(id);

            final BookSummary bookSummary = mapperToSummaryBook.apply(foundBook);

            return ok(bookSummary); // state = 200
    }


    @PutMapping("/id/{id}")
    public ResponseEntity<BookSummary> updateById(@RequestBody @Valid BookRequest book, @PathVariable Long id) {
            final Book updatedBook = service.update(book, id);

            final BookSummary bookSummary = mapperToSummaryBook.apply(updatedBook);

            return ok(bookSummary);
    }


    @DeleteMapping("/id/{id}/category_id/{categoryId}")
    public void deleteById(@PathVariable Long id, @PathVariable Long categoryId){
        service.deleteById(id, categoryId);
    }
}
