package bg.libapp.libraryapp.repository;

import bg.libapp.libraryapp.model.entity.Author;
import bg.libapp.libraryapp.model.entity.Book;
import bg.libapp.libraryapp.model.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book,Long>, JpaSpecificationExecutor<Book> {

    Set<Book> findAllByAuthorsContaining(Author author);

    boolean existsByIsbn(String isbn);

    Optional<Book> findByIsbn(String isbn);

    Set<Book> findBookByGenresContaining(Genre genre);
}
