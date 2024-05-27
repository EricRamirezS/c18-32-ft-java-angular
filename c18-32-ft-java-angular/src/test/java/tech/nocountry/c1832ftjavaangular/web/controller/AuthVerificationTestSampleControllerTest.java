package tech.nocountry.c1832ftjavaangular.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthVerificationTestSampleControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getWithLoggedInRequiredButNotLoggedIn() throws Exception {
        mvc.perform(get("/test/auth/required")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void getWithLoggedInRequiredAndLoggedIn() throws Exception {
        mvc.perform(get("/test/auth/required")).andExpect(status().isOk()).andExpect(content().string(containsString(
                "true")));
    }

    @Test
    void getWithLoggedInNotRequired() throws Exception {
        mvc.perform(get("/test/auth/not-required")).andExpect(status().isOk()).andExpect(content().string(containsString("true")));
    }

    @Test
    @WithMockUser
    void getWithLoggedInNotRequiredAndLoggedIn() throws Exception {
        mvc.perform(get("/test/auth/not-required")).andExpect(status().isOk()).andExpect(content().string(containsString("true")));
    }

    @Test
    @WithMockUser
    void getWithLoggedInNotRequiredAndLoggedInCheckingWrongAnswer() throws Exception {
        mvc.perform(get("/test/auth/not-required")).andExpect(status().isOk()).andExpect(content().string(not(containsString("false"))));
    }
}