package bg.libapp.libraryapp.web;

import bg.libapp.libraryapp.model.dto.book.BookAddRequest;
import bg.libapp.libraryapp.model.dto.book.BookExtendedDTO;
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
        this.bookService.saveNewBook(bookAddRequest);
        BookExtendedDTO book = this.bookService.findBookByIsbn(bookAddRequest.getIsbn());

        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @GetMapping("/{isbn}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER','ROLE_MODERATOR')")
    public ResponseEntity<?> getBookByIsbn(@PathVariable("isbn") String isbn) {
        BookExtendedDTO bookExtendedDTO = this.bookService.findBookByIsbn(isbn);
        return new ResponseEntity<>(bookExtendedDTO, HttpStatus.OK);
    }

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER','ROLE_MODERATOR')")
    public ResponseEntity<Set<BookExtendedDTO>> getAllBooks() {
        Set<BookExtendedDTO> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/author/{firstName},{lastName}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER','ROLE_MODERATOR')")
    public ResponseEntity<?> getAllBooksByAuthor(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
        Set<BookExtendedDTO> books = bookService.getAllBooksByAuthorFirstAndLastName(firstName, lastName);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    //get all books by genre
    @GetMapping("/genre/{genre}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER','ROLE_MODERATOR')")
    public ResponseEntity<?> getAllBooksByGenre(@PathVariable("genre") String genre) {
        Set<BookExtendedDTO> books = bookService.getAllBooksByGenre(genre);
        return new ResponseEntity<>(books, HttpStatus.OK);

    }
}
