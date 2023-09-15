package bg.libapp.libraryapp.model.dto.user;

import java.time.LocalDate;

public class UserDTO {
    private long id;
    private String username;

    private String firstName;

    private String lastName;

    private String displayName;

    private LocalDate dateOfBirth;

    private String role;

    public UserDTO() {
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getRole() {
        return role;
    }

    public UserDTO setId(long id) {
        this.id = id;
        return this;
    }

    public UserDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserDTO setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public UserDTO setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public UserDTO setRole(String role) {
        this.role = role;
        return this;
    }
}
