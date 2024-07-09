package com.example.spring2.controller;

import com.example.spring2.repository.BrandRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BrandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BrandRepository brandRepository;

    @AfterEach
    void clean() {
        brandRepository.deleteAll();
    }

    @Test
    @WithMockUser
    void shouldReturnAllBrandsForRoleUser() throws Exception {
        mockMvc.perform(get("/brand"))
                .andExpect(status().is(200))
                .andExpect(content().string("[]"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void shouldReturnAllBrandsForRoleAdmin() throws Exception {
        mockMvc.perform(get("/brand"))
                .andExpect(status().is(200))
                .andExpect(content().string("[]"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void shouldCreateNewBrandForAdmin() throws Exception {
        long countBefore = brandRepository.count();

        mockMvc.perform(post("/brand")
                        .content("{\"name\":\"Saab\"}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());

        long countAfter = brandRepository.count();

        Assertions.assertEquals(countBefore + 1, countAfter);
    }

    @Test
    @WithMockUser
    void shouldntCreateNewBrandForUser() throws Exception {
        long countBefore = brandRepository.count();

        mockMvc.perform(post("/brand")
                        .content("{\"name\":\"Saab\"}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isForbidden());

        long countAfter = brandRepository.count();

        Assertions.assertEquals(countBefore, countAfter);
    }
}
