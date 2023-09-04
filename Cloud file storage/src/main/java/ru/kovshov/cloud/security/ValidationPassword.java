package ru.kovshov.cloud.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kovshov.cloud.controller.UserController;
import ru.kovshov.cloud.model.User;
import ru.kovshov.cloud.payload.request.LoginRequest;
import ru.kovshov.cloud.service.UserService;

import java.util.Optional;

@Component
public class ValidationPassword {
    public static final Logger LOG = LoggerFactory.getLogger(ValidationPassword.class);
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ValidationPassword(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean validPasswordAndUsername(LoginRequest loginRequest){
        Optional<User> user = userService.getUserByUserName(loginRequest.getUsername());
        if (user.isPresent()){
            String passFromDB = user.get().getPassword();
            String passFromForm = loginRequest.getPassword();
            LOG.info("pass from DB -> {}", passFromDB);
            LOG.info("pass from FORM -> {}", passFromForm);
            System.out.println(passwordEncoder.matches(passFromForm, passFromDB) ? "TRUE" : "false");
            if(passwordEncoder.matches(passFromForm, passFromDB)){
                return true;
            }
        }
        return false;
    }
}
