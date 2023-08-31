package ru.kovshov.cloud.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kovshov.cloud.dto.UserDTO;
import ru.kovshov.cloud.facade.UserFacade;
import ru.kovshov.cloud.model.User;
import ru.kovshov.cloud.service.UserService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    public static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    private final UserService  userService;
    private final UserFacade userFacade;

    @Autowired
    public UserController(UserService userService, UserFacade userFacade) {
        this.userService = userService;
        this.userFacade = userFacade;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getCurrentUser(@PathVariable("userId") long userId) {
        User user = userService.findUserById(userId);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserDTO userDTO = UserFacade.userToUserDTO(user);
        LOG.info("User {} send to user interface ", userDTO.getUsername());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> saveUser(@RequestBody User userRequest){
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        LOG.info("User {} save to form", user.getUsername());
        return new ResponseEntity<>(UserFacade.userToUserDTO(userService.saveUser(user)), HttpStatus.OK);
    }

}
