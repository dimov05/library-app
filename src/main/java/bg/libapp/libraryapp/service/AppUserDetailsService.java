package bg.libapp.libraryapp.service;

import bg.libapp.libraryapp.model.entity.User;
import bg.libapp.libraryapp.model.enums.Role;
import bg.libapp.libraryapp.model.security.AppUserDetails;
import bg.libapp.libraryapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;


public class AppUserDetailsService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(AppUserDetailsService.class);
    private final UserRepository userRepository;

    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Logging user with this username '" + username + "'");
        return this.userRepository
                .findByUsername(username)
                .map(this::mapUserToUserDetails)
                .orElseThrow(() -> {
                    logger.info("User with username '" + username + "' was not found!");
                    return new UsernameNotFoundException("User with this username '" + username + "' was not found!");
                });
    }

    private UserDetails mapUserToUserDetails(User user) {
        return new AppUserDetails(
                user.getUsername(),
                user.getPassword(),
                user.isActive(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + Role.values()[user.getRole()])));
    }
}
