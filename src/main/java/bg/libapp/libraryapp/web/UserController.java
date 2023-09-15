package bg.libapp.libraryapp.web;

import bg.libapp.libraryapp.model.dto.user.UpdateUserRequest;
import bg.libapp.libraryapp.model.dto.user.ChangePasswordRequest;
import bg.libapp.libraryapp.model.dto.user.ChangeRoleRequest;
import bg.libapp.libraryapp.model.dto.user.UserDTO;
import bg.libapp.libraryapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MODERATOR') or authentication.name == @userService.getUsernameById(#id)")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
        UserDTO userDTO = userService.findViewDTOById(id);
        String principal = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(principal);
        if (userDTO != null) {
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.findAllUsersViewDTO();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @PutMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MODERATOR') or @userService.getUsernameById(#id) == authentication.name")
    public ResponseEntity<UserDTO> editUser(@Valid @RequestBody UpdateUserRequest updateUserRequest, @PathVariable("id") long id) {
        userService.editUserAndSave(updateUserRequest, id);
        UserDTO editedUser = userService.findViewDTOById(id);
        return new ResponseEntity<>(editedUser, HttpStatus.OK);
    }

    @PutMapping("/change-role/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> changeRole(@Valid @RequestBody ChangeRoleRequest changeRoleRequest, @PathVariable("id") long id) {
        userService.changeRoleAndSave(changeRoleRequest, id);
        UserDTO editedUser = userService.findViewDTOById(id);
        return new ResponseEntity<>(editedUser, HttpStatus.OK);
    }

    @PutMapping("/change-password/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or @userService.getUsernameById(#id) == authentication.name")
    public ResponseEntity<UserDTO> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest, @PathVariable("id") long id) {
        userService.changePasswordAndSave(changePasswordRequest, id);
        UserDTO editedUser = userService.findViewDTOById(id);
        return new ResponseEntity<>(editedUser, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<UserDTO> deleteUserById(@PathVariable("id") long id) {
        UserDTO deletedUser = userService.findViewDTOById(id);
        userService.deleteUserById(id);
        deletedUser.setUsername(deletedUser.getUsername() + "-deleted!");
        return new ResponseEntity<>(deletedUser, HttpStatus.OK);
    }
}
