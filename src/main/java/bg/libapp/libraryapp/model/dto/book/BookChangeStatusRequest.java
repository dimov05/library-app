package bg.libapp.libraryapp.model.dto.book;

import jakarta.validation.constraints.NotNull;

public class BookChangeStatusRequest {
    @NotNull(message = "isActivate status should be 'true' or 'false'")
    private boolean isActive;
    private String deactivateReason;

    public BookChangeStatusRequest() {
    }

    public BookChangeStatusRequest(boolean isActive, String deactivateReason) {
        this.isActive = isActive;
        this.deactivateReason = deactivateReason;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        // Handle the conversion from string "true" or "false" to Boolean
        this.isActive = Boolean.parseBoolean(String.valueOf(isActive).toLowerCase());
    }

    public String getDeactivateReason() {
        return deactivateReason;
    }

    public void setDeactivateReason(String deactivateReason) {
        this.deactivateReason = deactivateReason.toUpperCase();
    }

    @Override
    public String toString() {
        return "{" +
                "isActive=" + isActive +
                ", deactivateReason='" + deactivateReason + '\'' +
                '}';
    }
}
