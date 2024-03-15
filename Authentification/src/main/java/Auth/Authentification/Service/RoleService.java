package Auth.Authentification.Service;

import Auth.Authentification.DTos.RoleDto;
import Auth.Authentification.Entity.Role;

import java.util.List;

public interface RoleService {
    Role getRoleById(Long id);
    List<RoleDto> getAllRoles();
    RoleDto createRole(RoleDto roleDTo);
    RoleDto updateRole(Long id, RoleDto roleDTo);
    void deleteRole(Long id);
}
