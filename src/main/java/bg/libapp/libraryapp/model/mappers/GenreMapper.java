package bg.libapp.libraryapp.model.mappers;

import bg.libapp.libraryapp.model.dto.genre.GenreDTO;
import bg.libapp.libraryapp.model.entity.Genre;

public class GenreMapper {
       public static GenreDTO mapToGenreDTO(Genre genre) {
        return new GenreDTO()
                .setName(genre.getName());
    }
}
