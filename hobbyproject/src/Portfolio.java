import java.util.ArrayList;

public class Portfolio {

    private ArrayList<Bank> banks;

    public Portfolio() {
        this.banks = new ArrayList<>();
    }

    public ArrayList<Bank> getBankArray() {
        ArrayList<Bank> export = new ArrayList<>(banks);
        return export;
    }

    public Bank getOrAddBank(String name) {
        for (Bank b : banks) {
            if (b.getBankName().equals(name)) {
                return b;
            }
        }
        Bank newBank = new Bank(name);
        banks.add(newBank);
        return newBank;
    }

    public String listOfBanks() {
        String s = "";
        for (Bank b : banks) {
            s += b.getBankName() + ";";
        }
        if (s.length()==0) {
            return "empty list";
        }
        return s = s.substring(0, s.length()-1);
    }

    public int sumOfBanks() {
        int sum = 0;
        for (Bank b : banks) {
            sum += b.sumOfAccounts();
        }
        return sum;
    }
}
