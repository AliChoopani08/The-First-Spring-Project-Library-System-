package com.ali.First.spring.project.repositoryTest;

import com.ali.First.spring.project.DTO.RequestCategory;
import com.ali.First.spring.project.model.Book;
import com.ali.First.spring.project.model.Category;
import com.ali.First.spring.project.reposirory.BookRepository;
import com.ali.First.spring.project.reposirory.CategoryRepository;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void add_new_category() {
        RequestCategory requestCategory = new RequestCategory("Drama");

        categoryRepository.save(new Category(requestCategory.getName()));
        final Optional<Category> foundCategory = categoryRepository.findByNameIgnoreCase("drama");

        assertThat(foundCategory.get()).isEqualTo(new Category("Drama"));
    }

    @Test
    void find_expensive_books() {
        saveBooks("Programming Language", "Java", "Hoseyn", 2000);
        saveBooks("Framework", "Spring Boot", "Mohammad", 5000);
        final List<Book> expensiveBooks = bookRepository.findExpensiveBooks(3000);

        final Book expectedBook ;

        assertThat(expensiveBooks)
                .extracting(Book::getTitle, Book::getAuthor, Book::getPrice)
                .containsExactly(Tuple.tuple("Spring Boot", "Mohammad", 5000));
    }

    @Test
    void find_books_by_category_name() {
        saveBooks("Programming Language", "Java", "Hoseyn", 2000);
        saveBooks("Framework", "Spring Boot", "Mohammad", 5000);

        final Optional<List<Book>> byCategory = bookRepository.findBooksByCategory("FRAM");

        assertThat(byCategory.get())
                .extracting(Book::getTitle, Book::getAuthor, Book::getPrice)
                .containsExactly(Tuple.tuple("Spring Boot", "Mohammad", 5000));
    }

    private Category saveBooks(String categoryName, String bookTitle, String bookAuthor, int bookPrice) {
        final Category category = new Category(categoryName);
        final Book book = new Book(bookTitle, bookAuthor, bookPrice);

        category.addBook(book);

        return categoryRepository.save(category);
    }
}
