package org.bsim.intern.ui.model.response;

public class WalletBalanceResponse {

    private String name;
    private long balance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }
}
