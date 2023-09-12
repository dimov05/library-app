package bg.libapp.libraryapp.model.dto.user;

import bg.libapp.libraryapp.model.validation.FieldMatch;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@FieldMatch(first = "newPassword", second = "confirmNewPassword", message = "Passwords must match")
public class ChangePasswordRequest {
    @NotBlank(message = "Password can not be empty")
    @Length(min = 6, max = 100, message = "Password should be at least 6 symbols")
    private String oldPassword;
    @NotBlank(message = "Password can not be empty")
    @Length(min = 6, max = 100, message = "Password should be at least 6 symbols")
    private String newPassword;

    @NotBlank(message = "Password can not be empty")
    @Length(min = 6, max = 100, message = "Password should be at least 6 symbols")
    private String confirmNewPassword;

    public ChangePasswordRequest() {
    }


    public String getOldPassword() {
        return oldPassword;
    }

    public ChangePasswordRequest setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
        return this;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public ChangePasswordRequest setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public ChangePasswordRequest setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
        return this;
    }
}