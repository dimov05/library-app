package bg.libapp.libraryapp.model.dto.user;

import bg.libapp.libraryapp.model.validation.FieldMatch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

@FieldMatch(first = "password", second = "confirmPassword", message = "Passwords must match")
public class UserPasswordChangeDTO {
    private String username;
    @NotBlank(message = "Password can not be empty")
    @Length(min = 6, max = 100, message = "Password should be at least 6 symbols")
    private String oldPassword;
    @NotBlank(message = "Password can not be empty")
    @Length(min = 6, max = 100, message = "Password should be at least 6 symbols")
    private String newPassword;

    @NotBlank(message = "Password can not be empty")
    @Length(min = 6, max = 100, message = "Password should be at least 6 symbols")
    private String confirmNewPassword;

    public UserPasswordChangeDTO() {
    }

    public String getUsername() {
        return username;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public UserPasswordChangeDTO setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
        return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public UserPasswordChangeDTO setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public UserPasswordChangeDTO setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
        return this;
    }
}
