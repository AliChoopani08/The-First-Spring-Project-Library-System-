package com.ali.First.spring.project.seviceTest;

import com.ali.First.spring.project.DTO.BookRequest;
import com.ali.First.spring.project.DTO.RequestCategory;
import com.ali.First.spring.project.exceptionHandler.NotFoundBook;
import com.ali.First.spring.project.exceptionHandler.NotFoundCategory;
import com.ali.First.spring.project.model.Book;
import com.ali.First.spring.project.model.Category;
import com.ali.First.spring.project.reposirory.BookRepository;
import com.ali.First.spring.project.reposirory.CategoryRepository;
import com.ali.First.spring.project.service.ServiceBookImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceBookShould {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private ServiceBookImpl service;



    @Test
    void save_a_book() {
        final Category programmingLanguage = new Category("Programming Language");
        BookRequest bookRequest = new BookRequest("Java", "Ali", "65000" ,"Programming Language");
        Book expectedBook = new Book("Java", "Ali", 65000);

        when(categoryRepository.findByNameIgnoreCase("Programming Language")).thenReturn(Optional.of(programmingLanguage));
        when(bookRepository.save(any(Book.class))).thenReturn(expectedBook);
        programmingLanguage.addBook(expectedBook);
        final Book finalBook = service.saveBook(bookRequest);

        assertThat(finalBook).isEqualTo(expectedBook);
    }

    @Test
    void find_books_by_category() {
        List<Book> expectedBooks = List.of(new Book("Java", "Ali", 2000),
                new Book("Python", "Mohammad", 1000));

        when(bookRepository.findBooksByCategory("Programming language")).thenReturn(Optional.of(expectedBooks));
        final List<Book> booksByCategory = service.getAllBooksByCategory("Programming language");

        Assertions.assertThat(booksByCategory).isEqualTo(expectedBooks);
    }

    @Test
    void find_book_by_id() {
        Long id = 2L;
        Book fakeBook = new Book("Spring Boot", "Reza", 23000);

        when(bookRepository.findById(id)).thenReturn(Optional.of(fakeBook));
       final Book foundBook = service.getById(id);

        assertThat(foundBook).isEqualTo(fakeBook);

    }

    @Test
    void update_book_by_id() {
        final Category programmingLanguage = new Category("Programming language");
        Long id = 2L;
        Book foundBook = new Book("Java", "Javad", 30);
        BookRequest newBook = new BookRequest("Spring", "Javad", "500", programmingLanguage.getName());
        Book expectedBook = new Book("Spring", "Javad", 500);

        when(categoryRepository.findByNameIgnoreCase("Programming language")).thenReturn(Optional.of(programmingLanguage));
        when(bookRepository.findById(id)).thenReturn(Optional.of(foundBook));
        when(bookRepository.save(any(Book.class))).thenReturn(expectedBook);
        programmingLanguage.addBook(expectedBook);
        final Book finalBook= service.update(newBook, id);

        assertThat(finalBook).isEqualTo(expectedBook);
    }

    @Test
    void get_book_by_id_return_exception() {
        Long id = 999L;

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(()-> service.getById(id))
                .isInstanceOf( NotFoundBook.class)
                .hasMessageContaining("User not found with this id: " + id);
    }

    @Test
    void delete_book_by_id() {
        Category framework = new Category("Framework");
        Long categoryId = 3L;
        Long id = 4L;

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(framework));
        when(bookRepository.findById(id)).thenReturn(Optional.of(new Book("Spring", "Ali", 2000)));

        service.deleteById(id, categoryId);

        assertThat(framework.getBooks()).isEmpty();
    }

    @Test
    void get_category_and_return_exception() {
        String requestedCategory = "Comedy";

        when(categoryRepository.findByNameIgnoreCase(requestedCategory)).thenReturn(Optional.empty());

       assertThatThrownBy(() -> service.getAllBooksByCategory(requestedCategory))
               .isInstanceOf(NotFoundCategory.class)
               .hasMessageContaining("Not found this category: " + requestedCategory);
    }
}
