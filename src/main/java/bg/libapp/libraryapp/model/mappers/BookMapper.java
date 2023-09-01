package bg.libapp.libraryapp.model.mappers;

import bg.libapp.libraryapp.model.dto.author.AuthorBookViewDTO;
import bg.libapp.libraryapp.model.dto.book.BookAddDTO;
import bg.libapp.libraryapp.model.dto.book.BookAuthorViewDTO;
import bg.libapp.libraryapp.model.dto.book.BookViewDTO;
import bg.libapp.libraryapp.model.entity.Author;
import bg.libapp.libraryapp.model.entity.Book;
import bg.libapp.libraryapp.repository.AuthorRepository;
import bg.libapp.libraryapp.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
public class BookMapper {
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final AuthorMapper authorMapper;
    private final GenreMapper genreMapper;

    @Autowired
    public BookMapper(AuthorRepository authorRepository, GenreRepository genreRepository, AuthorMapper authorMapper, GenreMapper genreMapper) {
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.authorMapper = authorMapper;
        this.genreMapper = genreMapper;
    }

    public BookAuthorViewDTO mapBookAuthorViewDTOFromBook(Book book) {
        return new BookAuthorViewDTO()
                .setIsbn(book.getIsbn())
                .setYear(book.getYear())
                .setTitle(book.getTitle())
                .setPublisher(book.getPublisher())
                .setGenres(book.getGenres()
                        .stream()
                        .map(genreMapper::mapGenreBookViewDTOFromGenre)
                        .collect(Collectors.toSet()));
    }

    public Book mapBookFromBookAddDTO(BookAddDTO bookAddDTO) {
        return new Book()
                .setIsbn(bookAddDTO.getIsbn())
                .setTitle(bookAddDTO.getTitle())
                .setYear(bookAddDTO.getYear())
                .setDateAdded(LocalDateTime.now())
                .setPublisher(bookAddDTO.getPublisher())
                .setGenres(bookAddDTO.getGenres()
                        .stream()
                        .map(genre -> this.genreRepository.findByName(genre.getName()))
                        .collect(Collectors.toSet()))
                .setAuthors(bookAddDTO.getAuthors()
                        .stream()
                        .map(author -> {
                            saveAuthorIfNotPresent(author);
                            return authorRepository.findAuthorByFirstNameAndLastName(author.getFirstName(), author.getLastName());
                        })
                        .collect(Collectors.toSet()));
    }

    public BookViewDTO mapBookViewDTOFromBook(Book book) {
        return new BookViewDTO()
                .setIsbn(book.getIsbn())
                .setTitle(book.getTitle())
                .setPublisher(book.getPublisher())
                .setYear(book.getYear())
                .setDateAdded(book.getDateAdded())
                .setGenres(book.getGenres()
                        .stream()
                        .map(genreMapper::mapGenreBookViewDTOFromGenre)
                        .collect(Collectors.toSet()))
                .setAuthors(book.getAuthors()
                        .stream()
                        .map(authorMapper::mapAuthorBookViewDTOFromAuthor)
                        .collect(Collectors.toSet()));
    }

    private void saveAuthorIfNotPresent(AuthorBookViewDTO author) {
        if (this.authorRepository.findAuthorByFirstNameAndLastName(author.getFirstName(), author.getLastName()) == null) {
            this.authorRepository.saveAndFlush(new Author()
                    .setFirstName(author.getFirstName())
                    .setLastName(author.getLastName()));
        }
    }
}
