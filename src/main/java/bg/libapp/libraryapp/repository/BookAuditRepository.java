package bg.libapp.libraryapp.repository;

import bg.libapp.libraryapp.model.entity.BookAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookAuditRepository extends JpaRepository<BookAudit,Long> {
}
