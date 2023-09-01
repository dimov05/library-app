package bg.libapp.libraryapp.model.dto.genre;

import bg.libapp.libraryapp.model.entity.Book;

import java.util.Set;

public class GenreViewDTO {
    private String name;
    public GenreViewDTO() {
    }

    public GenreViewDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public GenreViewDTO setName(String name) {
        this.name = name;
        return this;
    }
}
