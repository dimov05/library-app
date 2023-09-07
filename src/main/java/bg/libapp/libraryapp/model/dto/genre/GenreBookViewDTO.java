package bg.libapp.libraryapp.model.dto.genre;

public class GenreBookViewDTO {
    private String name;

    public GenreBookViewDTO() {
    }

    public GenreBookViewDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public GenreBookViewDTO setName(String name) {
        this.name = name;
        return this;
    }
}
