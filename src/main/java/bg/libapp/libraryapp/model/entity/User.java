package bg.libapp.libraryapp.model.entity;

import bg.libapp.libraryapp.model.enums.Role;
import jakarta.persistence.*;
import org.yaml.snakeyaml.events.Event;

import java.time.LocalDate;

@Table
@Entity(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "username",length = 50,nullable = false,unique = true)
    private String username;
    @Column(name = "first_name",length = 50,nullable = false)
    private String firstName;
    @Column(name = "last_name",length = 50,nullable = false)
    private String lastName;
    @Column(name = "display_name",length = 50,nullable = false)
    private String displayName;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "date_of_birth",nullable = false)
    private LocalDate dateOfBirth;
    @Column(name = "role",nullable = false)
    private int role;

    public User() {
    }

    public User(String username, String firstName, String lastName, String displayName, String password, LocalDate dateOfBirth, int role) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = displayName;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.role = role;
    }

    public User(long id, String username, String firstName, String lastName, String displayName, String password, LocalDate dateOfBirth, int role) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = displayName;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public User setId(long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public User setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public User setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public int getRole() {
        return role;
    }

    public User setRole(int role) {
        this.role = role;
        return this;
    }
}
