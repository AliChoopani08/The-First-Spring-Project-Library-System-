package com.ali.First.spring.project.repositoryTest;

import com.ali.First.spring.project.DTO.RequestCategory;
import com.ali.First.spring.project.model.Book;
import com.ali.First.spring.project.model.Category;
import com.ali.First.spring.project.reposirory.BookRepository;
import com.ali.First.spring.project.reposirory.CategoryRepository;
import org.assertj.core.api.Assertions;
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
    void find_books_by_category() {
        final Category programmingLanguage = new Category("Programming Language");
        final Category framework = new Category("Framework");
        final Book book1 = new Book("Java", "Hoseyn", 2000);
       programmingLanguage.addBook(book1);
        final Book book2 = new Book("Spring Boot", "Mohammad", 5000);
        framework.addBook(book2);

        categoryRepository.save(programmingLanguage);
        categoryRepository.save(framework);

        final Optional<Category> requestedCategory = categoryRepository.findByNameIgnoreCase("framework");
        System.out.println(requestedCategory.get().getId());
        final List<Book> booksByCategoryId = bookRepository.findByCategoryId(2L);
        System.out.println(booksByCategoryId);
        assertThat(booksByCategoryId.getFirst()).isEqualTo(book2);


    }

    @Test
    void add_new_category() {
        RequestCategory requestCategory = new RequestCategory("Drama");

        categoryRepository.save(new Category(requestCategory.getName()));
        final Optional<Category> foundCategory = categoryRepository.findByNameIgnoreCase("drama");

        assertThat(foundCategory.get()).isEqualTo(new Category("Drama"));
    }
}
