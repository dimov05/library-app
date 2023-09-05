package bg.libapp.libraryapp.model.mappers;

import bg.libapp.libraryapp.model.dto.author.AuthorRequest;
import bg.libapp.libraryapp.model.dto.book.BookAddRequest;
import bg.libapp.libraryapp.model.dto.book.BookDTO;
import bg.libapp.libraryapp.model.dto.book.BookExtendedDTO;
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

    public BookDTO toBookDTO(Book book) {
        return new BookDTO()
                .setIsbn(book.getIsbn())
                .setYear(book.getYear())
                .setTitle(book.getTitle())
                .setPublisher(book.getPublisher())
                .setGenres(book.getGenres()
                        .stream()
                        .map(genreMapper::toGenreDTO)
                        .collect(Collectors.toSet()));
    }

    public Book toBook(BookAddRequest bookAddRequest) {
        return new Book()
                .setIsbn(bookAddRequest.getIsbn())
                .setTitle(bookAddRequest.getTitle())
                .setYear(bookAddRequest.getYear())
                .setDateAdded(LocalDateTime.now())
                .setPublisher(bookAddRequest.getPublisher())
                .setGenres(bookAddRequest.getGenres()
                        .stream()
                        .map(genre -> this.genreRepository.findByName(genre.getName()))
                        .collect(Collectors.toSet()))
                .setAuthors(bookAddRequest.getAuthors()
                        .stream()
                        .map(this::findOrCreate)
                        .collect(Collectors.toSet()));
    }

    public BookExtendedDTO toBookExtendedDTO(Book book) {
        return new BookExtendedDTO()
                .setIsbn(book.getIsbn())
                .setTitle(book.getTitle())
                .setPublisher(book.getPublisher())
                .setYear(book.getYear())
                .setDateAdded(book.getDateAdded())
                .setGenres(book.getGenres()
                        .stream()
                        .map(genreMapper::toGenreDTO)
                        .collect(Collectors.toSet()))
                .setAuthors(book.getAuthors()
                        .stream()
                        .map(authorMapper::toAuthorDTO)
                        .collect(Collectors.toSet()));
    }

    private Author findOrCreate(AuthorRequest author) {
        Author searchedAuthor = this.authorRepository.findAuthorByFirstNameAndLastName(author.getFirstName(), author.getLastName());
        return searchedAuthor == null
                ? authorRepository.saveAndFlush(new Author()
                .setFirstName(author.getFirstName())
                .setLastName(author.getLastName()))
                : searchedAuthor;
    }
}
