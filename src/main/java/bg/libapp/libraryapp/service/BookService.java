package bg.libapp.libraryapp.service;

import bg.libapp.libraryapp.event.SaveBookAuditEvent;
import bg.libapp.libraryapp.event.UpdatePublisherBookAuditEvent;
import bg.libapp.libraryapp.event.UpdateYearBookAuditEvent;
import bg.libapp.libraryapp.exceptions.*;
import bg.libapp.libraryapp.model.dto.book.*;
import bg.libapp.libraryapp.model.entity.Author;
import bg.libapp.libraryapp.model.entity.Book;
import bg.libapp.libraryapp.model.entity.Genre;
import bg.libapp.libraryapp.model.mappers.BookMapper;
import bg.libapp.libraryapp.repository.AuthorRepository;
import bg.libapp.libraryapp.repository.BookRepository;
import bg.libapp.libraryapp.repository.GenreRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final ObjectMapper mapper;

    private final AuthorService authorService;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository, ApplicationEventPublisher eventPublisher, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.eventPublisher = eventPublisher;
        this.authorService = authorService;
        this.mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    public BookExtendedDTO saveNewBook(BookAddRequest bookAddRequest) {
        String isbnOfBook = bookAddRequest.getIsbn();
        Book book = bookRepository.findByIsbn(isbnOfBook);
        if (book != null) {
            throw new BookAlreadyAddedException(isbnOfBook);
        }
        book = BookMapper.mapToBook(bookAddRequest);
        book.setGenres(bookAddRequest.getGenres()
                .stream()
                .map(genre -> this.genreRepository.findByName(genre.getName()))
                .collect(Collectors.toSet()));
        book.setAuthors(bookAddRequest.getAuthors()
                .stream()
                .map(authorService::findOrCreate)
                .collect(Collectors.toSet()));
        bookRepository.saveAndFlush(book);
        eventPublisher.publishEvent(new SaveBookAuditEvent(book, getJsonOfBook(book)));
        return BookMapper.mapToBookExtendedDTO(book);
    }

    public BookDTO deleteById(String isbn) {
        Book bookToDelete = bookRepository.findByIsbn(isbn);
        if (bookToDelete == null) {
            throw new BookNotFoundException(isbn);
        }
        BookDTO bookToReturn = BookMapper.mapToBookDTO(bookToDelete);
        bookRepository.delete(bookToDelete);
        return bookToReturn;
    }

    public BookDTO updateYear(String isbn, BookUpdateYearRequest bookUpdateYearRequest) {
        Book bookToEdit = bookRepository.findByIsbn(isbn);
        if (bookToEdit == null) {
            throw new BookNotFoundException(isbn);
        }
        String oldValueYear = String.valueOf(bookToEdit.getYear());
        String newValueYear = String.valueOf(bookUpdateYearRequest.getYear());
        if (!oldValueYear.equals(newValueYear)) {
            bookToEdit.setYear(bookUpdateYearRequest.getYear());
            bookRepository.saveAndFlush(bookToEdit);
            eventPublisher.publishEvent(new UpdateYearBookAuditEvent(bookToEdit, oldValueYear));
        }
        return BookMapper.mapToBookDTO(bookToEdit);
    }

    public BookDTO updatePublisher(String isbn, BookUpdatePublisherRequest bookUpdatePublisherRequest) {
        Book bookToEdit = bookRepository.findByIsbn(isbn);
        if (bookToEdit == null) {
            throw new BookNotFoundException(isbn);
        }
        String oldValuePublisher = bookToEdit.getPublisher();
        String newValuePublisher = bookUpdatePublisherRequest.getPublisher();
        if (!oldValuePublisher.equals(newValuePublisher)) {
            bookToEdit.setPublisher(bookUpdatePublisherRequest.getPublisher());
            bookRepository.saveAndFlush(bookToEdit);
            eventPublisher.publishEvent(new UpdatePublisherBookAuditEvent(bookToEdit, oldValuePublisher));
        }
        return BookMapper.mapToBookDTO(bookToEdit);
    }

    public BookExtendedDTO findBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn);
        if (book == null) {
            throw new BookNotFoundException(isbn);
        }
        return BookMapper.mapToBookExtendedDTO(book);
    }

    public Set<BookExtendedDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookMapper::mapToBookExtendedDTO)
                .collect(Collectors.toSet());
    }

    public Set<BookExtendedDTO> getAllBooksByAuthorFirstAndLastName(String firstName, String lastName) {
        Author author = authorRepository.findAuthorByFirstNameAndLastName(firstName, lastName);
        if (author == null) {
            throw new AuthorNotFoundException(firstName, lastName);
        }
        return bookRepository.findAllByAuthorsContaining(author)
                .stream()
                .map(BookMapper::mapToBookExtendedDTO)
                .collect(Collectors.toSet());
    }

    public Set<BookExtendedDTO> getAllBooksByGenre(String genre) {
        Genre genreToCheck = genreRepository.findByName(genre);
        if (genreToCheck == null) {
            throw new GenreNotFoundException(genre);
        }
        return bookRepository.findBookByGenresContaining(genreToCheck)
                .stream()
                .map(BookMapper::mapToBookExtendedDTO)
                .collect(Collectors.toSet());
    }


    private String getJsonOfBook(Book book) {
        String json;
        try {
            json = mapper.writeValueAsString(BookMapper.mapToBookExtendedDTO(book));
        } catch (JsonProcessingException e) {
            throw new CannotProcessJsonOfEntity(book);
        }
        return json;
    }
}
