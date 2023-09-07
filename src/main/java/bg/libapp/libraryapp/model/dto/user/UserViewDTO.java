package bg.libapp.libraryapp.model.dto.user;

import jakarta.persistence.Column;

import java.time.LocalDate;

public class UserViewDTO {
    private long id;
    private String username;

    private String firstName;

    private String lastName;

    private String displayName;

    private LocalDate dateOfBirth;

    private String role;

    public UserViewDTO() {
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

    public UserViewDTO setId(long id) {
        this.id = id;
        return this;
    }

    public UserViewDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserViewDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserViewDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserViewDTO setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public UserViewDTO setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public UserViewDTO setRole(String role) {
        this.role = role;
        return this;
    }
}
