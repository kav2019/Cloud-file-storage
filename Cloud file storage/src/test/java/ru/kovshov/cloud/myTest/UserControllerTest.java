package ru.kovshov.cloud.myTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ru.kovshov.cloud.model.User;
import ru.kovshov.cloud.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@WebMvcTest(UserController.class)
// @WithMockUser(username="admin",password = "password", roles={"USER","ADMIN"})


// @TestProperySource("/application.properties") при запуске тестов приолодения система пойдет брать проперти в это метсо
//    например что бы подлючить тестовую бд
@Sql(value = {"/other/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD) //ставится на уровне класс или метода,  на уровне класса будет выполняться ПЕРЕД каждым методом, код берет из ресурсов по имени файла
@Sql(value = {"/other/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private ObjectMapper objectMapper; // converts object to json
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;


//    @AfterEach
//    public void resetDb(){
//        userRepository.delete(user);
//    }

    @WithMockUser //для тестирования методдов защищенных секюрити
    @Test
    public void saveUser() throws Exception{
        User user = new User();
        user.setEmail("controller@test.ru");
        user.setUsername("ControllerTest");
        user.setPassword("test123");



        mockMvc.perform(post("http://localhost:8080/api/user/create")

                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))).andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("ControllerTest"))
                        .andExpect(jsonPath("$.email").value("controller@test.ru"));

    }


    @WithMockUser
    @Test
    public void deleteUser() throws Exception{

        User user = new User();
        user.setEmail("controller@test.ru");
        user.setUsername("ControllerTest");
        user.setId(11);

        mockMvc.perform(post("http://localhost:8080/api/user/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());

    }





}
