package bg.libapp.libraryapp.service;

import bg.libapp.libraryapp.event.SaveBookAuditEvent;
import bg.libapp.libraryapp.event.UpdatePublisherBookAuditEvent;
import bg.libapp.libraryapp.event.UpdateYearBookAuditEvent;
import bg.libapp.libraryapp.model.entity.User;
import bg.libapp.libraryapp.model.enums.Audit;
import bg.libapp.libraryapp.model.mappers.BookAuditMapper;
import bg.libapp.libraryapp.repository.BookAuditRepository;
import bg.libapp.libraryapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static bg.libapp.libraryapp.model.constants.ApplicationConstants.PUBLISHER_BOOK_FIELD;
import static bg.libapp.libraryapp.model.constants.ApplicationConstants.YEAR_BOOK_FIELD;

@Service
@Transactional
public class BookAuditService {
    private final UserRepository userRepository;
    private final BookAuditRepository bookAuditRepository;

    @Autowired
    public BookAuditService(UserRepository userRepository, BookAuditRepository bookAuditRepository) {
        this.userRepository = userRepository;
        this.bookAuditRepository = bookAuditRepository;
    }

    public void updateYearOfBook(UpdateYearBookAuditEvent event) {
        event.setUser(getUserForAudit());
        event.setNewValue(String.valueOf(event.getBook().getYear()));
        event.setOperationType(Audit.UPDATE.name());
        event.setFieldName(YEAR_BOOK_FIELD);
        bookAuditRepository.saveAndFlush(BookAuditMapper.mapToBookAudit(event));
    }

    public void updatePublisherOfBook(UpdatePublisherBookAuditEvent event) {
        event.setUser(getUserForAudit());
        event.setNewValue(String.valueOf(event.getBook().getYear()));
        event.setOperationType(Audit.UPDATE.name());
        event.setFieldName(PUBLISHER_BOOK_FIELD);
        bookAuditRepository.saveAndFlush(BookAuditMapper.mapToBookAudit(event));
    }

    public void saveBook(SaveBookAuditEvent event) {
        event.setUser(getUserForAudit());
        event.setNewValue(event.getNewValue());
        event.setOperationType(Audit.ADD.name());
        bookAuditRepository.saveAndFlush(BookAuditMapper.mapToBookAudit(event));
    }

    private User getUserForAudit() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow();
    }
}
