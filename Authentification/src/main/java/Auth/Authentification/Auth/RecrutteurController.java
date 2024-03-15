package Auth.Authentification.Auth;

import Auth.Authentification.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recrutteur")
@RequiredArgsConstructor
public class RecrutteurController {
    @Autowired
    private UserService userService;


    @PostMapping("/validate/{userId}")
    public ResponseEntity<String> validateUser(@PathVariable Long userId) {
        try {
            userService.processUserValidation(userId);
            return ResponseEntity.ok("Validation réussie et email envoyé ");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la validation de l'utilisateur : " + e.getMessage());
        }
    }
}
