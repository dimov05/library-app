package bg.libapp.libraryapp.service;

import bg.libapp.libraryapp.exceptions.UserNotFoundException;
import bg.libapp.libraryapp.model.dto.user.UserEditDTO;
import bg.libapp.libraryapp.model.dto.user.UserPasswordChangeDTO;
import bg.libapp.libraryapp.model.dto.user.UserRoleChangeDTO;
import bg.libapp.libraryapp.model.dto.user.UserViewDTO;
import bg.libapp.libraryapp.model.entity.User;
import bg.libapp.libraryapp.model.mappers.UserMapper;
import bg.libapp.libraryapp.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(User userToSave) {
        this.userRepository.saveAndFlush(userToSave);
    }

    public boolean existsByUsername(String username) {
        return this.userRepository.existsByUsername(username);
    }

    public UserViewDTO findViewDTOById(long id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.map(UserMapper::mapViewDTOFromUser).orElse(null);
    }

    public List<UserViewDTO> findAllUsersViewDTO() {
        List<User> users = this.userRepository.findAll();
        return users
                .stream()
                .map(UserMapper::mapViewDTOFromUser)
                .toList();
    }

    public void editUserAndSave(UserEditDTO userEditDTO, long id) {
        User oldUser = this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with this id is not found! - " + id));
        editUserWithUserEditDTOData(userEditDTO, oldUser);
        this.userRepository.saveAndFlush(oldUser);
    }

    public void changeRoleAndSave(UserRoleChangeDTO userRoleChangeDTO, long id) {
        User oldUser = this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with this id is not found! - " + id));
        oldUser.setRole(userRoleChangeDTO.getRole());
        this.userRepository.saveAndFlush(oldUser);
    }

    public void changePasswordAndSave(UserPasswordChangeDTO userPasswordChangeDTO, long id) {
        User oldUser = this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with this id is not found! - " + id));
        oldUser.setPassword(passwordEncoder.encode(userPasswordChangeDTO.getNewPassword()));
        this.userRepository.saveAndFlush(oldUser);
    }

    public String getUsernameById(long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with this id is not found! - " + id))
                .getUsername();
    }

    private static void editUserWithUserEditDTOData(UserEditDTO userEditDTO, User oldUser) {
        oldUser
                .setFirstName(userEditDTO.getFirstName())
                .setLastName(userEditDTO.getLastName())
                .setDisplayName(userEditDTO.getDisplayName());
    }

    public void deleteUserById(long id) {
        this.userRepository.deleteById(id);
    }

    public void logout() {
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        SecurityContextHolder.clearContext();
    }
}
