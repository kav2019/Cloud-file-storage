package ru.kovshov.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kovshov.cloud.dto.UserDTO;
import ru.kovshov.cloud.exeprions.UserNotFoundExeption;
import ru.kovshov.cloud.model.User;
import ru.kovshov.cloud.repository.UserRepository;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getCurrentUser(@PathVariable("userId") long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserDTO userDTO = new UserDTO();
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

}
