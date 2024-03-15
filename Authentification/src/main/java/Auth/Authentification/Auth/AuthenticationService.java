package Auth.Authentification.Auth;

import Auth.Authentification.Entity.User;
import Auth.Authentification.Repository.RoleRepository;
import Auth.Authentification.Repository.UserRepository;
import Auth.Authentification.config.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 12;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);


    public AuthentificationResponce register(registerRequest request) {
        var roleName = request.getRole().getName();
        var role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("Le rôle spécifié n'est pas valide."));

        var userBuilder = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .role(role);
        if ("recrutteur".equals(roleName)) {
            userBuilder.password(generateTemporaryPassword());

        } else {
            throw new IllegalArgumentException("Le rôle spécifié n'est pas accepté.");
        }
        var user = userBuilder.build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthentificationResponce.builder()
                .token(jwtToken)
                .build();
    }
    public AuthentificationResponce authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            var user = repository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            var jwtToken = jwtService.generateToken(user);
            return AuthentificationResponce.builder()
                    .token(jwtToken)
                    .build();
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid credentials", e);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("User not found", e);
        }
    }





    private String generateTemporaryPassword() {
        StringBuilder stringBuilder = new StringBuilder(PASSWORD_LENGTH);
        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int randomIndex = secureRandom.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }

}
