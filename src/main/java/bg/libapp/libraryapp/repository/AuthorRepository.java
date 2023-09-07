package bg.libapp.libraryapp.repository;

import bg.libapp.libraryapp.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findAuthorByFirstNameAndLastName(String firstName, String lastName);
}
