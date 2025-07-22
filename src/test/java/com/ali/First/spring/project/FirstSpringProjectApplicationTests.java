package com.ali.First.spring.project;

import com.ali.First.spring.project.reposirory.BookRepository;
import com.ali.First.spring.project.reposirory.CategoryRepository;
import com.ali.First.spring.project.service.BookServiceInterface;
import com.ali.First.spring.project.service.ServiceCategoryInterface;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class FirstSpringProjectApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private BookRepository repository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private BookServiceInterface serviceBook;

	@Autowired
	private ServiceCategoryInterface serviceCategory;

	@BeforeEach
	void setUp() {
		repository.deleteAll();
		categoryRepository.deleteAll();
	}

	@Test
	void Spring_context() throws Exception {

		// SAVE NEW CATEGORY
		String jsonCategory = """
							{
							"name": "Programming Language"
							}
							""";
		mvcPerformPost("/categories", jsonCategory, "$.name", "Programming Language");

		// --------------------------------------------------------------------------------------------------------

		// SAVE NEW CATEGORY
		String jsonCategory2 = """
							{
							"name": "Framework"
							}
							""";
		mvcPerformPost("/categories", jsonCategory2, "$.name", "Framework");

		// --------------------------------------------------------------------------------------------------------

		// SAVE NEW BOOK
		final String jsonBook = getJsonBook("Java", "Ali", "2000", "Programming Language");

		mvcPerformPost("/books", jsonBook, "$.author", "Ali");

		// --------------------------------------------------------------------------------------------------------

		// SAVE NEW BOOK
		final String jsonBook2 = getJsonBook("Spring", "Mohammad", "4000", "Framework");

		 mvcPerformPost("/books", jsonBook2, "$.category", "Category(name=Framework)");

		// --------------------------------------------------------------------------------------------------------

		 // FIND BOOK BY ID
		  mvcPerformGet("/books/id/2", "$.author", "Mohammad");

		// --------------------------------------------------------------------------------------------------------

		  // GET ALL BOOKS BY CATEGORY
		   mvcPerformGet("/books/category/Framework", "$[0].title", "Spring");

		// --------------------------------------------------------------------------------------------------------

		   // UPDATE BY ID
		final String jsonBook3 = getJsonBook("Java", "Ali", "50000", "Programming Language");
		mockMvc.perform(put("/books/id/1")
						.contentType(APPLICATION_JSON)
						.content(jsonBook3))
		                    .andExpect(status().isOk())
		                    .andExpect(jsonPath("$.title").value("Java"));

		// --------------------------------------------------------------------------------------------------------

		// DELETE BY ID
		mockMvc.perform(delete("/books/id/2/category_id/2"))
				.andExpect(status().isOk());

	}

	private void mvcPerformGet(String URL, String expression, String expectedValue) throws Exception {
		 mockMvc.perform(get(URL))
		                 .andExpect(status().isOk())
		                 .andExpect(jsonPath(expression).value(expectedValue));
	}

	private void mvcPerformPost(String URL,String content, String expression, String expectedValue) throws Exception {
		mockMvc.perform(post(URL)
				.contentType(APPLICATION_JSON)
				.content(content))
				.andExpect(status().isCreated())
				.andExpect(jsonPath(expression).value(expectedValue));
	}

	private String getJsonBook(String title, String author, String price, String category) {
		return "{\n" +
				"""
						"title":\s""" + "\"" + title + "\"" + ",\n" +
				"""
						"author":\s""" + "\"" + author + "\"" + ",\n" +
				"""
						"price":\s""" + "\"" + price + "\"" + ",\n" +
				"""
						"category":\s""" + "\"" + category + "\"" + "\n"
				+ "}";
	}
}
