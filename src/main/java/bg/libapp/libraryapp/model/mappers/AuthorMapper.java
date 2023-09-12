package bg.libapp.libraryapp.model.mappers;


import bg.libapp.libraryapp.model.dto.author.AuthorDTO;
import bg.libapp.libraryapp.model.dto.author.AuthorExtendedDTO;
import bg.libapp.libraryapp.model.entity.Author;

import java.util.stream.Collectors;

public class AuthorMapper {

    public static AuthorExtendedDTO mapToAuthorExtendedDTO(Author author) {
        return new AuthorExtendedDTO()
                .setFirstName(author.getFirstName())
                .setLastName(author.getLastName())
                .setBooks(author.getBooks()
                        .stream()
                        .map(BookMapper::mapToBookDTO)
                        .collect(Collectors.toSet()));
    }

    public static AuthorDTO mapToAuthorDTO(Author author) {
        return new AuthorDTO()
                .setFirstName(author.getFirstName())
                .setLastName(author.getLastName());
    }
}