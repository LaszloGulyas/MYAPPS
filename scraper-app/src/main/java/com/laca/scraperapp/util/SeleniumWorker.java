package com.laca.scraperapp.util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class SeleniumWorker {

    private final WebDriver driver;

    public SeleniumWorker() {
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");
        //options.addArguments("start-maximized");
        //options.addArguments("window-size=1920,1080");
        //options.addArguments("disable-infobars");
        //options.addArguments("--disable-extensions");
        //options.addArguments("--no-sandbox");
        //options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    }

    static {
        URL resource = SeleniumWorker.class.getClassLoader().getResource("driver/chromedriver.exe");
        File f = new File("Driver");
        if (!f.exists()) {
            f.mkdirs();
        }
        File chromeDriver = new File("Driver" + File.separator + "chromedriver.exe");
        if (!chromeDriver.exists()) {
            try {
                chromeDriver.createNewFile();
                FileUtils.copyURLToFile(resource, chromeDriver);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.setProperty("webdriver.chrome.driver", chromeDriver.getAbsolutePath());
    }

    public WebDriver getDriver() {
        return driver;
    }
}
