package bg.libapp.libraryapp.web;

import bg.libapp.libraryapp.model.dto.genre.GenreDTO;
import bg.libapp.libraryapp.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_MODERATOR','ROLE_ADMIN')")
    public ResponseEntity<Set<GenreDTO>> getAllGenres() {
        return new ResponseEntity<>(genreService.getAllGenresViewDTO(), HttpStatus.OK);
    }
}
