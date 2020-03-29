package org.cesken.perfbook.model;

public class AccountInfoResponse {
    final long accountId;
    final int balance;

    public AccountInfoResponse(long accountId, int balance) {
        if (accountId <= 0) {
            throw new IllegalArgumentException("accountId must be positive");
        }
        // Balance can be negative

        this.accountId = accountId;
        this.balance = balance;
    }

    public long getAccountid() {
        return accountId;
    }

    public int getBalance() {
        return balance;
    }
}
