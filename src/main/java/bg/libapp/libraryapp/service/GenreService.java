package bg.libapp.libraryapp.service;

import bg.libapp.libraryapp.model.dto.genre.GenreDTO;
import bg.libapp.libraryapp.model.mappers.GenreMapper;
import bg.libapp.libraryapp.repository.GenreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;
@Autowired
    public GenreService(GenreRepository genreRepository, GenreMapper genreMapper) {
        this.genreRepository = genreRepository;
        this.genreMapper = genreMapper;
    }

    public Set<GenreDTO> getAllGenresViewDTO() {
        return genreRepository.findAll()
                .stream()
                .map(genreMapper::toGenreDTO)
                .collect(Collectors.toSet());
    }
}
