package bg.libapp.libraryapp.service;

import bg.libapp.libraryapp.exceptions.AuthorNotFoundException;
import bg.libapp.libraryapp.exceptions.BookAlreadyAddedException;
import bg.libapp.libraryapp.exceptions.BookNotFoundException;
import bg.libapp.libraryapp.exceptions.GenreNotFoundException;
import bg.libapp.libraryapp.model.dto.book.BookAddRequest;
import bg.libapp.libraryapp.model.dto.book.BookExtendedDTO;
import bg.libapp.libraryapp.model.entity.Author;
import bg.libapp.libraryapp.model.entity.Book;
import bg.libapp.libraryapp.model.entity.Genre;
import bg.libapp.libraryapp.model.mappers.BookMapper;
import bg.libapp.libraryapp.repository.AuthorRepository;
import bg.libapp.libraryapp.repository.BookRepository;
import bg.libapp.libraryapp.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;
    private final GenreRepository genreRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, BookMapper bookMapper, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookMapper = bookMapper;
        this.genreRepository = genreRepository;
    }

    public void saveNewBook(BookAddRequest bookAddRequest) {
        Book book = bookRepository.findByIsbn(bookAddRequest.getIsbn());
        if (book != null) {
            throw new BookAlreadyAddedException(bookAddRequest.getIsbn());
        }
        book = bookMapper.toBook(bookAddRequest);
        bookRepository.saveAndFlush(book);
    }

    public BookExtendedDTO findBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn);
        if (book == null) {
            throw new BookNotFoundException(isbn);
        }
        return bookMapper.toBookExtendedDTO(book);
    }

    public Set<BookExtendedDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toBookExtendedDTO)
                .collect(Collectors.toSet());
    }

    public Set<BookExtendedDTO> getAllBooksByAuthorFirstAndLastName(String firstName, String lastName) {
        Author author = authorRepository.findAuthorByFirstNameAndLastName(firstName, lastName);
        if (author == null) {
            throw new AuthorNotFoundException(firstName, lastName);
        }
        return bookRepository.findAllByAuthorsContaining(author)
                .stream()
                .map(bookMapper::toBookExtendedDTO)
                .collect(Collectors.toSet());
    }

    public Set<BookExtendedDTO> getAllBooksByGenre(String genre) {
        Genre genreToCheck = genreRepository.findByName(genre);
        if (genreToCheck == null) {
            throw new GenreNotFoundException(genre);
        }
        return bookRepository.findBookByGenresContaining(genreToCheck)
                .stream()
                .map(bookMapper::toBookExtendedDTO)
                .collect(Collectors.toSet());
    }
}
