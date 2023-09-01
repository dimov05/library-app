package bg.libapp.libraryapp.service;

import bg.libapp.libraryapp.exceptions.AuthorNotFoundException;
import bg.libapp.libraryapp.exceptions.BookAlreadyAddedException;
import bg.libapp.libraryapp.exceptions.BookNotFoundException;
import bg.libapp.libraryapp.exceptions.GenreNotFoundException;
import bg.libapp.libraryapp.model.dto.book.BookAddDTO;
import bg.libapp.libraryapp.model.dto.book.BookViewDTO;
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

    public void saveNewBook(BookAddDTO bookAddDTO) {
        Book book = this.bookRepository.findByIsbn(bookAddDTO.getIsbn());
        if (book != null) {
            throw new BookAlreadyAddedException("Book with this isbn: " + bookAddDTO.getIsbn() + ",is already added!");
        }
        book = bookMapper.mapBookFromBookAddDTO(bookAddDTO);
        this.bookRepository.saveAndFlush(book);
    }

    public BookViewDTO findBookByIsbn(String isbn) {
        Book book = this.bookRepository.findByIsbn(isbn);
        if (book == null) {
            throw new BookNotFoundException("Book with this isbn is not present in library!");
        }
        return bookMapper.mapBookViewDTOFromBook(book);
    }

    public Set<BookViewDTO> getAllBooks() {
        return this.bookRepository.findAll()
                .stream()
                .map(bookMapper::mapBookViewDTOFromBook)
                .collect(Collectors.toSet());
    }

    public Set<BookViewDTO> getAllBooksByAuthorFirstAndLastName(String firstName, String lastName) {
        Author author = this.authorRepository.findAuthorByFirstNameAndLastName(firstName, lastName);
        if (author == null) {
            throw new AuthorNotFoundException("Author with this first and last name is not present in library!");
        }
        return this.bookRepository.findAllByAuthorsContaining(author)
                .stream()
                .map(bookMapper::mapBookViewDTOFromBook)
                .collect(Collectors.toSet());
    }

    public Set<BookViewDTO> getAllBooksByGenre(String genre) {
        Genre genreToCheck = this.genreRepository.findByName(genre);
        if (genreToCheck == null) {
            throw new GenreNotFoundException("Genre with this name is not present in the library!");
        }
        return this.bookRepository.findBookByGenresContaining(genreToCheck)
                .stream()
                .map(bookMapper::mapBookViewDTOFromBook)
                .collect(Collectors.toSet());
    }
}
