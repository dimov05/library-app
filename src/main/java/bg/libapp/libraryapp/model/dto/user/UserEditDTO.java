package bg.libapp.libraryapp.model.dto.user;

import bg.libapp.libraryapp.model.validation.UniqueUsername;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public class UserEditDTO {
    @NotEmpty(message = "First name can not be empty")
    @Length(min = 2, message = "First name should be at least 2 characters")
    private String firstName;
    @NotEmpty(message = "Last name can not be empty")
    @Length(min = 2, message = "Last name should be at least 2 characters")
    private String lastName;
    @NotEmpty(message = "Display name can not be empty")
    @Length(min = 3, message = "Display name should be at least 3 characters")
    private String displayName;

    public UserEditDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public UserEditDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEditDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public UserEditDTO setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }
}
