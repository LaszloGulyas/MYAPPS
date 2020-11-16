import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CommandList {

    private int osType = 0;
    //0 = default OS, windows | 1 = Linux

    public CommandList() {
        if(System.getProperty("os.name").equals("Linux")) {
            osType = 1;
        }
    }

    public void clear() {
        if(osType == 1) { //this runs on Linux OS
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } else { //this runs on other OS
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (Exception errMsg) {
                System.out.println("Error");
            }
        }
    }

    public void waitSec(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (Exception errMsg) {
            System.out.println("Error");
        }
    }

    public int scanInt(Scanner scanner) {
        boolean isInt = scanner.hasNextInt();
        if(isInt) {
            int i = scanner.nextInt();
            scanner.nextLine();
            return i;
        } else {
            scanner.nextLine();
            return -1;
        }
    }
}