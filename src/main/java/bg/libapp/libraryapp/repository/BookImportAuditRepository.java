package bg.libapp.libraryapp.repository;

import bg.libapp.libraryapp.model.entity.BookImportAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookImportAuditRepository extends JpaRepository<BookImportAudit,Long> {
}
