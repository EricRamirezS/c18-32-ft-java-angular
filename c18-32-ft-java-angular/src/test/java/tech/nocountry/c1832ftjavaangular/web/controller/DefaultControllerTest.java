package tech.nocountry.c1832ftjavaangular.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DefaultControllerTest {

    @Autowired
    private MockMvc mvc;


    @Test
    public void getNonExistingEndpointWhenNotLoggedIn() throws Exception {
        mvc.perform(get("/I-dont-exist"))
                .andExpect(status().isUnauthorized());
    }
    @Test
    @WithMockUser
    public void getNonExistingEndpointWhenLoggedIn() throws Exception {
        mvc.perform(get("/I-dont-exist"))
                .andExpect(status().isNotFound());
    }


    @Test
    void getDefaultEndpoint() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isFound());
    }

    @Test
    void getRedirectedToSwagger() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/swagger-ui/index.html"));
    }
}
