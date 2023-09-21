package bg.libapp.libraryapp.model.mappers;

import bg.libapp.libraryapp.model.dto.book.BookAddRequest;
import bg.libapp.libraryapp.model.dto.book.BookDTO;
import bg.libapp.libraryapp.model.dto.book.BookExtendedDTO;
import bg.libapp.libraryapp.model.entity.Book;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.stream.Collectors;

public class BookMapper {

    public static BookDTO mapToBookDTO(Book book) {
        return new BookDTO()
                .setIsbn(book.getIsbn())
                .setYear(book.getYear())
                .setTitle(book.getTitle())
                .setPublisher(book.getPublisher())
                .setActive(book.isActive())
                .setDeactivateReason(book.getDeactivateReason())
                .setGenres(book.getGenres()
                        .stream()
                        .map(GenreMapper::mapToGenreDTO)
                        .collect(Collectors.toSet()));
    }

    public static Book mapToBook(BookAddRequest bookAddRequest) {
        return new Book()
                .setIsbn(bookAddRequest.getIsbn())
                .setTitle(bookAddRequest.getTitle())
                .setYear(bookAddRequest.getYear())
                .setDateAdded(LocalDateTime.now())
                .setPublisher(bookAddRequest.getPublisher())
                .setGenres(new HashSet<>())
                .setAuthors(new HashSet<>());
    }

    public static BookExtendedDTO mapToBookExtendedDTO(Book book) {
        return new BookExtendedDTO()
                .setIsbn(book.getIsbn())
                .setTitle(book.getTitle())
                .setPublisher(book.getPublisher())
                .setYear(book.getYear())
                .setDateAdded(book.getDateAdded().toLocalDate().toString())
                .setActive(book.isActive())
                .setDeactivateReason(book.getDeactivateReason())
                .setGenres(book.getGenres()
                        .stream()
                        .map(GenreMapper::mapToGenreDTO)
                        .collect(Collectors.toSet()))
                .setAuthors(book.getAuthors()
                        .stream()
                        .map(AuthorMapper::mapToAuthorDTO)
                        .collect(Collectors.toSet()));
    }
}