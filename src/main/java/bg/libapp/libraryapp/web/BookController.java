package bg.libapp.libraryapp.web;

import bg.libapp.libraryapp.model.dto.book.BookAddRequest;
import bg.libapp.libraryapp.model.dto.book.BookChangeStatusRequest;
import bg.libapp.libraryapp.model.dto.book.BookDTO;
import bg.libapp.libraryapp.model.dto.book.BookExtendedDTO;
import bg.libapp.libraryapp.model.dto.book.BookFilterRequest;
import bg.libapp.libraryapp.model.dto.book.BookUpdatePublisherRequest;
import bg.libapp.libraryapp.model.dto.book.BookUpdateTotalQuantityRequest;
import bg.libapp.libraryapp.model.dto.book.BookUpdateYearRequest;
import bg.libapp.libraryapp.service.BookService;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.ISBN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/books")
@Validated
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
    public ResponseEntity<BookExtendedDTO> getBookByIsbn(@PathVariable("isbn") @ISBN(type = ISBN.Type.ANY, message = "Invalid isbn") String isbn) {
        return new ResponseEntity<>(this.bookService.findBookExtendedDTOByIsbn(isbn), HttpStatus.OK);
    }


    @GetMapping("")
    public ResponseEntity<Set<BookExtendedDTO>> getAllBooks(@Valid BookFilterRequest bookFilterRequest) {
        return new ResponseEntity<>(bookService.getAllBooks(bookFilterRequest), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{isbn}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<BookDTO> deleteBookById(@PathVariable("isbn") @ISBN(type = ISBN.Type.ANY, message = "Invalid isbn") String isbn) {
        return new ResponseEntity<>(bookService.deleteByIsbn(isbn), HttpStatus.OK);
    }

    @PutMapping("/year/{isbn}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<BookDTO> updateYear(@PathVariable("isbn") @ISBN(type = ISBN.Type.ANY, message = "Invalid isbn") String isbn, @Valid @RequestBody BookUpdateYearRequest bookUpdateYearRequest) {
        return new ResponseEntity<>(bookService.updateYear(isbn, bookUpdateYearRequest), HttpStatus.OK);
    }

    @PutMapping("/total-quantity/{isbn}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<BookDTO> updateTotalQuantity(@PathVariable("isbn") @ISBN(type = ISBN.Type.ANY, message = "Invalid isbn") String isbn, @Valid @RequestBody BookUpdateTotalQuantityRequest bookUpdateTotalQuantityRequest) {
        return new ResponseEntity<>(bookService.updateTotalQuantity(isbn, bookUpdateTotalQuantityRequest), HttpStatus.OK);
    }

    @PutMapping("/publisher/{isbn}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<BookDTO> updatePublisher(@PathVariable("isbn") @ISBN(type = ISBN.Type.ANY, message = "Invalid isbn") String isbn, @Valid @RequestBody BookUpdatePublisherRequest bookUpdatePublisherRequest) {
        return new ResponseEntity<>(bookService.updatePublisher(isbn, bookUpdatePublisherRequest), HttpStatus.OK);
    }

    @PutMapping("/change-status/{isbn}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<BookDTO> changeBookStatus(@PathVariable("isbn") @ISBN(type = ISBN.Type.ANY, message = "Invalid isbn") String isbn, @Valid @RequestBody BookChangeStatusRequest bookChangeStatusRequest) {
        return new ResponseEntity<>(bookService.changeStatus(isbn, bookChangeStatusRequest), HttpStatus.OK);
    }
}
