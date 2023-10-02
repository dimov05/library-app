package bg.libapp.libraryapp.web;

import bg.libapp.libraryapp.model.dto.book.*;
import bg.libapp.libraryapp.model.dto.rent.RentDTO;
import bg.libapp.libraryapp.service.BookService;
import bg.libapp.libraryapp.service.RentService;
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
    public ResponseEntity<BookDTO> addBook(@Valid @RequestBody(required = false) BookAddRequest bookAddRequest) {
        return new ResponseEntity<>(this.bookService.saveNewBook(bookAddRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookExtendedDTO> getBookByIsbn(@PathVariable("isbn") String isbn) {
        return new ResponseEntity<>(this.bookService.findBookExtendedDTOByIsbn(isbn), HttpStatus.OK);
    }


    @GetMapping("")
    public ResponseEntity<Set<BookExtendedDTO>> getAllBooks(BookFilterRequest bookFilterRequest) {
        return new ResponseEntity<>(bookService.getAllBooks(bookFilterRequest), HttpStatus.OK);
    }

    @GetMapping("/author/{firstName},{lastName}")
    public ResponseEntity<?> getAllBooksByAuthor(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
        return new ResponseEntity<>(bookService.getAllBooksByAuthorFirstAndLastName(firstName, lastName), HttpStatus.OK);
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<Set<BookExtendedDTO>> getAllBooksByGenre(@PathVariable("genre") String genre) {
        return new ResponseEntity<>(bookService.getAllBooksByGenre(genre), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{isbn}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<BookDTO> deleteBookById(@PathVariable("isbn") String isbn) {
        return new ResponseEntity<>(bookService.deleteByIsbn(isbn), HttpStatus.OK);
    }

    @PutMapping("/year/{isbn}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<BookDTO> updateYear(@PathVariable("isbn") String isbn, @Valid @RequestBody BookUpdateYearRequest bookUpdateYearRequest) {
        return new ResponseEntity<>(bookService.updateYear(isbn, bookUpdateYearRequest), HttpStatus.OK);
    }

    @PutMapping("/total-quantity/{isbn}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<BookDTO> updateTotalQuantity(@PathVariable("isbn") String isbn, @Valid @RequestBody BookUpdateTotalQuantityRequest bookUpdateTotalQuantityRequest) {
        return new ResponseEntity<>(bookService.updateTotalQuantity(isbn, bookUpdateTotalQuantityRequest), HttpStatus.OK);
    }

    @PutMapping("/publisher/{isbn}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<BookDTO> updatePublisher(@PathVariable("isbn") String isbn, @Valid @RequestBody BookUpdatePublisherRequest bookUpdatePublisherRequest) {
        return new ResponseEntity<>(bookService.updatePublisher(isbn, bookUpdatePublisherRequest), HttpStatus.OK);
    }

    @PutMapping("/change-status/{isbn}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<BookDTO> changeBookStatus(@PathVariable("isbn") String isbn, @Valid BookChangeStatusRequest bookChangeStatusRequest) {
        return new ResponseEntity<>(bookService.changeStatus(isbn, bookChangeStatusRequest), HttpStatus.OK);
    }
}
