package ru.kovshov.cloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kovshov.cloud.model.User;
import ru.kovshov.cloud.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserById(Long userId){
        return userRepository.findById(userId).get();
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }
}
