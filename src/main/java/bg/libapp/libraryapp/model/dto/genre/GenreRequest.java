package bg.libapp.libraryapp.model.dto.genre;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class GenreRequest {
    @NotBlank(message = "Genre must not be blank")
    @Length(min = 2, message = "Genre length must be at least 2 symbols")
    private String name;

    public GenreRequest() {
    }

    public GenreRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public GenreRequest setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                '}';
    }
}