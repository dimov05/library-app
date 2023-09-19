package bg.libapp.libraryapp.service;

import bg.libapp.libraryapp.model.dto.genre.GenreDTO;
import bg.libapp.libraryapp.model.mappers.GenreMapper;
import bg.libapp.libraryapp.repository.GenreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GenreService {
    private final Logger logger = LoggerFactory.getLogger(GenreService.class);

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Set<GenreDTO> getAllGenres() {
        logger.info("getAllGenres method accessed");
        return this.genreRepository.findAll()
                .stream()
                .map(GenreMapper::mapToGenreDTO)
                .collect(Collectors.toSet());
    }
}
