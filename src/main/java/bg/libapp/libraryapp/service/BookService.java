package bg.libapp.libraryapp.service;

import bg.libapp.libraryapp.event.BookImportAuditEvent;
import bg.libapp.libraryapp.event.SaveBookAuditEvent;
import bg.libapp.libraryapp.event.UpdateActiveStatusBookAuditEvent;
import bg.libapp.libraryapp.event.UpdateDeactivateReasonBookAuditEvent;
import bg.libapp.libraryapp.event.UpdatePublisherBookAuditEvent;
import bg.libapp.libraryapp.event.UpdateQuantityBookAuditEvent;
import bg.libapp.libraryapp.event.UpdateYearBookAuditEvent;
import bg.libapp.libraryapp.exceptions.CannotProcessJsonOfEntityException;
import bg.libapp.libraryapp.exceptions.MovingFileException;
import bg.libapp.libraryapp.exceptions.book.BookAlreadyAddedException;
import bg.libapp.libraryapp.exceptions.book.BookIsActiveOnDeleteException;
import bg.libapp.libraryapp.exceptions.book.BookNotActiveException;
import bg.libapp.libraryapp.exceptions.book.BookNotFoundException;
import bg.libapp.libraryapp.exceptions.book.NoSuchDeactivateReasonException;
import bg.libapp.libraryapp.exceptions.genre.GenreNotFoundException;
import bg.libapp.libraryapp.exceptions.rent.InsufficientTotalQuantityException;
import bg.libapp.libraryapp.model.dto.author.AuthorRequest;
import bg.libapp.libraryapp.model.dto.book.BookAddRequest;
import bg.libapp.libraryapp.model.dto.book.BookChangeStatusRequest;
import bg.libapp.libraryapp.model.dto.book.BookDTO;
import bg.libapp.libraryapp.model.dto.book.BookExtendedDTO;
import bg.libapp.libraryapp.model.dto.book.BookFilterRequest;
import bg.libapp.libraryapp.model.dto.book.BookUpdatePublisherRequest;
import bg.libapp.libraryapp.model.dto.book.BookUpdateTotalQuantityRequest;
import bg.libapp.libraryapp.model.dto.book.BookUpdateYearRequest;
import bg.libapp.libraryapp.model.dto.genre.GenreRequest;
import bg.libapp.libraryapp.model.entity.Book;
import bg.libapp.libraryapp.model.entity.Genre;
import bg.libapp.libraryapp.model.enums.DeactivateReason;
import bg.libapp.libraryapp.model.mappers.BookMapper;
import bg.libapp.libraryapp.repository.BookRepository;
import bg.libapp.libraryapp.repository.GenreRepository;
import bg.libapp.libraryapp.specifications.BookSpecifications;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static bg.libapp.libraryapp.model.constants.ApplicationConstants.*;

@Service
@Transactional
public class BookService {
    private final Logger logger = LoggerFactory.getLogger(BookService.class);
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final ObjectMapper objectMapper;
    private final AuthorService authorService;
    private final XmlMapper xmlMapper;

    @Autowired
    public BookService(BookRepository bookRepository, GenreRepository genreRepository, ApplicationEventPublisher eventPublisher, ObjectMapper objectMapper, AuthorService authorService, XmlMapper xmlMapper) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.eventPublisher = eventPublisher;
        this.objectMapper = objectMapper;
        this.authorService = authorService;
        this.xmlMapper = xmlMapper;
    }

    public BookDTO saveNewBook(BookAddRequest bookAddRequest) {
        checkIfBookWithThisIsbnIsAlreadyAdded(bookAddRequest);
        Set<Genre> genres = getGenresFromRequest(bookAddRequest.getGenres());
        Book book = BookMapper.mapToBook(bookAddRequest);
        book.setActive(true);
        book.setAvailableQuantity(book.getTotalQuantity());
        book.setGenres(genres);
        book.setAuthors(bookAddRequest.getAuthors()
                .stream()
                .map(authorService::findOrCreate)
                .collect(Collectors.toSet()));
        bookRepository.saveAndFlush(book);
        logger.info(SAVE_NEW_BOOK_WITH_ISBN, bookAddRequest.getIsbn(), bookAddRequest);
        eventPublisher.publishEvent(new SaveBookAuditEvent(book, getJsonOfBook(book)));
        return BookMapper.mapToBookDTO(book);
    }

    private Set<Genre> getGenresFromRequest(Set<GenreRequest> genres) {
        return genres.stream().map(genre -> this.genreRepository.findByName(genre.getName())
                        .orElseThrow(() -> {
                            logger.error(NO_GENRES_WITH_THIS_NAME, genre.getName());
                            return new GenreNotFoundException(genre.getName());
                        }))
                .collect(Collectors.toSet());
    }

    private void checkIfBookWithThisIsbnIsAlreadyAdded(BookAddRequest bookAddRequest) {
        String isbnOfBook = bookAddRequest.getIsbn();
        if (bookRepository.existsByIsbn(isbnOfBook)) {
            logger.error(String.format(BOOK_WITH_ISBN_ALREADY_EXISTS, bookAddRequest.getIsbn()));
            throw new BookAlreadyAddedException(isbnOfBook);
        }
    }

    public BookDTO deleteByIsbn(String isbn) {
        Book bookToDelete = getBookByIsbnOrThrowException(isbn);
        isBookInActive(bookToDelete);
        BookDTO bookToReturn = BookMapper.mapToBookDTO(bookToDelete);
        bookRepository.delete(bookToDelete);
        logger.info(DELETE_BOOK_WITH_ISBN, isbn);
        return bookToReturn;
    }

    public BookDTO updateYear(String isbn, BookUpdateYearRequest bookUpdateYearRequest) {
        Book bookToEdit = getBookByIsbnOrThrowException(isbn);
        String oldValueYear = String.valueOf(bookToEdit.getYear());
        String newValueYear = String.valueOf(bookUpdateYearRequest.getYear());
        if (!oldValueYear.equals(newValueYear)) {
            bookToEdit.setYear(bookUpdateYearRequest.getYear());
            bookRepository.saveAndFlush(bookToEdit);
            eventPublisher.publishEvent(new UpdateYearBookAuditEvent(bookToEdit, oldValueYear));
            logger.info(UPDATED_YEAR_OF_BOOK_WITH_ISBN, isbn, bookUpdateYearRequest);
        }
        return BookMapper.mapToBookDTO(bookToEdit);
    }

    private void isBookActive(Book bookToEdit) {
        if (!bookToEdit.isActive()) {
            logger.error(BOOK_WITH_ISBN_IS_NOT_ACTIVE, bookToEdit.getIsbn());
            throw new BookNotActiveException(bookToEdit.getIsbn());
        }
    }

    private void isBookInActive(Book bookToEdit) {
        if (bookToEdit.isActive()) {
            logger.error(BOOK_WITH_ISBN_IS_ACTIVE_AND_CAN_NOT_BE_DELETED, bookToEdit.getIsbn());
            throw new BookIsActiveOnDeleteException(bookToEdit.getIsbn());
        }
    }

    public BookDTO updatePublisher(String isbn, BookUpdatePublisherRequest bookUpdatePublisherRequest) {
        Book bookToEdit = getBookByIsbnOrThrowException(isbn);
        String oldValuePublisher = bookToEdit.getPublisher();
        String newValuePublisher = bookUpdatePublisherRequest.getPublisher();
        if (!oldValuePublisher.equals(newValuePublisher)) {
            bookToEdit.setPublisher(bookUpdatePublisherRequest.getPublisher());
            bookRepository.saveAndFlush(bookToEdit);
            eventPublisher.publishEvent(new UpdatePublisherBookAuditEvent(bookToEdit, oldValuePublisher));
            logger.info(UPDATED_PUBLISHER_OF_BOOK_WITH_ISBN, isbn, bookUpdatePublisherRequest);
        }
        return BookMapper.mapToBookDTO(bookToEdit);
    }

    public BookExtendedDTO findBookExtendedDTOByIsbn(String isbn) {
        Book book = getBookByIsbnOrThrowException(isbn);
        isBookActive(book);
        logger.info(FIND_BOOK_WITH_ISBN, isbn);
        return BookMapper.mapToBookExtendedDTO(book);
    }

    public Book findBookByIsbn(String isbn) {
        Book book = getBookByIsbnOrThrowException(isbn);
        isBookActive(book);
        logger.info(FIND_BOOK_WITH_ISBN, isbn);
        return book;
    }

    private String getJsonOfBook(Book book) {
        String json;
        try {
            json = objectMapper.writeValueAsString(BookMapper.mapToBookDTO(book));
        } catch (JsonProcessingException e) {
            logger.error(CAN_NOT_GET_JSON_FORMAT_OF_BOOK_ENTITY);
            throw new CannotProcessJsonOfEntityException(book);
        }
        logger.info(GET_JSON_FORMAT_OF_BOOK_ENTITY);
        return json;
    }

    public Set<BookExtendedDTO> getAllBooks(BookFilterRequest bookFilterRequest) {
        logger.info(GET_ALL_BOOKS_ACCESSED_LOGGER, bookFilterRequest);

        List<Book> books = bookFilterRequest == null
                ? bookRepository.findAll()
                : bookRepository.findAll(getBookSpecifications(bookFilterRequest));
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
        if (bookFilterRequest.getAvailableQuantity() != null) {
            String[] comparisons = bookFilterRequest.getAvailableQuantity().split(",");
            specification = getFieldCompareSpecification(specification, comparisons, AVAILABLE_QUANTITY);
        }
        if (bookFilterRequest.getYear() != null) {
            String[] comparisons = bookFilterRequest.getYear().split(",");
            specification = getFieldCompareSpecification(specification, comparisons, YEAR);
        }
        if (bookFilterRequest.getTotalQuantity() != null) {
            String[] comparisons = bookFilterRequest.getTotalQuantity().split(",");
            specification = getFieldCompareSpecification(specification, comparisons, TOTAL_QUANTITY);
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

    private static Specification<Book> getFieldCompareSpecification(Specification<Book> specification, String[] comparisons, String totalQuantity) {
        for (String comparison : comparisons) {
            String[] tokens = comparison.split(":");
            String operation = tokens[0];
            int value = Integer.parseInt(tokens[1]);
            specification = BookSpecifications.fieldCompareValue(totalQuantity, value, operation).and(specification);
        }
        return specification;
    }

    @Transactional
    public BookDTO changeStatus(String isbn, BookChangeStatusRequest bookChangeStatusRequest) {
        logger.info(CHANGE_STATUS_OF_BOOK_METHOD_LOGGER, bookChangeStatusRequest);

        Book book = getBookByIsbnOrThrowException(isbn);
        boolean newStatus = bookChangeStatusRequest.getIsActive();
        boolean oldStatus = book.isActive();
        String newReason = bookChangeStatusRequest.getDeactivateReason();
        String oldReason = book.getDeactivateReason();
        String oldStatusString = String.valueOf(oldStatus);

        if (newStatus && !oldStatus) {
            book.setActive(true);
            book.setDeactivateReason(null);
            eventPublisher.publishEvent(new UpdateActiveStatusBookAuditEvent(book, oldStatusString));
            eventPublisher.publishEvent(new UpdateDeactivateReasonBookAuditEvent(book, oldReason));
            logger.info(ACTIVATED_BOOK_WITH_ISBN, book.getIsbn(), book);
        } else if (!newStatus) {
            isValidDeactivateReason(newReason.toUpperCase());
            if (oldStatus) {
                book.setActive(false);
                eventPublisher.publishEvent(new UpdateActiveStatusBookAuditEvent(book, oldStatusString));
            }
            if (!StringUtils.equals(oldReason, newReason)) {
                book.setDeactivateReason(newReason);
                eventPublisher.publishEvent(new UpdateDeactivateReasonBookAuditEvent(book, oldReason));
            }
            logger.info(DEACTIVATED_BOOK_WITH_ISBN, book.getIsbn(), book);
        }
        return BookMapper.mapToBookDTO(book);
    }

    private void isValidDeactivateReason(String deactivateReason) {
        boolean isValid = EnumUtils.isValidEnum(DeactivateReason.class, deactivateReason);
        if (!isValid) {
            logger.error(THERE_IS_NO_SUCH_DEACTIVATE_REASON, deactivateReason);
            throw new NoSuchDeactivateReasonException(deactivateReason);
        }
    }

    private Book getBookByIsbnOrThrowException(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> {
                    logger.error(BOOK_WITH_ISBN_NOT_FOUND, isbn);
                    return new BookNotFoundException(isbn);
                });
    }

    public BookDTO updateTotalQuantity(String isbn, BookUpdateTotalQuantityRequest bookUpdateTotalQuantityRequest) {
        logger.info(UPDATE_TOTAL_QUANTITY_METHOD_ACCESSED, isbn, bookUpdateTotalQuantityRequest);
        Book bookToEdit = getBookByIsbnOrThrowException(isbn);
        int oldTotalQuantity = bookToEdit.getTotalQuantity();
        int oldAvailableQuantity = bookToEdit.getAvailableQuantity();
        int newTotalQuantity = bookUpdateTotalQuantityRequest.getTotalQuantity();

        if (oldTotalQuantity != newTotalQuantity) {
            checkIfNewTotalQuantityIsLessThanRentedQuantity(newTotalQuantity, bookToEdit);
            int difference = Math.abs(newTotalQuantity - oldTotalQuantity);
            int newAvailableQuantity = getBookNewAvailableQuantity(oldTotalQuantity, newTotalQuantity, bookToEdit, difference);
            bookToEdit.setTotalQuantity(newTotalQuantity);
            bookToEdit.setAvailableQuantity(newAvailableQuantity);

            bookRepository.saveAndFlush(bookToEdit);
            eventPublisher.publishEvent(new UpdateQuantityBookAuditEvent(bookToEdit, oldTotalQuantity, TOTAL_QUANTITY));
            eventPublisher.publishEvent(new UpdateQuantityBookAuditEvent(bookToEdit, oldAvailableQuantity, AVAILABLE_QUANTITY));
            logger.info(UPDATE_TOTAL_QUANTITY_OF_BOOK_WITH_ISBN, isbn, bookUpdateTotalQuantityRequest);
        }
        return BookMapper.mapToBookDTO(bookToEdit);
    }

    private int getBookNewAvailableQuantity(int oldTotalQuantity, int newTotalQuantity, Book bookToEdit, int difference) {
        return oldTotalQuantity > newTotalQuantity
                ? bookToEdit.getAvailableQuantity() - difference
                : bookToEdit.getAvailableQuantity() + difference;

    }

    private void checkIfNewTotalQuantityIsLessThanRentedQuantity(int newValueTotalQuantity, Book bookToEdit) {
        int rentedQuantity = bookToEdit.getTotalQuantity() - bookToEdit.getAvailableQuantity();
        if (newValueTotalQuantity < rentedQuantity) {
            throw new InsufficientTotalQuantityException(bookToEdit.getIsbn(), newValueTotalQuantity);
        }
    }

    public String importXmlBooks() {
        int importedBooks = 0;

        List<Resource> zipFiles = getAllZipFilesToImport();
        for (Resource zipFile : zipFiles) {
            List<BookImportAuditEvent> failEvents = new ArrayList<>();
            List<BookAddRequest> booksToAdd = new ArrayList<>();

            try (ZipInputStream zipInputStream = new ZipInputStream(zipFile.getInputStream(), StandardCharsets.UTF_8)) {
                ZipEntry zipEntry = zipInputStream.getNextEntry();
                while (zipEntry != null) {
                    if (!zipEntry.isDirectory() && zipEntry.getName().endsWith(".xml")) {
                        String xmlContent = new BufferedReader(new InputStreamReader(zipInputStream)).lines().collect(Collectors.joining("\n"));
                        BookAddRequest bookAddRequest = xmlMapper.readValue(xmlContent, BookAddRequest.class);
                        addBookOnSuccessValidation(zipFile, bookAddRequest, booksToAdd, failEvents, zipEntry);
                    }
                    zipEntry = zipInputStream.getNextEntry();
                }
                if (failEvents.isEmpty()) {
                    importedBooks += booksToAdd.size();
                    booksToAdd.forEach(this::saveNewBook);
                    eventPublisher.publishEvent(new BookImportAuditEvent(true, zipFile.getFilename(), "All files", String.format("Uploaded %d books", booksToAdd.size())));
                    moveZipFileOnSuccess(zipFile);
                } else {
                    failEvents.forEach(eventPublisher::publishEvent);
                }
            } catch (IOException e) {
                logger.info(ERROR_READING_ZIP_FILE, e.getMessage());
                eventPublisher.publishEvent(new BookImportAuditEvent(false, zipFile.getFilename(), null, e.getMessage()));
            }
        }

        return String.format("Successfully imported %d books", importedBooks);
    }

    private void addBookOnSuccessValidation(Resource zipFile, BookAddRequest bookAddRequest, List<BookAddRequest> booksToAdd, List<BookImportAuditEvent> failEvents, ZipEntry zipEntry) {
        List<String> validationsErrors = validateBookAddRequest(bookAddRequest);
        if (validationsErrors.isEmpty()) {
            booksToAdd.add(bookAddRequest);
        } else {
            String errorMessage = String.join(", ", validationsErrors);
            failEvents.add(new BookImportAuditEvent(false, zipFile.getFilename(), zipEntry.getName(), errorMessage));
        }
    }

    private List<String> validateBookAddRequest(BookAddRequest bookAddRequest) {
        List<String> errors = new ArrayList<>();
        String regex10 = REGEX_FOR_10_SYMBOLS_ISBN;
        String regex13 = REGEX_FOR_13_SYMBOLS_ISBN;
        Pattern pattern10 = Pattern.compile(regex10);
        Pattern pattern13 = Pattern.compile(regex13);
        if (!pattern10.matcher(regex10).matches() && pattern13.matcher(regex13).matches()) {
            errors.add(String.format(THIS_ISBN_IS_NOT_VALID, bookAddRequest.getIsbn()));
        }
        if (bookAddRequest.getTitle().isBlank() || bookAddRequest.getTitle().length() > 150) {
            errors.add(TITLE_LENGTH_VALIDATION);
        }
        if (bookAddRequest.getYear() < 1000 || bookAddRequest.getYear() > 2100) {
            errors.add(YEAR_RANGE_EXCEPTION);
        }
        if (bookAddRequest.getPublisher().isBlank() || bookAddRequest.getPublisher().length() > 100) {
            errors.add(PUBLISHER_SIZE_EXCEPTION);
        }
        if (bookAddRequest.getTotalQuantity() < 0) {
            errors.add(BOOK_QUANTITY_LESS_THAN_0);
        }
        if (bookRepository.existsByIsbn(bookAddRequest.getIsbn())) {
            errors.add(String.format(BOOK_WITH_ISBN_ALREADY_EXISTS, bookAddRequest.getIsbn()));
        }

        for (GenreRequest genre : bookAddRequest.getGenres()) {
            if (genre.getName().isBlank() || genre.getName().length() < 2 || genreRepository.findByName(genre.getName()).isEmpty()) {
                errors.add(String.format(THERE_IS_NO_GENRE_WITH_THIS_NAME, genre.getName()));
            }
        }
        for (AuthorRequest author : bookAddRequest.getAuthors()) {
            if (author.getFirstName().isEmpty() || author.getFirstName().length() < 2) {
                errors.add(AUTHOR_FIRST_NAME_LENGTH_VALIDATION);
            }
            if (author.getLastName().isEmpty() || author.getLastName().length() < 2) {
                errors.add(String.format(AUTHOR_LAST_NAME_LENGTH_VALIDATION));
            }
        }

        return errors;
    }

    private static void moveZipFileOnSuccess(Resource zipFile) {
        try {
            Path sourcePath = Paths.get(zipFile.getURI());
            Path targetPath = Paths.get(BOOKS_IMPORTED_FOLDER_PATH, sourcePath.getFileName().toString());
            Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new MovingFileException(zipFile.getFilename(), e.getMessage());
        }
    }

    private List<Resource> getAllZipFilesToImport() {
        List<Resource> zips = new ArrayList<>();
        File folderImports = new File(BOOKS_IMPORT_FOLDER_PATH);
        File[] files = folderImports.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".zip")) {
                    zips.add(new FileSystemResource(file));
                }
            }
        }
        return zips;
    }
}
