package com.ali.First.spring.project.testController;

import com.ali.First.spring.project.DTO.RequestCategory;
import com.ali.First.spring.project.controller.ControllerCategory;
import com.ali.First.spring.project.model.Category;
import com.ali.First.spring.project.service.ServiceCategoryInterface;
import org.apache.coyote.http11.upgrade.UpgradeServletOutputStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ControllerCategory.class)
@Import(TestConfigController.class)
public class ControllerCategoryShould {

    @Autowired
    private ServiceCategoryInterface service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void save_category() throws Exception {
        RequestCategory newCategory = new RequestCategory("Scary");
        String categoryJson = """
                        {
                        "name": "Scary"
                        }
                        """;

         given(service.save(any(RequestCategory.class)))
                         .willReturn(new Category("Scary"));

          mockMvc.perform(post("/categories")
                          .contentType(APPLICATION_JSON)
                          .content(categoryJson))
                          .andExpect(status().isCreated())
                          .andExpect(jsonPath("$.name").value("Scary"));
    }
}
