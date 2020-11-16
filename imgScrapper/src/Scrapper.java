import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Scrapper {

    private int nrOfCreatedImgLastBatch;
    private String targetPath;

    private Scanner scanner;

    public Scrapper(String targetPath) {
        this.targetPath = targetPath;
    }

    public Scrapper() {
        this("downloadedImgs\\");
    }

    public void run(String htmlBody) {
        List<String> linesOfHtmlBody = new ArrayList<>();
        List<URL> downloadableLinks;
        nrOfCreatedImgLastBatch = 0;

        scanner = new Scanner(Objects.requireNonNull(htmlBody));
        while (scanner.hasNextLine())
            linesOfHtmlBody.add(scanner.nextLine());

        LinkConvInstagram converter = new LinkConvInstagram();
        downloadableLinks = converter.getDownloadableLinks(linesOfHtmlBody);
        downloadableLinks.forEach(this::downloadImg);

        System.out.println("Total downloaded imgs: " + nrOfCreatedImgLastBatch);
    }

    private void downloadImg(URL imgLink) {
        try {
            BufferedImage imgOutput = ImageIO.read(imgLink);
            ImageIO.write(imgOutput, "jpg", new File(generateFilePath()));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private String generateFilePath() {
        nrOfCreatedImgLastBatch++;
        StringBuilder filePathName = new StringBuilder(targetPath);
        filePathName.append("img")
                .append(nrOfCreatedImgLastBatch)
                .append(".jpg");

        return filePathName.toString();
    }

    public String getHtmlBodyJsoup(String link) {
        try {
            Document document = Jsoup.connect(link).get();
            System.out.println("connection: established");
            return document.toString();
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }
}
