package bg.libapp.libraryapp.model.mappers;

import bg.libapp.libraryapp.model.dto.genre.GenreDTO;
import bg.libapp.libraryapp.model.entity.Genre;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {
       public GenreDTO toGenreDTO(Genre genre) {
        return new GenreDTO()
                .setName(genre.getName());
    }
}
