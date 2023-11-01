package bg.libapp.libraryapp.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "book_import_audit")
public class BookImportAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;
    @Column(name = "is_imported", nullable = false)
    private Boolean isImported;
    @Column(name = "zip_name")
    private String zipName;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "message")
    private String message;

    public BookImportAudit() {
    }

    public BookImportAudit(Long id, LocalDateTime eventDate, Boolean isImported, String zipName, String fileName, String message) {
        this.id = id;
        this.eventDate = eventDate;
        this.isImported = isImported;
        this.zipName = zipName;
        this.fileName = fileName;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public BookImportAudit setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public BookImportAudit setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
        return this;
    }

    public Boolean getImported() {
        return isImported;
    }

    public BookImportAudit setImported(Boolean imported) {
        isImported = imported;
        return this;
    }

    public String getZipName() {
        return zipName;
    }

    public BookImportAudit setZipName(String failedZip) {
        this.zipName = failedZip;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public BookImportAudit setFileName(String failedFile) {
        this.fileName = failedFile;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public BookImportAudit setMessage(String failReason) {
        this.message = failReason;
        return this;
    }
}
