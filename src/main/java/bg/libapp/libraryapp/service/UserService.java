package bg.libapp.libraryapp.service;

import bg.libapp.libraryapp.model.dto.user.UserEditDTO;
import bg.libapp.libraryapp.model.dto.user.UserRoleChangeDTO;
import bg.libapp.libraryapp.model.dto.user.UserViewDTO;
import bg.libapp.libraryapp.model.entity.User;
import bg.libapp.libraryapp.model.mappers.UserMapper;
import bg.libapp.libraryapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User userToSave) {
        this.userRepository.save(userToSave);
    }

    public boolean existsByUsername(String username) {
        return this.userRepository.existsByUsername(username);
    }

    public UserViewDTO findViewDTOById(long id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.map(UserMapper::mapViewDTOFromUser).orElse(null);
    }

    public UserViewDTO findViewDTOByUsername(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with this username is not found! - " + username));
        return UserMapper.mapViewDTOFromUser(user);
    }

    public List<UserViewDTO> findAllUsersViewDTO() {
        List<User> users = this.userRepository.findAll();
        return users
                .stream()
                .map(UserMapper::mapViewDTOFromUser)
                .collect(Collectors.toList());
    }

    public void editUserAndSave(UserEditDTO userEditDTO, long id) {
        User oldUser = this.userRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("User with this id is not found! - " + id));
        editUserWithUserEditDTOData(userEditDTO, oldUser);
        this.userRepository.save(oldUser);
    }

    public void changeRoleAndSave(UserRoleChangeDTO userRoleChangeDTO, long id) {
        User oldUser = this.userRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("User with this id is not found! - " + id));
        oldUser.setRole(userRoleChangeDTO.getRole());
        this.userRepository.save(oldUser);
    }

    public String getUsernameById(long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("User with this id is not found! - " + id))
                .getUsername();
    }

    private static User editUserWithUserEditDTOData(UserEditDTO userEditDTO, User oldUser) {
        return oldUser
                .setFirstName(userEditDTO.getFirstName())
                .setLastName(userEditDTO.getLastName())
                .setDisplayName(userEditDTO.getDisplayName());
    }

    public void deleteUserById(long id) {
        this.userRepository.deleteById(id);
    }
}
