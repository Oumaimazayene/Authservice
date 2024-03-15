package Auth.Authentification.Auth;

import Auth.Authentification.DTos.RoleDto;
import Auth.Authentification.Entity.Role;
import Auth.Authentification.Service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/roles")
public class RoleController {
    private   final RoleService roleService;

    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }
    @GetMapping("/roles")
    public List<RoleDto> getAllRoles() {
        return roleService.getAllRoles();
    }

    @PostMapping("/add")
    public ResponseEntity<?> createRole(@RequestBody RoleDto roleDTo) {
        return ResponseEntity.ok().body(roleService.createRole(roleDTo));
    }
    @PutMapping("/{id}")

    public RoleDto updateRole(@PathVariable Long id, @RequestBody RoleDto roleDTo) {
        return roleService.updateRole(id, roleDTo);
    }

    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Long id) {
        roleService
                .deleteRole(id);
    }
}

