package bg.libapp.libraryapp.service;

import bg.libapp.libraryapp.model.entity.User;
import bg.libapp.libraryapp.model.enums.Role;
import bg.libapp.libraryapp.model.security.AppUserDetails;
import bg.libapp.libraryapp.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

public class AppUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository
                .findByUsername(username)
                .map(this::mapUserToUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("User with this username '" + username + "' was not found!"));
    }

    private UserDetails mapUserToUserDetails(User user) {
        return new AppUserDetails(
                user.getUsername(),
                user.getPassword(),
                user.isActive(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + Role.values()[user.getRole()])));
    }
}
