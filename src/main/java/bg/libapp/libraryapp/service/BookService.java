package bg.libapp.libraryapp.service;

import bg.libapp.libraryapp.event.SaveBookAuditEvent;
import bg.libapp.libraryapp.event.UpdateActiveStatusBookAuditEvent;
import bg.libapp.libraryapp.event.UpdateDeactivateReasonBookAuditEvent;
import bg.libapp.libraryapp.event.UpdateYearBookAuditEvent;
import bg.libapp.libraryapp.exceptions.*;
import bg.libapp.libraryapp.model.dto.book.*;
import bg.libapp.libraryapp.model.entity.Author;
import bg.libapp.libraryapp.model.entity.Book;
import bg.libapp.libraryapp.model.entity.Genre;
import bg.libapp.libraryapp.model.enums.DeactivateReason;
import bg.libapp.libraryapp.model.mappers.BookMapper;
import bg.libapp.libraryapp.repository.AuthorRepository;
import bg.libapp.libraryapp.repository.BookRepository;
import bg.libapp.libraryapp.repository.GenreRepository;
import bg.libapp.libraryapp.specifications.BookSpecifications;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static bg.libapp.libraryapp.model.constants.ApplicationConstants.*;

@Service
@Transactional
public class BookService {

    private final Logger logger = LoggerFactory.getLogger(BookService.class);

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
        if (bookRepository.existsByIsbn(isbnOfBook)) {
            logger.error("Book with this isbn is already added");
            throw new BookAlreadyAddedException(isbnOfBook);
        }
        Book book = BookMapper.mapToBook(bookAddRequest);
        book.setActive(true);
        book.setGenres(bookAddRequest.getGenres()
                .stream()
                .map(genre -> this.genreRepository.findByName(genre.getName())
                        .orElseThrow(() -> {
                            logger.error("There are no genres with this name '" + genre.getName() + "'");
                            return new GenreNotFoundException(genre.getName());
                        }))
                .collect(Collectors.toSet()));
        book.setAuthors(bookAddRequest.getAuthors()
                .stream()
                .map(authorService::findOrCreate)
                .collect(Collectors.toSet()));
        bookRepository.saveAndFlush(book);
        logger.info("Save a new book with isbn '" + bookAddRequest.getIsbn() + "' and params: " + bookAddRequest);
        eventPublisher.publishEvent(new SaveBookAuditEvent(book, getJsonOfBook(book)));
        return BookMapper.mapToBookExtendedDTO(book);
    }

    public BookDTO deleteByIsbn(String isbn) {
        Book bookToDelete = getBookByIsbnOrThrowException(isbn);
        BookDTO bookToReturn = BookMapper.mapToBookDTO(bookToDelete);
        bookRepository.delete(bookToDelete);
        logger.info("Delete book with isbn '" + isbn + "'");

        return bookToReturn;
    }

    public BookDTO updateYear(String isbn, BookUpdateYearRequest bookUpdateYearRequest) {
        Book bookToEdit = getBookByIsbnOrThrowException(isbn);
        isBookActive(bookToEdit);
        String oldValueYear = String.valueOf(bookToEdit.getYear());
        String newValueYear = String.valueOf(bookUpdateYearRequest.getYear());
        if (!oldValueYear.equals(newValueYear)) {
            bookToEdit.setYear(bookUpdateYearRequest.getYear());
            bookRepository.saveAndFlush(bookToEdit);
            eventPublisher.publishEvent(new UpdateYearBookAuditEvent(bookToEdit, oldValueYear));
            logger.info("Updated year of book with isbn '" + isbn + "' and params: " + bookUpdateYearRequest);
        }
        return BookMapper.mapToBookDTO(bookToEdit);
    }

    private void isBookActive(Book bookToEdit) {
        if (!bookToEdit.isActive()) {
            throw new BookNotActiveException(bookToEdit.getIsbn());
        }
    }

    public BookDTO updatePublisher(String isbn, BookUpdatePublisherRequest bookUpdatePublisherRequest) {
        Book bookToEdit = getBookByIsbnOrThrowException(isbn);
        isBookActive(bookToEdit);
        String oldValuePublisher = bookToEdit.getPublisher();
        String newValuePublisher = bookUpdatePublisherRequest.getPublisher();
        if (!oldValuePublisher.equals(newValuePublisher)) {
            bookToEdit.setPublisher(bookUpdatePublisherRequest.getPublisher());
            bookRepository.saveAndFlush(bookToEdit);
            eventPublisher.publishEvent(new UpdateYearBookAuditEvent(bookToEdit, oldValuePublisher));
            logger.info("Updated publisher of book with isbn '" + isbn + "' and params: " + bookUpdatePublisherRequest);
        }
        return BookMapper.mapToBookDTO(bookToEdit);
    }

    public BookExtendedDTO findBookByIsbn(String isbn) {
        Book book = getBookByIsbnOrThrowException(isbn);
        logger.info("Find book with isbn '" + isbn + "'");
        return BookMapper.mapToBookExtendedDTO(book);
    }

    public Set<BookExtendedDTO> getAllBooksByAuthorFirstAndLastName(String firstName, String lastName) {
        Author author = authorRepository.findAuthorByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> {
                    logger.error("Author with this name '" + firstName + " " + lastName + "' was not found!");
                    return new AuthorNotFoundException(firstName, lastName);
                });
        logger.info("getAllBooksByAuthorFirstAndLastName accessed with author first and lastname: '" + firstName + lastName + "'");
        return bookRepository.findAllByAuthorsContaining(author)
                .stream()
                .map(BookMapper::mapToBookExtendedDTO)
                .collect(Collectors.toSet());
    }

    public Set<BookExtendedDTO> getAllBooksByGenre(String genre) {
        logger.info("getAllBooksByGenre with genre = '" + genre + "' was accessed");
        Genre genreToCheck = genreRepository.findByName(genre)
                .orElseThrow(() -> {
                    logger.error("There are no genres with this name '" + genre + "'");
                    return new GenreNotFoundException(genre);
                });
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

    private static String getBookNotFoundMessage(String isbn) {
        return "Book with this isbn '" + isbn + "' was not found!";
    }

    public Set<BookExtendedDTO> getBooksFiltered(BookFilterRequest bookFilterRequest) {
        logger.info("GetBooksFiltered method called with params: " + bookFilterRequest);
        Specification<Book> specification = getBookSpecifications(bookFilterRequest);
        List<Book> books = specification == null
                ? bookRepository.findAll()
                : bookRepository.findAll(specification);
        return books
                .stream()
                .map(BookMapper::mapToBookExtendedDTO)
                .collect(Collectors.toSet());
    }

    private static Specification<Book> getBookSpecifications(BookFilterRequest bookFilterRequest) {
        Specification<Book> specification = null;
        if (bookFilterRequest.getIsActive() != null) {
            specification = BookSpecifications.isActive(bookFilterRequest.getIsActive()).and(Specification.where(specification));
        }
        if (bookFilterRequest.getTitle() != null) {
            specification = BookSpecifications.fieldLike(TITLE, bookFilterRequest.getTitle()).and(Specification.where(specification));
        }
        if (bookFilterRequest.getPublisher() != null) {
            specification = BookSpecifications.fieldLike(PUBLISHER, bookFilterRequest.getPublisher()).and(Specification.where(specification));
        }
        if (bookFilterRequest.getYearFrom() != null) {
            specification = BookSpecifications.fieldGreaterThanOrEqual(YEAR, bookFilterRequest.getYearFrom()).and(Specification.where(specification));
        }
        if (bookFilterRequest.getYearTo() != null) {
            specification = BookSpecifications.fieldLowerThanOrEqual(YEAR, bookFilterRequest.getYearTo()).and(Specification.where(specification));
        }
        if (bookFilterRequest.getGenres() != null) {
            for (Integer genre : bookFilterRequest.getGenres()) {
                specification = BookSpecifications.genreEquals(genre).and(Specification.where(specification));
            }
        }
        if (bookFilterRequest.getAuthorsFirstName() != null) {
            for (String authorFirstName : bookFilterRequest.getAuthorsFirstName()) {
                specification = BookSpecifications.authorNameEquals(FIRST_NAME, authorFirstName).and(Specification.where(specification));
            }
        }
        if (bookFilterRequest.getAuthorsLastName() != null) {
            for (String authorLastName : bookFilterRequest.getAuthorsLastName()) {
                specification = BookSpecifications.authorNameEquals(LAST_NAME, authorLastName).and(Specification.where(specification));
            }
        }
        return specification;
    }

    public BookDTO changeStatus(String isbn, BookChangeStatusRequest bookChangeStatusRequest) {
        logger.info("changeStatus of Book method called with params: " + bookChangeStatusRequest);

        Book book = getBookByIsbnOrThrowException(isbn);
        boolean statusToSet = bookChangeStatusRequest.isActive();
        if (statusToSet) {
            activateBook(book);
        } else {
            deactivateBook(book, bookChangeStatusRequest.getDeactivateReason());
        }
        return BookMapper.mapToBookDTO(book);
    }

    private void deactivateBook(Book book, String deactivateReason) {
        if (isValidDeactivateReason(deactivateReason)) {
            String oldStatus = String.valueOf(book.isActive());
            String oldReason = book.getDeactivateReason();
            book.setActive(false);
            book.setDeactivateReason(deactivateReason);
            eventPublisher.publishEvent(new UpdateActiveStatusBookAuditEvent(book, oldStatus));
            eventPublisher.publishEvent(new UpdateDeactivateReasonBookAuditEvent(book, oldReason));
            logger.info("Deactivated book with isbn '" + book.getIsbn() + "' and params: " + book);
        } else {
            logger.error("There is no such a deactivate reason '" + deactivateReason + "'");
            throw new NoSuchDeactivateReasonException(deactivateReason);
        }
    }

    private void activateBook(Book book) {
        if (!book.isActive()) {
            String oldStatus = String.valueOf(book.isActive());
            String oldReason = book.getDeactivateReason();
            book.setActive(true);
            book.setDeactivateReason(null);
            eventPublisher.publishEvent(new UpdateActiveStatusBookAuditEvent(book, oldStatus));
            eventPublisher.publishEvent(new UpdateDeactivateReasonBookAuditEvent(book, oldReason));
            logger.info("Activated book with isbn '" + book.getIsbn() + "' and params: " + book);
        }
    }

    private static boolean isValidDeactivateReason(String deactivateReason) {
        return Arrays.stream(DeactivateReason.values()).map(String::valueOf)
                .collect(Collectors.toSet()).contains(deactivateReason);
    }

    private Book getBookByIsbnOrThrowException(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> {
                    logger.error(getBookNotFoundMessage(isbn));
                    return new BookNotFoundException(isbn);
                });
    }
}
