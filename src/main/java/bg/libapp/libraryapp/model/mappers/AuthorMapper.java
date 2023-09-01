package bg.libapp.libraryapp.model.mappers;

import bg.libapp.libraryapp.model.dto.author.AuthorBookViewDTO;
import bg.libapp.libraryapp.model.dto.author.AuthorViewDTO;
import bg.libapp.libraryapp.model.entity.Author;
import bg.libapp.libraryapp.repository.AuthorRepository;
import bg.libapp.libraryapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AuthorMapper {
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    @Autowired
    public AuthorMapper(AuthorRepository authorRepository, @Lazy BookMapper bookMapper, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookMapper = bookMapper;
        this.bookRepository = bookRepository;
    }

    public AuthorViewDTO mapAuthorViewDTOFromAuthor(Author author) {
        return new AuthorViewDTO()
                .setFirstName(author.getFirstName())
                .setLastName(author.getLastName())
                .setBooks(author.getBooks()
                        .stream()
                        .map(bookMapper::mapBookAuthorViewDTOFromBook)
                        .collect(Collectors.toSet()));
    }

    public AuthorBookViewDTO mapAuthorBookViewDTOFromAuthor(Author author) {
        return new AuthorBookViewDTO()
                .setFirstName(author.getFirstName())
                .setLastName(author.getLastName());
    }

    public Author mapAuthorFromAuthorBookViewDTO(AuthorBookViewDTO authorBookViewDTO) {
        Author author = authorRepository.findAuthorByFirstNameAndLastName(authorBookViewDTO.getFirstName(), authorBookViewDTO.getLastName());
        return new Author()
                .setId(authorRepository.findAuthorByFirstNameAndLastName(authorBookViewDTO.getFirstName(), authorBookViewDTO.getLastName()).getId())
                .setFirstName(authorBookViewDTO.getFirstName())
                .setLastName(authorBookViewDTO.getLastName())
                .setBooks(bookRepository.findAllByAuthorsContaining(author));
    }
}
