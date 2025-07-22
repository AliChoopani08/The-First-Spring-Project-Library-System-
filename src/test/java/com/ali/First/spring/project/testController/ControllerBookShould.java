package com.ali.First.spring.project.testController;

import com.ali.First.spring.project.DTO.BookRequest;
import com.ali.First.spring.project.DTO.BookSummary;
import com.ali.First.spring.project.controller.ControllerBookClass;
import com.ali.First.spring.project.exceptionHandler.NotFoundBook;
import com.ali.First.spring.project.exceptionHandler.NotFoundCategory;
import com.ali.First.spring.project.model.Book;
import com.ali.First.spring.project.service.BookServiceInterface;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ControllerBookClass.class)
@Import(TestConfigController.class)
public class ControllerBookShould {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookServiceInterface service;

    @Autowired
    private ModelMapper modelMapper;



    @Test
    void save_book() throws Exception {
        BookRequest bookRequest = new BookRequest("Java", "Ali", "20000", "Programming Language");
        final String jsonBook = getJsonBook("Java", "Ali", "20000", "Programming Language");

        given(service.saveBook(refEq(bookRequest)))
                .willReturn(new Book("Java", "Ali", 20000));
        givenMethodForMap(new BookSummary("Java", "Ali", 20000, "Programming Language"));

        mockMvc.perform(post("/books")
                .contentType(APPLICATION_JSON)
                .content(jsonBook))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Java"));
    }

    @Test
    void get_all_book_by_category() throws Exception {
        given(service.getAllBooksByCategory(argThat(equalsIgnoreCase("SQL"))))
                .willReturn(List.of(new Book("MySql", "Javad", 3000)));
        givenMethodForMap(new BookSummary("MySql", "Javad", 3000, "SQL"));

        mockMvc.perform(get("/books/category/sql"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].category").value("SQL"));
    }

    @Test
    void get_book_by_id__and_return_Book() throws Exception {
        Long id = 2L;

        given(service.getById(id))
                .willReturn(new Book("Spring", "Javad", 32000));
        givenMethodForMap(new BookSummary("Spring", "Javad", 32000, "Programming Language"));

        mockMvc.perform(get("/books/id/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("Javad"));
    }

    @Test
    void get_book_by_id_and_return_exception() throws Exception {
        Long id = 999L;

        given(service.getById(id))
                .willThrow(new NotFoundBook("User didn't find with this id !"));

        mockMvc.perform(get("/books/id/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not found"));
    }

    @Test
    void update_book_by_id() throws Exception {
        Long id = 3L;
        final String jsonBook = getJsonBook("mySQL", "Mohammad", "15000", "SQL");
        BookRequest updatingForBook = new BookRequest("mySQL", "Mohammad", "15000", "SQL");

        given(service.update(refEq(updatingForBook), eq(id)))
                .willReturn(new Book("mySQL", "Mohammad", 15000));
        givenMethodForMap(new BookSummary("mySQL", "Mohammad", 15000, "SQL"));

        mockMvc.perform(put("/books/id/3")
                .contentType(APPLICATION_JSON)
                .content(jsonBook))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("mySQL"));
    }

    @Test
    void delete_book_by_id() throws Exception {
        mockMvc.perform(delete("/books/id/4/category_id/2"))
                .andExpect(status().isOk());
    }

    @Test
    void return_exception_when_not_found_category() throws Exception {
        String requestedCategory = "comedy";

        given(service.getAllBooksByCategory(argThat(equalsIgnoreCase(requestedCategory))))
                .willThrow(new NotFoundCategory("Not found this category: " + requestedCategory));

         mockMvc.perform(get("/books/category/Comedy"))
                         .andExpect(status().isNotFound())
                         .andExpect(jsonPath("$.message").value("Not found this category: comedy"));
    }

    private String getJsonBook(String title, String author, String price, String category) {
        return "{ \n" +
                """
                "title":\s""" + "\"" + title + "\" ," +
                """
                "author":\s""" + "\"" + author + "\" ," +
                """
                "price":\s""" + "\"" + price + "\" ," +
                """
                "category":\s""" + "\"" + category + "\"" +
                "\n }";
    }

    private void givenMethodForMap(BookSummary summaryBook) {
        given(modelMapper.map(any(Book.class),eq(BookSummary.class)))
                        .willReturn(summaryBook);
    }

    private ArgumentMatcher<String> equalsIgnoreCase(String expected) {
        return input -> input != null
                && input.equalsIgnoreCase(expected);
    }
}
