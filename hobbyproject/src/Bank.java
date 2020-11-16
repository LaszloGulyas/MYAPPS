import java.util.ArrayList;

public class Bank {

    private String bankName;
    private ArrayList<Account> accounts;

    public Bank(String bankName) {
        this.bankName = bankName;
        this.accounts = new ArrayList<>();
    }

    public ArrayList<Account> getAccountArray() {
        ArrayList<Account> export = new ArrayList<>(accounts);
        return export;
    }

    public String getBankName() {
        return bankName;
    }

    public void addAccount(String name, int balance) {
        accounts.add(new Account(name, balance));
    }

    public String listOfAccounts() {
        String s = "";
        for (Account a : accounts) {
            s += a.getAccountName() + ";";
        }
        return s.substring(0, s.length()-1);
    }
    public int sumOfAccounts() {
        int sum = 0;
        for (Account a : accounts) {
            sum += a.getBalance();
        }
        return sum;
    }
}
