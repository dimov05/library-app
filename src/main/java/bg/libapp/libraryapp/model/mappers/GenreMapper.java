package bg.libapp.libraryapp.model.mappers;

import bg.libapp.libraryapp.model.dto.genre.GenreBookViewDTO;
import bg.libapp.libraryapp.model.dto.genre.GenreViewDTO;
import bg.libapp.libraryapp.model.entity.Genre;
import bg.libapp.libraryapp.repository.GenreRepository;
import bg.libapp.libraryapp.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {
    public GenreViewDTO mapGenreViewDTOFromGenre(Genre genre) {
        return new GenreViewDTO()
                .setName(genre.getName());
    }

    public GenreBookViewDTO mapGenreBookViewDTOFromGenre(Genre genre) {
        return new GenreBookViewDTO()
                .setName(genre.getName());
    }

    public Genre mapGenreFromGenreBookViewDTO(GenreBookViewDTO genreBookViewDTO) {
        return new Genre()
                .setName(genreBookViewDTO.getName());
    }
}
