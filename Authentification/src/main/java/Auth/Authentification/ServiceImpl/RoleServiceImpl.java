package Auth.Authentification.ServiceImpl;

import Auth.Authentification.DTos.RoleDto;
import Auth.Authentification.Entity.Role;
import Auth.Authentification.Mappers.RoleMapp;
import Auth.Authentification.Repository.RoleRepository;
import Auth.Authentification.Service.RoleService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private  final RoleMapp roleMapp;

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElse(null);

    }

    @Override
    public List<RoleDto> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapp::ToRoleDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDto createRole(RoleDto roleDTo) {
        return roleMapp.ToRoleDto(roleRepository.save(roleMapp.ToRole(roleDTo)));

    }

        @Override
    public RoleDto updateRole(Long id, RoleDto roleDTo) {
        Optional<Role> existingRole = roleRepository.findById(id);
        if (existingRole.isPresent()) {
            roleMapp.updateRoleFromDTO(roleDTo, existingRole.get());
            return roleMapp.ToRoleDto(roleRepository.save(existingRole.get()));
        }
        return null;
    }
    @Override
    public void deleteRole(Long id) {
        try {
            roleRepository.deleteById(id);
            System.out.println("Role deleted successfully with ID: " + id);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("Role with ID " + id + " not found");
            throw e;
        } catch (Exception e) {
            System.err.println("Error deleting Role");
            throw e;
        }
    }

}
