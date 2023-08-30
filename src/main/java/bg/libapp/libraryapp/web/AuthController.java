package bg.libapp.libraryapp.web;

import bg.libapp.libraryapp.model.dto.user.UserLoginDTO;
import bg.libapp.libraryapp.model.dto.user.UserRegisterDTO;
import bg.libapp.libraryapp.model.entity.User;
import bg.libapp.libraryapp.model.mappers.UserMapper;
import bg.libapp.libraryapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private UserService userService;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        userLoginDTO.getUsername(), userLoginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println(authentication.getName());
        System.out.println(authentication.getAuthorities().toString());
        return new ResponseEntity<>("User logged in successfully!", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        if (userService.existsByUsername(userRegisterDTO.getUsername())) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }
        User userToSave = UserMapper.mapUserFromUserRegisterDTO(userRegisterDTO);
        userToSave.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));

        userService.save(userToSave);

        return new ResponseEntity<>(userToSave, HttpStatus.CREATED);
    }
}
