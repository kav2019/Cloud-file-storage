//package ru.kovshov.cloud.myTest;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.support.AnnotationConfigContextLoader;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//import ru.kovshov.cloud.model.User;
//import ru.kovshov.cloud.repository.UserRepository;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(
//        classes = { UserJpaConfig.class },
//        loader = AnnotationConfigContextLoader.class)
//@Transactional
//public class DatabaseTest {
//
//    @Autowired
//    private ObjectMapper objectMapper; // converts object to json
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private MockMvc mockMvc;
//
//
//    @WithMockUser //для тестирования методдов защищенных секюрити
//    @Test
//    public void saveUser() throws Exception{
//        User user = new User();
//        user.setEmail("controller@test.ru");
//        user.setUsername("ControllerTest");
//        user.setPassword("test123");
//
//
//
//        mockMvc.perform(post("http://localhost:8080/api/user/create")
//
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(user))).andExpect(status().isOk())
//                .andExpect(jsonPath("$.username").value("ControllerTest"))
//                .andExpect(jsonPath("$.email").value("controller@test.ru"));
//
//    }
//
//}
