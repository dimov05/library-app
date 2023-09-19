package bg.libapp.libraryapp.model.mappers;

import bg.libapp.libraryapp.model.dto.user.ChangePasswordRequest;
import bg.libapp.libraryapp.model.dto.user.RegisterUserRequest;
import bg.libapp.libraryapp.model.dto.user.UpdateUserRequest;
import bg.libapp.libraryapp.model.dto.user.UserDTO;
import bg.libapp.libraryapp.model.entity.User;
import bg.libapp.libraryapp.model.enums.Role;

public class UserMapper {
    public static User mapToUser(RegisterUserRequest registerUserRequest) {
        return new User()
                .setUsername(registerUserRequest.getUsername())
                .setFirstName(registerUserRequest.getFirstName())
                .setLastName(registerUserRequest.getLastName())
                .setDisplayName(registerUserRequest.getDisplayName())
                .setPassword(registerUserRequest.getPassword()) // encoding in service
                .setDateOfBirth(registerUserRequest.getDateOfBirth())
                .setActive(true)
                .setRole(Role.USER.ordinal());
    }

    public static User mapToUser(UpdateUserRequest updateUserRequest) {
        return new User()
                .setFirstName(updateUserRequest.getFirstName())
                .setLastName(updateUserRequest.getLastName())
                .setDisplayName(updateUserRequest.getDisplayName());
    }

    public static User mapToUser(ChangePasswordRequest changePasswordRequest) {
        return new User()
                .setPassword(changePasswordRequest.getNewPassword());
    }

    public static UserDTO mapToUserDTO(User user) {
        return new UserDTO()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setDisplayName(user.getDisplayName())
                .setDateOfBirth(user.getDateOfBirth())
                .setRole(Role.values()[user.getRole()].name())
                .setActive(user.isActive());
    }
}