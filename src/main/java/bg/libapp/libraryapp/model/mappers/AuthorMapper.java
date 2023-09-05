package bg.libapp.libraryapp.model.mappers;


import bg.libapp.libraryapp.model.dto.author.AuthorDTO;
import bg.libapp.libraryapp.model.dto.author.AuthorExtendedDTO;
import bg.libapp.libraryapp.model.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AuthorMapper {
    private final BookMapper bookMapper;

    @Autowired
    public AuthorMapper(@Lazy BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public AuthorExtendedDTO toAuthorExtendedDTO(Author author) {
        return new AuthorExtendedDTO()
                .setFirstName(author.getFirstName())
                .setLastName(author.getLastName())
                .setBooks(author.getBooks()
                        .stream()
                        .map(bookMapper::toBookDTO)
                        .collect(Collectors.toSet()));
    }

    public AuthorDTO toAuthorDTO(Author author) {
        return new AuthorDTO()
                .setFirstName(author.getFirstName())
                .setLastName(author.getLastName());
    }
}
