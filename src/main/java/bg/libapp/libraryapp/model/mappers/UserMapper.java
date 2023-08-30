package bg.libapp.libraryapp.model.mappers;

import bg.libapp.libraryapp.model.dto.user.UserEditDTO;
import bg.libapp.libraryapp.model.dto.user.UserPasswordChangeDTO;
import bg.libapp.libraryapp.model.dto.user.UserRegisterDTO;
import bg.libapp.libraryapp.model.dto.user.UserViewDTO;
import bg.libapp.libraryapp.model.entity.User;
import bg.libapp.libraryapp.model.enums.Role;

import java.util.Optional;

public class UserMapper {

    public static User mapUserFromUserRegisterDTO(UserRegisterDTO userRegisterDTO) {
        return new User()
                .setUsername(userRegisterDTO.getUsername())
                .setFirstName(userRegisterDTO.getFirstName())
                .setLastName(userRegisterDTO.getLastName())
                .setDisplayName(userRegisterDTO.getDisplayName())
                .setPassword(userRegisterDTO.getPassword()) // encoding in service
                .setDateOfBirth(userRegisterDTO.getDateOfBirth())
                .setRole(Role.USER.ordinal());
    }

    public static User mapUserFromUserEditDTO(UserEditDTO userEditDTO) {
        return new User()
                .setFirstName(userEditDTO.getFirstName())
                .setLastName(userEditDTO.getLastName())
                .setDisplayName(userEditDTO.getDisplayName());
    }

    public static User mapUserFromUserPasswordChangeDTO(UserPasswordChangeDTO userPasswordChangeDTO) {
        return new User()
                .setPassword(userPasswordChangeDTO.getNewPassword());
    }

    public static UserViewDTO mapViewDTOFromUser(User user) {
        return new UserViewDTO()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setDisplayName(user.getDisplayName())
                .setDateOfBirth(user.getDateOfBirth())
                .setRole(Role.values()[user.getRole()].name());
    }
}
