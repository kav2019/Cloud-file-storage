package ru.kovshov.cloud.facade;

import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;
import ru.kovshov.cloud.dto.UserDTO;
import ru.kovshov.cloud.model.User;

@Component
public class UserFacade {
    public UserDTO userToUserDTO(User user){
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail());
    }
}
