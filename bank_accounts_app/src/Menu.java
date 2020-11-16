import java.util.Scanner;

public class Menu {

    private static CommandList commandList = new CommandList();
    private Scanner scanner = new Scanner(System.in);
    private Portfolio portfolio;

    Menu(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public void firstLogin(Portfolio portfolio) {
        commandList.clear();
        System.out.println("########  Portfolio Application  ########");
        System.out.println("\nEnter your name: ");
        String name = scanner.nextLine();
        portfolio.setOwner(name);
        this.mainMenu();
    }

    public void mainMenu() {
        boolean exit = true;
        do {
            commandList.clear();
            System.out.println("########  " + portfolio.getOwner() + "'s Portfolio  ########");
            System.out.println("\nMain menu: ");
            System.out.println("1.) Banks & Accounts"); //OK
            System.out.println("0.) Exit Application"); //OK

            if(!exit) {
                System.out.println("\nPlease enter valid menu point\n");
            }

            exit = true;
            String option = scanner.nextLine();
            //sub_menus
            switch (option) {
                case "1":
                    menuBanks();
                    break;
                case "0":
                    System.exit(0);
                    break;
                default:
                    exit = false;
                    break;
            }
        } while (!exit);
    }

    private void menuBanks() {
        boolean exit = true;
        do {
            commandList.clear();
            System.out.println("########  Banks & Accounts  ########");
            System.out.println("\nOptions: ");
            System.out.println("1.) Manage my accounts"); //OK
            System.out.println("2.) Show total balance"); //OK
            System.out.println("3.) Show detailed balance"); //OK
            System.out.println("0.) Return to main menu"); //OK

            if(!exit) {
                System.out.println("\nPlease enter valid menu point\n");
            }

            exit = true;
            String option = scanner.nextLine();
            //sub_menus
            switch (option) {
                case "1":
                    menuBanksAccounts();
                    break;
                case "2":
                    menuBalance(2);
                    break;
                case "3":
                    menuBalance(3);
                    break;
                case "0":
                    mainMenu();
                    break;
                default:
                    exit = false;
                    break;
            }
        } while (!exit);
    }

    public void menuBanksAccounts() {
        boolean exit = true;
        do {
            commandList.clear();
            System.out.println("########  Manage Accounts  ########");
            System.out.println("\nOptions: ");
            System.out.println("1.) Add new Bank"); //OK
            if(portfolio.bankExists()) {
                System.out.println("2.) Add new Account"); //OK
                System.out.println("3.) Manage existing Banks"); //OK
                System.out.println("4.) Manage existing Account - NOT WORKING");
            }
            System.out.println("0.) Return to Banks & Accounts menu"); //OK

            if(!exit) {
                System.out.println("\nPlease enter valid menu point\n");
            }

            exit = true;
            int option = commandList.scanInt(scanner);
            if(!portfolio.bankExists() && option>1) {
                option = -1;
            }
            //sub_menus
            switch (option) {
                case 1:
                    addNewBank();
                    break;
                case 2:
                    addNewAccount();
                    break;
                case 3:
                    manageBank(-1);
                    break;
                case 0:
                    menuBanks();
                    break;
                default:
                    exit = false;
                    break;
            }
        } while (!exit);
    }

    public void addNewBank() {
        commandList.clear();
        System.out.println("########  Adding New Bank  ########");
        System.out.print("\nEnter name of the Bank: ");
        String bankName = scanner.nextLine();
        portfolio.addNewBank(bankName);
        System.out.println(bankName + " was added.");
        commandList.waitSec(2);
        menuBanksAccounts();
    }

    public void addNewAccount() {
        String menuHeader = "########  Creating Account  ########";
        if(portfolio.getNumberOfBanks() == 0) {
            commandList.clear();
            System.out.println(menuHeader + "\n");
            System.out.println("\nAdd your first Bank before adding a new account.");
            commandList.waitSec(5);
            menuBanksAccounts();
        } else {
            int selectedBank = selectBank(menuHeader);

            commandList.clear();
            System.out.println(menuHeader);
            System.out.println("\nEnter name of the new Account: ");
            String accountName = scanner.nextLine();
            portfolio.addNewAccount(selectedBank, accountName);
            System.out.println("\n" + accountName + " account was created in "
                    + portfolio.getBankName(selectedBank));
            commandList.waitSec(2);
            menuBanksAccounts();
        }
    }

    public void manageBank(int i) {
        //if i<0 then user must select a bank, else the bank is given

        String menuHeader = "########  Manage Bank  ########";
        int selectedBank;
        if(i<0) {
            selectedBank = selectBank(menuHeader);
        } else {
            selectedBank = i;
        }

        boolean exit = true;
        do {
            commandList.clear();
            System.out.println(menuHeader);
            System.out.println("\nSelected bank: " + portfolio.getBankName(selectedBank) + "\n");
            System.out.println("Options: ");
            System.out.println("1.) Edit Bank name"); //OK
            System.out.println("2.) Delete Bank"); //OK
            System.out.println("0.) Return to previous menu"); //OK

            if(!exit) {
                System.out.println("\nPlease enter valid menu point\n");
            }
            exit = true;
            String option = scanner.nextLine();

            switch(option) {
                case "1":
                    String oldName = portfolio.getBankName(selectedBank);
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    portfolio.setBankName(selectedBank, newName);
                    System.out.println("\n" + oldName + " has been renamed to " + newName);
                    commandList.waitSec(3);
                    manageBank(selectedBank);
                    break;
                case "2":
                    commandList.clear();
                    System.out.println(menuHeader);
                    System.out.println("\n" + portfolio.getBankName(selectedBank) +
                            " and all related accounts are deleted");
                    portfolio.removeBank(selectedBank);
                    commandList.waitSec(3);
                    menuBanksAccounts();
                    break;
                case "0":
                    menuBanksAccounts();
                    break;
                default:
                    exit = false;
                    break;
            }
        } while(!exit);
    }

    public int selectBank(String menuHeader) {
        int selectedBank;
        boolean exit;
        do {
            exit = true;
            commandList.clear();
            System.out.println(menuHeader);
            System.out.println("\nEnter a valid Bank ID");
            portfolio.printBankList();
            selectedBank = commandList.scanInt(scanner)-1;
            if(selectedBank<0 || selectedBank>=portfolio.getNumberOfBanks()) {
                exit = false;
            }
        } while (!exit);
        return selectedBank;
    }

    public void menuBalance(int parameter) {
        //INPUTS: 2 = only total || 3 = detailed

        commandList.clear();
        if(parameter == 2) {
            System.out.println("********  Total Balance  ********\n");
        } else {
            System.out.println("********  Detailed Balance  ********\n");
        }
        int numberOfBanks = portfolio.getNumberOfBanks();
        int totalBalance =0;
        if(numberOfBanks ==0) {
            System.out.println("No existing banks / accounts");
            commandList.waitSec(3);
            menuBanks();
        } else {
            for(int i=0; i< numberOfBanks; i++) {
                if(parameter == 3) {
                    System.out.println("\n" + portfolio.getBankName(i));
                }
                int numberOfAccounts = portfolio.getNumberOfAccounts(i);
                if(numberOfAccounts == 0) {
                    if(parameter==3) {
                        System.out.println("  -No accounts\n");
                    }
                } else {
                    for(int j=0; j< numberOfAccounts; j++) {
                        if(parameter ==3) {
                            System.out.println("  -" + portfolio.getAccountName(i, j) +
                                    ": " + portfolio.getBalance(i, j));
                        }
                        totalBalance += portfolio.getBalance(i,j);
                    }
                }
            }
            System.out.println("\n\nTotal Balance :     " + totalBalance);
            System.out.println("\n\n**** Press Enter to continue ****");
            scanner.nextLine();
            menuBanks();
        }
    }
}