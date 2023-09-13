package bg.libapp.libraryapp.service;

import bg.libapp.libraryapp.model.dto.genre.GenreDTO;
import bg.libapp.libraryapp.model.mappers.GenreMapper;
import bg.libapp.libraryapp.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Set<GenreDTO> getAllGenres() {
        return this.genreRepository.findAll()
                .stream()
                .map(GenreMapper::mapToGenreDTO)
                .collect(Collectors.toSet());
    }
}
