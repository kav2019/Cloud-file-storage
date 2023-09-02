package ru.kovshov.cloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.kovshov.cloud.model.User;

import java.util.Optional;

@Component
public class UserDetailService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public UserDetailService(UserService userService) {
        this.userService = userService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userService.getUserByUserName(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }
}
