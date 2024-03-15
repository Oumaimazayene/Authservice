package Auth.Authentification.Mappers;

import Auth.Authentification.DTos.UserDto;
import Auth.Authentification.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapp {
    User ToUsers(UserDto userDTo);
    UserDto ToUserDTo(User user);

    void updateUser(UserDto userDTo,@MappingTarget User existingUser);
}
