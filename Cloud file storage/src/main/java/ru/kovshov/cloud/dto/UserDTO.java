package ru.kovshov.cloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kovshov.cloud.model.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private long id;
    private String username;
    private String email;

    public UserDTO userToUserDTO(User user){
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail());
    }
}
