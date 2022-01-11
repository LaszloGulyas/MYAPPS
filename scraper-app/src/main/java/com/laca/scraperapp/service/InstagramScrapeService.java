package com.laca.scraperapp.service;

import com.laca.scraperapp.config.InstagramCookieConfig;
import com.laca.scraperapp.util.SeleniumWorker;
import com.laca.scraperapp.util.Utils;
import org.openqa.selenium.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class InstagramScrapeService {

    private static final Logger logger = Logger.getLogger("InstagramScrapeService");

    @Autowired
    private InstagramCookieConfig cfg;

    private static final String siteUrl = "https://www.instagram.com/";
    private final String savedImagesFolderName = "downloadedInstaImages";
    private String savedImagesTargetPath = "";
    private int totalNrOfCreatedImages = 0;

    public InstagramScrapeService() {

    }

    private WebDriver init(String urlProfile) {
        totalNrOfCreatedImages = 0;
        savedImagesTargetPath = savedImagesFolderName + "\\" + urlProfile;
        createFolder();
        SeleniumWorker seleniumWorker = new SeleniumWorker();
        return seleniumWorker.getDriver();
    }

    private void createFolder() {
        File parentImageFolder = new File(savedImagesFolderName);
        if (!parentImageFolder.exists()) {
            parentImageFolder.mkdirs();
        }

        File profileFolder = new File(savedImagesTargetPath);
        if (!profileFolder.exists()) {
            profileFolder.mkdirs();
        }
    }

    public int scrape(String urlProfile) {
        WebDriver selenium = init(urlProfile);

        String url = siteUrl + urlProfile;
        selenium.get(url);
        addCookie(selenium);
        selenium.navigate().to(url);
        List<String> collectedImageLinks = scrollToBottomAndCollectImageLinks(selenium);
        fetchImages(collectedImageLinks, selenium);
        selenium.close();
        logger.info("Job done:  " + totalNrOfCreatedImages + " images saved");
        return totalNrOfCreatedImages;
    }

    private void fetchImages(List<String> imageParentLinks, WebDriver selenium) {
        List<URL> downloadableLinks;
        downloadableLinks = getDownloadableLinks(imageParentLinks, selenium);
        downloadableLinks.forEach(this::downloadImg);
    }

    private void addCookie(WebDriver selenium) {
        selenium.manage().addCookie(new Cookie("csrftoken", cfg.getCsrftoken()));
        selenium.manage().addCookie(new Cookie("ds_user_id", cfg.getDsuserid()));
        selenium.manage().addCookie(new Cookie("sessionid", cfg.getSessionid()));
    }

    private List<String> scrollToBottomAndCollectImageLinks(WebDriver selenium) {
        logger.info("Collecting links...");
        List<String> collectedImageParentLinks = new ArrayList<>();

        int pageLength = 0;
        while (pageLength != selenium.getPageSource().length()) {
            pageLength = selenium.getPageSource().length();
            ((JavascriptExecutor) selenium).executeScript("window.scrollBy(0,2000)", "");
            Utils.waitMilliseconds(1000L);
            List<WebElement> imageParentElements = selenium.findElements(By.cssSelector("a"));
            collectedImageParentLinks.addAll(getImageParentLinks(imageParentElements));
        }
        return collectedImageParentLinks;
    }

    private List<String> getImageParentLinks(List<WebElement> imageParentElements) {
        return imageParentElements.stream()
                .map(imageParentElement -> imageParentElement.getAttribute("href"))
                .filter(imageParentLink -> imageParentLink.contains("/p/"))
                .collect(Collectors.toList());
    }

    private void downloadImg(URL imgLink) {
        try {
            BufferedImage imgOutput = ImageIO.read(imgLink);
            ImageIO.write(imgOutput, "jpg", new File(generateSavedImageFilePath()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateSavedImageFilePath() {
        totalNrOfCreatedImages++;
        StringBuilder filePathName = new StringBuilder(savedImagesTargetPath);
        filePathName
                .append("\\")
                .append("img")
                .append(totalNrOfCreatedImages < 10 ? "0" : "")
                .append(totalNrOfCreatedImages)
                .append(".jpg");

        return filePathName.toString();
    }

    private List<URL> getDownloadableLinks(List<String> imageParentLinks, WebDriver selenium) {
        List<String> unformattedLinks = imageParentLinks.stream()
                .distinct()
                .map(parentLink -> {
                    selenium.navigate().to(parentLink);
                    String pageSource = selenium.getPageSource();
                    String trimmedLink = pageSource.substring(pageSource.indexOf("display_url") + 14);
                    return trimmedLink.substring(0, trimmedLink.indexOf(",") - 1);
                })
                .collect(Collectors.toList());

        logger.info("Nr of collected links: " + unformattedLinks.size());

        return unformattedLinks.stream()
                .map(this::makeURL)
                .collect(Collectors.toList());
    }

    private URL makeURL(String link) {
        try {
            link = link.substring(link.indexOf("http"), link.length() - 1);
            link = link.replace("\\u0026", "&");
            return new URL(link);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
