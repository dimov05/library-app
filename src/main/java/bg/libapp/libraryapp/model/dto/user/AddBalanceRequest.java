package bg.libapp.libraryapp.model.dto.user;

import java.math.BigDecimal;

public class AddBalanceRequest {
    private BigDecimal balance;

    public AddBalanceRequest() {
    }

    public AddBalanceRequest(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public AddBalanceRequest setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }
}
