package org.bsim.intern.shared.dto;

public class WalletsBalanceDTO {

    private static final long serialVersionUID= 163338024479094157L;
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
