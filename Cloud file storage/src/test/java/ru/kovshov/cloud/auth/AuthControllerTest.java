package ru.kovshov.cloud.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ru.kovshov.cloud.controller.UserController;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
//@WithUserDetails("Alexandr")
@WithMockUser(username="admin",password = "password", roles={"USER","ADMIN"})
// @TestProperySource("/application.properties") при запуске тестов приолодения система пойдет брать проперти в это метсо
//    например что бы подлючить тестовую бд
    @Sql(value = {"/other/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD) //ставится на уровне класс или метода,  на уровне класса будет выполняться ПЕРЕД каждым методом, код берет из ресурсов по имени файла
    @Sql(value = {"/other/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD) //ставится на уровне класс или метода,  на уровне класса будет выполняться ПОСЛЕ каждым методом
class AuthControllerTest {
    @Autowired
    private UserController userController;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    //проверяет что пользователь корректно индефецирован, тест пройдет ессли в контексте установлена веб сессия,
    // для добавления в контексте над классом или методом @WithUserDetails
    @Test
    public void mainPageTest() throws Exception{
        mockMvc.perform(get("/main"))
                .andExpect(SecurityMockMvcResultMatchers.authenticated());

    }
}
