import java.util.ArrayList;

public class Portfolio {

    private String owner;
    private ArrayList<Bank> banks;

    Portfolio(String owner) {
        this.owner = owner;
        this.banks = new ArrayList<Bank>();
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public int getNumberOfBanks() {
        return banks.size();
    }

    public void addNewBank(String newBankName) {
        banks.add(new Bank(newBankName));
    }

    public void printBankList() {
        if(banks.size() != 0) {
            System.out.println("\nList of Banks: ");
            for (int i=0; i<banks.size(); i++) {
                System.out.println((i + 1) + ".) " + banks.get(i).getBankName());
            }
        }
    }

    public String getBankName(int bankIndex) {
        return banks.get(bankIndex).getBankName();
    }

    public void setBankName(int bankIndex, String name) {
        banks.get(bankIndex).setBankName(name);
    }

    public void addNewAccount(int bankIndex, String newAccountName) {
        banks.get(bankIndex).addNewAccount(newAccountName);
    }

    public boolean bankExists() {
        if(banks.size()>0) {
            return true;
        } else {
            return false;
        }
    }

    public void removeBank(int bankIndex) {
        banks.remove(bankIndex);
    }

    public int getNumberOfAccounts(int bankIndex) {
        return banks.get(bankIndex).getNumberOfAccounts();
    }

    public String getAccountName(int bankIndex, int accountIndex) {
        return banks.get(bankIndex).getAccountName(accountIndex);
    }

    public int getBalance(int bankIndex, int accountIndex) {
        return banks.get(bankIndex).getBalance(accountIndex);
    }
}
