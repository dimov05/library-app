package bg.libapp.libraryapp.web;

import bg.libapp.libraryapp.model.dto.user.UserEditDTO;
import bg.libapp.libraryapp.model.dto.user.UserRoleChangeDTO;
import bg.libapp.libraryapp.model.dto.user.UserViewDTO;
import bg.libapp.libraryapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @Autowired
    public UserController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping("/{id}")
//    @PreAuthorize("authentication.name == @userService.getUsernameById(#id)")
    public ResponseEntity<UserViewDTO> viewUserById(@PathVariable("id") Long id) {
        UserViewDTO userViewDTO = userService.findViewDTOById(id);
        if (userViewDTO != null) {
            return new ResponseEntity<>(userViewDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    public ResponseEntity<List<UserViewDTO>> getAllUsers() {
        List<UserViewDTO> users = this.userService.findAllUsersViewDTO();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
//    @userService.getUsernameById(#id) == authentication.name
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    public ResponseEntity<UserViewDTO> editUser(@Valid @RequestBody UserEditDTO userEditDTO, @PathVariable("id") long id) {
        this.userService.editUserAndSave(userEditDTO, id);
        UserViewDTO editedUser = this.userService.findViewDTOById(id);
        return new ResponseEntity<>(editedUser, HttpStatus.OK);
    }

    @PutMapping("/change-role/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UserViewDTO> changeRole(@Valid @RequestBody UserRoleChangeDTO userRoleChangeDTO, @PathVariable("id") long id) {
        this.userService.changeRoleAndSave(userRoleChangeDTO, id);
        UserViewDTO editedUser = this.userService.findViewDTOById(id);
        return new ResponseEntity<>(editedUser, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    public ResponseEntity<UserViewDTO> deleteUserById(@PathVariable("id") long id) {
        UserViewDTO deletedUser = this.userService.findViewDTOById(id);
        this.userService.deleteUserById(id);
        deletedUser.setUsername(deletedUser.getUsername() + "-deleted!");
        return new ResponseEntity<>(deletedUser, HttpStatus.OK);
    }
}
