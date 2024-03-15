package Auth.Authentification.Mappers;

import Auth.Authentification.DTos.RoleDto;
import Auth.Authentification.Entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapp {
Role ToRole(RoleDto roleDto);
RoleDto ToRoleDto(Role role);

    void updateRoleFromDTO(RoleDto roleDTo, @MappingTarget Role existingRole  );
}
