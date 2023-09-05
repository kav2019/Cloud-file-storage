package ru.kovshov.cloud.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kovshov.cloud.facade.UserFacade;
import ru.kovshov.cloud.model.Role;
import ru.kovshov.cloud.model.User;
import ru.kovshov.cloud.payload.request.LoginRequest;
import ru.kovshov.cloud.payload.request.SignupRequest;
import ru.kovshov.cloud.payload.responce.error.InvalidLoginResponce;
import ru.kovshov.cloud.payload.responce.error.UserExistError;
import ru.kovshov.cloud.payload.responce.succes.JWTTokenSuccessResponse;
import ru.kovshov.cloud.security.SecurityConstants;
import ru.kovshov.cloud.security.ValidationPassword;
import ru.kovshov.cloud.security.old.JWTTokenProvaider;
import ru.kovshov.cloud.service.UserService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
@PreAuthorize("permitAll()")
public class AuthController {
    public static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;
    private final UserFacade userFacade;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvaider jwtTokenProvaider;
    private final ValidationPassword validationPassword;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public AuthController(UserService userService, UserFacade userFacade, AuthenticationManager authenticationManager, JWTTokenProvaider jwtTokenProvaider, ValidationPassword validationPassword, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userFacade = userFacade;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvaider = jwtTokenProvaider;
        this.validationPassword = validationPassword;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody SignupRequest signupRequest){
        LOG.info("we give email: {}", signupRequest.getEmail());
        Optional<User> userFromDb = userService.getUserByEmail(signupRequest.getEmail());
        if(userFromDb.isPresent()){
            return new ResponseEntity<>(new UserExistError(), HttpStatus.BAD_REQUEST);
        }
        System.out.println("!!!! GIVE name: " + signupRequest.getEmail());
        User newUser = new User();
        newUser.setUsername(signupRequest.getUsername());
        newUser.setEmail(signupRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        newUser.addRole(Role.USER);
        userService.saveUser(newUser);
        return new ResponseEntity<>(userFacade.userToUserDTO(newUser), HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<Object> authenticateUser(@RequestBody LoginRequest loginRequest){
        System.out.println("!!!! GIVE name: " + loginRequest.getUsername());
        if(!validationPassword.validPasswordAndUsername(loginRequest)){
            return new ResponseEntity<>(new InvalidLoginResponce(), HttpStatus.FORBIDDEN);
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvaider.generatedToken(authentication);
        System.out.println(jwt);
        return ResponseEntity.ok(new JWTTokenSuccessResponse(true, jwt));

    }

}
