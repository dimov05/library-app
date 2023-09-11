package bg.libapp.libraryapp.service;

import bg.libapp.libraryapp.exceptions.UserNotFoundException;
import bg.libapp.libraryapp.model.dto.user.UpdateUserRequest;
import bg.libapp.libraryapp.model.dto.user.ChangePasswordRequest;
import bg.libapp.libraryapp.model.dto.user.ChangeRoleRequest;
import bg.libapp.libraryapp.model.dto.user.UserDTO;
import bg.libapp.libraryapp.model.entity.User;
import bg.libapp.libraryapp.model.mappers.UserMapper;
import bg.libapp.libraryapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(User userToSave) {
        userRepository.saveAndFlush(userToSave);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public UserDTO findViewDTOById(long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(UserMapper::toUserDTO).orElse(null);
    }

    public List<UserDTO> findAllUsersViewDTO() {
        List<User> users = userRepository.findAll();
        return users
                .stream()
                .map(UserMapper::toUserDTO)
                .toList();
    }

    public UserDTO editUserAndSave(UpdateUserRequest updateUserRequest, long id) {
        User oldUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        editUserWithUserEditDTOData(updateUserRequest, oldUser);
        userRepository.saveAndFlush(oldUser);
        return UserMapper.toUserDTO(oldUser);
    }

    public UserDTO changeRoleAndSave(ChangeRoleRequest changeRoleRequest, long id) {
        User oldUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        oldUser.setRole(changeRoleRequest.getRole());
        userRepository.saveAndFlush(oldUser);
        return UserMapper.toUserDTO(oldUser);
    }

    public UserDTO changePasswordAndSave(ChangePasswordRequest changePasswordRequest, long id) {
        User oldUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        oldUser.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.saveAndFlush(oldUser);
        return UserMapper.toUserDTO(oldUser);
    }

    public String getUsernameById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id))
                .getUsername();
    }

    private static void editUserWithUserEditDTOData(UpdateUserRequest updateUserRequest, User oldUser) {
        oldUser
                .setFirstName(updateUserRequest.getFirstName())
                .setLastName(updateUserRequest.getLastName())
                .setDisplayName(updateUserRequest.getDisplayName());
    }

    public UserDTO deleteUserById(long id) {
        User toDelete = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userRepository.deleteById(id);
        return UserMapper.toUserDTO(toDelete);
    }

    public void logout() {
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        SecurityContextHolder.clearContext();
    }
}
