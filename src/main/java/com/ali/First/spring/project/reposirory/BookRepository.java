package com.ali.First.spring.project.reposirory;

import com.ali.First.spring.project.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE b.price > :price")
    List<Book> findExpensiveBooks(@Param("price") int price) ;
    List<Book> findByCategoryId(Long categoryId);

    @Query("SELECT b FROM Book b WHERE LOWER(b.category.name) LIKE LOWER(CONCAT('%', :kind, '%'))")
    Optional<List<Book>> findBooksByCategory(@Param("kind") String kind);

}
