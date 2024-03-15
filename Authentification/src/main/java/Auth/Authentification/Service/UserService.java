package Auth.Authentification.Service;

import Auth.Authentification.DTos.UserDto;
import Auth.Authentification.Entity.User;

import java.util.List;

public interface UserService {
    User getUserById(Long id);
    List<UserDto> getAllUser();
    UserDto updateUser(Long id, UserDto userDTo);
    void softDeleteUser(Long id);
    void processUserValidation(Long userId);
}
