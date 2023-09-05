package bg.libapp.libraryapp.model.dto.user;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class ChangeRoleRequest {
    @Min(value = 0, message = "Role must be between 0 and 2 (0->USER, 1->MODERATOR,2->ADMIN)")
    @Max(value = 2, message = "Role must be between 0 and 2 (0->USER, 1->MODERATOR,2->ADMIN)")
    private int role;

    public ChangeRoleRequest() {
    }

    public ChangeRoleRequest(int role) {
        this.role = role;
    }

    public int getRole() {
        return role;
    }

    public ChangeRoleRequest setRole(int role) {
        this.role = role;
        return this;
    }
}
