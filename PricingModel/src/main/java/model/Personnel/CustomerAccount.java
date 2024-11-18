/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Personnel;

/**
 *
 * @author kal bugrara
 */
public class CustomerAccount {

    private String accountId;
    private double balance;
    private String status; 

    public CustomerAccount() {
        this.accountId = "Unknown";
        this.balance = 0.0;
        this.status = "Inactive";
    }

    public CustomerAccount(String accountId, double initialBalance, String status) {
        this.accountId = accountId;
        this.balance = initialBalance;
        this.status = status;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void addBalance(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }

    public boolean deductBalance(double amount) {
        if (amount > 0 && amount <= this.balance) {
            this.balance -= amount;
            return true; 
        }
        return false; 
    }

    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CustomerAccount{" +
                "accountId='" + accountId + '\'' +
                ", balance=" + balance +
                ", status='" + status + '\'' +
                '}';
    }
}
