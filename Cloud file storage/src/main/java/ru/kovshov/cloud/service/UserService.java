package ru.kovshov.cloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kovshov.cloud.exception.UserNotFoundExeption;
import ru.kovshov.cloud.model.User;
import ru.kovshov.cloud.repository.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findUserById(Long userId){
        return userRepository.findById(userId);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        List<User> userList =  userRepository.findAll();
        return userList;
    }

    public Optional<User> getUserByUserName(String username){
        return userRepository.findUserByUsername(username);
    }

    public Optional<User> getUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    public User getCurrentUser(Principal principal){
        return userRepository.findUserByEmail(principal.getName())
                .orElseThrow(() ->new UserNotFoundExeption("Username not found with username " + principal.getName()));
    }

    public Integer dellUser(String email){
        userRepository.deleteUserByEmail(email);
        return 1;
    }
}
