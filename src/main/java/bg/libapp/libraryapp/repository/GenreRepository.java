package bg.libapp.libraryapp.repository;

import bg.libapp.libraryapp.model.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre,Long> {
    Genre findByName(String name);
}
