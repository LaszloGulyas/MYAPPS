public class Account {

    private String accountName;
    private int balance;

    public Account(String accountName) {
        this.accountName = accountName;
        this.balance = 0;
    }

    public String getAccountName() {
        return accountName;
    }

    public int getBalance() {
        return balance;
    }
}
