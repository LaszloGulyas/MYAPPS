import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Downloader downloader = new Downloader();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                running = false;
            } else {
                try {

                    downloader.download(input);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Exit application");
    }
}
