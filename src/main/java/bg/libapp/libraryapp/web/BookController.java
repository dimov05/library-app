package bg.libapp.libraryapp.web;

import bg.libapp.libraryapp.model.dto.book.*;
import bg.libapp.libraryapp.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<?> addBook(@Valid @RequestBody BookAddRequest bookAddRequest) {
        return new ResponseEntity<>(this.bookService.saveNewBook(bookAddRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{isbn}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER','ROLE_MODERATOR')")
    public ResponseEntity<BookExtendedDTO> getBookByIsbn(@PathVariable("isbn") String isbn) {
        return new ResponseEntity<>(this.bookService.findBookByIsbn(isbn), HttpStatus.OK);
    }

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER','ROLE_MODERATOR')")
    public ResponseEntity<Set<BookExtendedDTO>> getBooksFiltered(@Valid BookFilterRequest bookFilterRequest) {

        return new ResponseEntity<>(bookService.getBooksFiltered(bookFilterRequest), HttpStatus.OK);
    }

    @GetMapping("/author/{firstName},{lastName}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER','ROLE_MODERATOR')")
    public ResponseEntity<?> getAllBooksByAuthor(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
        return new ResponseEntity<>(bookService.getAllBooksByAuthorFirstAndLastName(firstName, lastName), HttpStatus.OK);
    }

    //get all books by genre
    @GetMapping("/genre/{genre}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER','ROLE_MODERATOR')")
    public ResponseEntity<Set<BookExtendedDTO>> getAllBooksByGenre(@PathVariable("genre") String genre) {
        return new ResponseEntity<>(bookService.getAllBooksByGenre(genre), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{isbn}")
    public ResponseEntity<BookDTO> deleteBookById(@PathVariable("isbn") String isbn) {
        return new ResponseEntity<>(bookService.deleteByIsbn(isbn), HttpStatus.OK);
    }

    @PutMapping("/year/{isbn}")
    public ResponseEntity<BookDTO> updateYear(@PathVariable("isbn") String isbn, @Valid @RequestBody BookUpdateYearRequest bookUpdateYearRequest) {
        return new ResponseEntity<>(bookService.updateYear(isbn, bookUpdateYearRequest), HttpStatus.OK);
    }

    @PutMapping("/publisher/{isbn}")
    public ResponseEntity<BookDTO> updatePublisher(@PathVariable("isbn") String isbn, @Valid @RequestBody BookUpdatePublisherRequest bookUpdatePublisherRequest) {
        return new ResponseEntity<>(bookService.updatePublisher(isbn, bookUpdatePublisherRequest), HttpStatus.OK);
    }
}
