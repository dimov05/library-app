package bg.libapp.libraryapp.web;

import bg.libapp.libraryapp.exceptions.AuthorNotFoundException;
import bg.libapp.libraryapp.exceptions.BookNotFoundException;
import bg.libapp.libraryapp.exceptions.GenreNotFoundException;
import bg.libapp.libraryapp.model.dto.book.BookAddDTO;
import bg.libapp.libraryapp.model.dto.book.BookViewDTO;
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

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    public ResponseEntity<?> addBook(@Valid @RequestBody BookAddDTO bookAddDTO) {
        this.bookService.saveNewBook(bookAddDTO);
        BookViewDTO book = this.bookService.findBookByIsbn(bookAddDTO.getIsbn());

        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @GetMapping("/{isbn}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER','ROLE_MODERATOR')")
    public ResponseEntity<?> getBookByIsbn(@PathVariable("isbn") String isbn) {
        try {
            BookViewDTO bookViewDTO = this.bookService.findBookByIsbn(isbn);
            return new ResponseEntity<>(bookViewDTO, HttpStatus.OK);
        } catch (BookNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER','ROLE_MODERATOR')")
    public ResponseEntity<Set<BookViewDTO>> getAllBooks() {
        Set<BookViewDTO> books = this.bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/author/{firstName},{lastName}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER','ROLE_MODERATOR')")
    public ResponseEntity<?> getAllBooksByAuthor(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
        try {
            Set<BookViewDTO> books = this.bookService.getAllBooksByAuthorFirstAndLastName(firstName, lastName);
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (AuthorNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //get all books by genre
    @GetMapping("/genre/{genre}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER','ROLE_MODERATOR')")
    public ResponseEntity<?> getAllBooksByGenre(@PathVariable("genre") String genre) {
        try {
            Set<BookViewDTO> books = this.bookService.getAllBooksByGenre(genre);
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (GenreNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    //delete book or deactivate book???
//    @DeleteMapping("/delete/{isbn}")
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MODERATOR')")
//    public ResponseEntity<?> deleteBookByIsbn(@PathVariable("isbn") String isbn) {
//        try {
//            BookViewDTO deletedBook = this.bookService.deleteBookByIsbn(isbn);
//            return new ResponseEntity<>(deletedBook, HttpStatus.OK);
//        } catch (BookNotFoundException ex) {
//            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }


}
