package bg.libapp.libraryapp.service;

import bg.libapp.libraryapp.exceptions.UserIsAlreadyActivatedException;
import bg.libapp.libraryapp.exceptions.UserIsAlreadyDeactivatedException;
import bg.libapp.libraryapp.exceptions.UserNotFoundException;
import bg.libapp.libraryapp.exceptions.UserWithThisUsernameAlreadyExistsException;
import bg.libapp.libraryapp.model.dto.user.*;
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

    public UserDTO save(RegisterUserRequest registerUserRequest) {
        existsByUsername(registerUserRequest.getUsername());
        User userToSave = UserMapper.mapToUser(registerUserRequest);
        userToSave.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        userRepository.saveAndFlush(userToSave);
        return UserMapper.mapToUserDTO(userToSave);
    }

    public void existsByUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new UserWithThisUsernameAlreadyExistsException(username);
        }
    }

    public UserDTO findUserById(long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(UserMapper::mapToUserDTO).orElse(null);
    }

    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users
                .stream()
                .map(UserMapper::mapToUserDTO)
                .toList();
    }

    public UserDTO editUserAndSave(UpdateUserRequest updateUserRequest, long id) {
        User oldUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        editUser(updateUserRequest, oldUser);
        userRepository.saveAndFlush(oldUser);
        return UserMapper.mapToUserDTO(oldUser);
    }

    public UserDTO changeRoleAndSave(ChangeRoleRequest changeRoleRequest, long id) {
        User oldUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        oldUser.setRole(changeRoleRequest.getRole());
        userRepository.saveAndFlush(oldUser);
        return UserMapper.mapToUserDTO(oldUser);
    }

    public UserDTO changePasswordAndSave(ChangePasswordRequest changePasswordRequest, long id) {
        User oldUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        oldUser.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.saveAndFlush(oldUser);
        return UserMapper.mapToUserDTO(oldUser);
    }

    public String getUsernameById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id))
                .getUsername();
    }

    private static void editUser(UpdateUserRequest updateUserRequest, User oldUser) {
        oldUser
                .setFirstName(updateUserRequest.getFirstName())
                .setLastName(updateUserRequest.getLastName())
                .setDisplayName(updateUserRequest.getDisplayName());
    }

    public UserDTO deleteUserById(long id) {
        User toDelete = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userRepository.deleteById(id);
        return UserMapper.mapToUserDTO(toDelete);
    }

    public void logout() {
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        SecurityContextHolder.clearContext();
    }

    public UserDTO deactivateUser(long id) {
        User userToEdit = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        if (!userToEdit.isActive()) {
            throw new UserIsAlreadyDeactivatedException(id);
        }
        userToEdit.setActive(false);
        userRepository.saveAndFlush(userToEdit);
        return UserMapper.mapToUserDTO(userToEdit);
    }

    public UserDTO activateUser(long id) {
        User userToEdit = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        if (userToEdit.isActive()) {
            throw new UserIsAlreadyActivatedException(id);
        }
        userToEdit.setActive(true);
        userRepository.saveAndFlush(userToEdit);
        return UserMapper.mapToUserDTO(userToEdit);
    }

}
