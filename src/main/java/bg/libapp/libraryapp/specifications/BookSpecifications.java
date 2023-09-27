package bg.libapp.libraryapp.specifications;

import bg.libapp.libraryapp.model.entity.Author;
import bg.libapp.libraryapp.model.entity.Book;
import bg.libapp.libraryapp.model.entity.Genre;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import static bg.libapp.libraryapp.model.constants.ApplicationConstants.*;

public class BookSpecifications {

    public static Specification<Book> fieldLike(String fieldName, String value) {
        return ((root, query, builder) -> builder.like(root.get(fieldName), "%" + value + "%"));
    }

    public static Specification<Book> genreEquals(int genreId) {
        return (root, query, builder) -> {
            Join<Book, Genre> bookGenres = root.join(GENRES);
            return builder.equal(bookGenres.get(ID), genreId);
        };
    }

    public static Specification<Book> fieldGreaterThanOrEqual(String fieldName, int value) {
        return ((root, query, builder) -> builder.greaterThanOrEqualTo(root.get(fieldName), value));
    }

    public static Specification<Book> fieldLowerThanOrEqual(String fieldName, int value) {
        return ((root, query, builder) -> builder.lessThanOrEqualTo(root.get(fieldName), value));
    }

    public static Specification<Book> authorNameEquals(String authorFieldName, String authorName) {
        return (root, query, builder) -> {
            Join<Book, Author> bookAuthors = root.join(AUTHORS);
            return builder.equal(bookAuthors.get(authorFieldName), authorName);
        };
    }

    public static Specification<Book> isActive(Boolean status) {
        return ((root, query, builder) -> builder.equal(root.get(IS_ACTIVE), status));
    }
}
