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

    public boolean getIsActive() {
        return isActive;
    }

    public BookChangeStatusRequest setIsActive(boolean active) {
        this.isActive = active;
        return this;
    }

    public String getDeactivateReason() {
        return deactivateReason;
    }

    public BookChangeStatusRequest setDeactivateReason(String deactivateReason) {
        this.deactivateReason = deactivateReason;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "isActive=" + isActive +
                ", deactivateReason='" + deactivateReason + '\'' +
                '}';
    }
}
