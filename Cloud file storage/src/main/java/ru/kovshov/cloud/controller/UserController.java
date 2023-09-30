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

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public ResponseEntity<UserDTO> getCurrentUser(Principal principal){
        User user = userService.getCurrentUser(principal);
        UserDTO userDTO = userFacade.userToUserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);

    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getCurrentUser(@PathVariable("userId") long userId) {
        Optional<User> user = userService.findUserById(userId);
        if(user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserDTO userDTO = userFacade.userToUserDTO(user.get());
        LOG.info("User {} send to user interface ", userDTO.getUsername());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> saveUser(@RequestBody User userRequest){
        if(userRequest == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        LOG.info("User {} save to form", user.getUsername());
        return new ResponseEntity<>(userFacade.userToUserDTO(userService.saveUser(user)), HttpStatus.OK);
    }


    // FIX RETURN data when userList is null
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<User> userList = userService.getAllUsers();
        if (userList.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        List<UserDTO> userDTOList = userList.stream()
                .map(x -> userFacade.userToUserDTO(x))
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<HttpStatus> deletUser(@RequestBody UserDTO userDTO){
        if(userService.dellUser(userDTO.getEmail()) == 1){
            return new ResponseEntity<>(HttpStatus.OK);
        };
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
