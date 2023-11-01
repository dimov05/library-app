package bg.libapp.libraryapp.event;

public class BookImportAuditEvent {
    private Boolean isImported;
    private String failedZip;
    private String failedFile;
    private String failReason;

    public BookImportAuditEvent() {
    }

    public BookImportAuditEvent(Boolean isImported, String failedZip, String failedFile, String failReason) {
        this.isImported = isImported;
        this.failedZip = failedZip;
        this.failedFile = failedFile;
        this.failReason = failReason;
    }

    public Boolean getImported() {
        return isImported;
    }

    public void setImported(Boolean imported) {
        isImported = imported;
    }

    public String getFailedZip() {
        return failedZip;
    }

    public void setFailedZip(String failedZip) {
        this.failedZip = failedZip;
    }

    public String getFailedFile() {
        return failedFile;
    }

    public void setFailedFile(String failedFile) {
        this.failedFile = failedFile;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }
}
