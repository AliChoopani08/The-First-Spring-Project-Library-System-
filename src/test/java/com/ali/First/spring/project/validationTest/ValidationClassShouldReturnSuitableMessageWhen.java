package com.ali.First.spring.project.validationTest;

import com.ali.First.spring.project.controller.ControllerBookClass;
import com.ali.First.spring.project.testController.TestConfigController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ControllerBookClass.class)
@Import(TestConfigController.class)
public class ValidationClassShouldReturnSuitableMessageWhen {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void title_is_empty() throws Exception {
        String jsonBook = """
                       {
                          "title": "",
                          "author": "Ali",
                          "price": "12000"
                       }
                  """;

        mvcPerforms(post("/books"), jsonBook, "$.title", "Title of book mustn't be empty !");
    }


    @Test
    void author_is_invalid() throws Exception {
        String jsonBook = """
                       {
                          "title": "Java",
                          "author": "Ali14#%",
                          "price": "12000"
                       }
                  """;

        mvcPerforms(put("/books/3"), jsonBook, "$.author", "author should be combination of ((a-z) - (A-Z)");
    }

    @Test
    void price_is_invalid() throws Exception {
        String jsonBook = """
                       {
                          "title": "Java",
                          "author": "Ahmad",
                          "price": "1200k1l@"
                       }
                  """;

        mvcPerforms(put("/books/5"), jsonBook, "$.price", "Price is invalid");
    }

    private void mvcPerforms(MockHttpServletRequestBuilder request, String jsonFile, String fieldName, String fieldValue) throws Exception {
        mockMvc.perform(request
                 .contentType(APPLICATION_JSON)
                 .content(jsonFile))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(fieldName).value(fieldValue));
    }
}
