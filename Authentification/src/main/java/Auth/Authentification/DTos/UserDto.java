package Auth.Authentification.DTos;

import Auth.Authentification.Entity.Role;
import lombok.Data;

@Data
public class UserDto {
    private String firstname;
    private String lastname;
    private  String email;
    private String password;
    private boolean isDeleted ;
    private boolean isVerified;
    private String societyName;
    private  String Signature ;
    private String Logo;
    private RoleDto  role;




}
