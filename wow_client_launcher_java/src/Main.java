import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import java.lang.InterruptedException;
import java.io.File;

public class Main {

    public static void main (String[] args) throws InterruptedException, IOException, AWTException {

        System.out.println("Program is running...");
        Robot myBot = new Robot();
        Desktop desktop = Desktop.getDesktop();
        UserInterface appWindow = new UserInterface();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int)screenSize.getWidth();
        int screenHeight = (int)screenSize.getHeight();

        TimeUnit.SECONDS.sleep(20);


        //OPEN BLIZZARD LAUNCHERs
        System.out.println("Open Blizzard launcher");
        File blizzLauncher = new File("C:/Users/Public/Desktop/World of Warcraft Classic.lnk");
        desktop.open(blizzLauncher);
        TimeUnit.SECONDS.sleep(20);

        //RUN WOW CLASSIC
        System.out.println("Launch Classic WOW");
        myBot.keyPress(KeyEvent.VK_ENTER);
        myBot.keyRelease(KeyEvent.VK_ENTER);
        TimeUnit.SECONDS.sleep(30);

        //CHECK IF WOW RUNNING
        while(!isAppRunning()) {
                TimeUnit.SECONDS.sleep(120);
                myBot.keyPress(KeyEvent.VK_ENTER);
                myBot.keyRelease(KeyEvent.VK_ENTER);
                TimeUnit.SECONDS.sleep(30);
        }

        //SELECT REALM
        System.out.println("Select realm");
        myBot.mouseMove(screenWidth/2,screenHeight/4);
        myBot.mousePress(InputEvent.BUTTON1_MASK);
        myBot.mouseRelease(InputEvent.BUTTON1_MASK);
        myBot.keyPress(KeyEvent.VK_ENTER);
        myBot.keyRelease(KeyEvent.VK_ENTER);
        TimeUnit.SECONDS.sleep(20);

        //TRY TO CLICK ENTER BUTTON (to login character)
        System.out.println("Trying to click on [ENTER] button to login");

        while(true) {
            myBot.mouseMove((int)(screenWidth/2),(int)(screenHeight*925/1000));
            myBot.mousePress(InputEvent.BUTTON1_MASK);
            myBot.mouseRelease(InputEvent.BUTTON1_MASK);

            TimeUnit.SECONDS.sleep(60);

            myBot.keyPress(KeyEvent.VK_W);
            TimeUnit.MILLISECONDS.sleep(300);
            myBot.keyRelease(KeyEvent.VK_W);

            TimeUnit.SECONDS.sleep(60);

            myBot.keyPress(KeyEvent.VK_S);
            TimeUnit.MILLISECONDS.sleep(300);
            myBot.keyRelease(KeyEvent.VK_S);
        }

    }

    public static boolean isAppRunning () {
        boolean result = false;
        try {
            String line;
            Process p = Runtime.getRuntime().exec("tasklist.exe");
            BufferedReader input =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                if(line.startsWith("Wow.exe")){
                    result = true;
                    break;
                }
            }
            input.close();
        } catch (Exception err) {
            err.printStackTrace();
        }
        return result;
    }
}
