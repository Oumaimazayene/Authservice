package Auth.Authentification.Auth;

import Auth.Authentification.Entity.Role;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class registerRequest {
    private String firstname;
    private String lastname;
    private  String email;
private Role role ;
}
