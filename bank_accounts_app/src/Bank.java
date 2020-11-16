import java.util.ArrayList;

public class Bank {

    private String bankName;
    private ArrayList<Account> accounts;

    public Bank(String bankName) {
        this.bankName = bankName;
        this.accounts = new ArrayList<Account>();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void addNewAccount(String accountName) {
        accounts.add(new Account(accountName));
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public String getAccountName(int accountNumber) {
        return accounts.get(accountNumber).getAccountName();
    }

    public int getBalance(int accountNumber) {
        return accounts.get(accountNumber).getBalance();
    }
}
