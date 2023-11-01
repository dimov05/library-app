package bg.libapp.libraryapp.model.dto.genre;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import static bg.libapp.libraryapp.model.constants.ApplicationConstants.GENRE_LENGTH_VALIDATION;
import static bg.libapp.libraryapp.model.constants.ApplicationConstants.GENRE_NOT_BLANK;

public class GenreRequest {
    @NotBlank(message = GENRE_NOT_BLANK)
    @Length(min = 2, message = GENRE_LENGTH_VALIDATION)
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