package bg.libapp.libraryapp.model.dto.author;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class AuthorRequest {
    @NotEmpty(message = "First name can not be empty")
    @Length(min = 2, message = "First name should be at least 2 characters")
    private String firstName;

    @NotEmpty(message = "Last name can not be empty")
    @Length(min = 2, message = "Last name should be at least 2 characters")
    private String lastName;

    public AuthorRequest() {
    }

    public AuthorRequest(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public AuthorRequest setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AuthorRequest setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}