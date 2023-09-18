package bg.libapp.libraryapp.model.dto.user;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class UpdateUserRequest {
    @NotEmpty(message = "First name can not be empty")
    @Length(min = 2, message = "First name should be at least 2 characters")
    private String firstName;
    @NotEmpty(message = "Last name can not be empty")
    @Length(min = 2, message = "Last name should be at least 2 characters")
    private String lastName;
    @NotEmpty(message = "Display name can not be empty")
    @Length(min = 3, message = "Display name should be at least 3 characters")
    private String displayName;

    public UpdateUserRequest() {
    }

    public String getFirstName() {
        return firstName;
    }

    public UpdateUserRequest setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UpdateUserRequest setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public UpdateUserRequest setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}