package Auth.Authentification.ServiceImpl;

import Auth.Authentification.DTos.UserDto;
import Auth.Authentification.Entity.User;
import Auth.Authentification.Mappers.UserMapp;
import Auth.Authentification.Repository.UserRepository;
import Auth.Authentification.Service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
@Service
public class UserServiceImpl implements UserService {
    private  final UserRepository userRepository;
    private final UserMapp userMapp;
    private final  EmailServiceImpl emailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }


    @Override
    public List<UserDto> getAllUser() {
        return userRepository.findAll().stream()
                .map(userMapp::ToUserDTo)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDTo) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            userMapp.updateUser(userDTo, existingUser.get());
            return userMapp.ToUserDTo(userRepository.save(existingUser.get()));
        }
        return null;    }


    @Override
    public void softDeleteUser(Long id) {
        try {
            Optional<User> optionalUser = userRepository.findById(id);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.setDeleted(true);
                userRepository.save(user);
                System.out.println("User with ID: " + id + " soft-deleted successfully");
            } else {
                System.out.println("User with ID " + id + " not found");
                // Handle not found scenario
            }
        } catch (Exception e) {
            System.err.println("Error soft-deleting User");
            throw e;
        }
    }

@Override

    public void processUserValidation(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (!user.isVerified()) {
                // Si l'utilisateur n'est pas encore validé, procédez à la validation
                user.setVerified(true);
                userRepository.save(user);

                // Envoi de l'email de validation
                sendValidationEmail(user);
            } else {
                System.out.println("l'utilisateur  est déja verifier ");            }
        } else {
            System.out.println("utilisateur avec l'id "+userId+"n'est pas trouvé");        }
    }

    private void sendValidationEmail(User user) {
        if (user.isVerified() && "recrutteur".equals(user.getRole().getName())) {
            String subject = "Validation de Compte WindTestHub";
            String body = "Cher recruteur,\n\n"
                    + "Votre compte a été validé avec succès. Nous vous remercions de votre inscription.\n"
                    + "Veuillez trouver ci-dessous les détails de votre compte :\n\n"
                    + "Nom: " + user.getLastname() + "\n"
                    + "Prénom: " + user.getFirstname() + "\n"
                    + "E-mail: " + user.getEmail() + "\n"
                    + "Rôle: " + user.getRole().getName() + "\n"
                    + "Mot de passe: " + user.getPassword() + "\n\n"
                    + "Bienvenue dans notre communauté !\n\n"
                    + "Cordialement,\n"
                    + "L'équipe de Wind Consulting Tunisia";
            emailService.sendEmail(user.getEmail(), subject, body);
        }
    }


}
