package ru.kovshov.cloud.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.kovshov.cloud.model.User;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private UserController userController;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;


    //check controller is not null
    @Test
    public void testControllerNotNull() throws Exception{
        assertThat(userController).isNotNull();
    }


    // test getting current users from bd
    @Test
    void getCurrentUser() throws Exception {
        mockMvc.perform(get("/api/user/3 "))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.id").isNumber())
//                .andExpect(jsonPath("$.username").value("Alexandr"));
    }


    // test sending new users from bd
    @Test
    void saveUser() throws Exception {
        User user = new User();
        user.setUsername("Alexandr");
        user.setPassword("Password");
        user.setEmail("alex@mail.ru");

        mockMvc.perform(
                post("/api/user/create")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.id").isNumber())
//                .andExpect(jsonPath("$.username").value("Alexandr"));
    }


//    @Test
//    public void givenUsers_whenGetUsers_thenStatus200()
//            throws Exception {
//
//
//        mockMvc.perform(get("/api/user")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content()
//                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].name", is("bob")));
//    }
}