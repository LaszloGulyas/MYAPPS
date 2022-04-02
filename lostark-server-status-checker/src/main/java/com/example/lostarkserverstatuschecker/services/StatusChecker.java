package com.example.lostarkserverstatuschecker.services;

import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


@Service
public class StatusChecker {


    public static final String URL_LOSTARK_SERVER_STATUS_PAGE = "https://www.playlostark.com/en-us/support/server-status";
    public static final String SERVER_NAME = "Kadan";


    @Bean
    public void start() throws IOException, InterruptedException {
        String htmlContent = getHtmlCall(URL_LOSTARK_SERVER_STATUS_PAGE);
        String serverStatus = "";
        while (!(serverStatus.equals("busy") || serverStatus.equals("full") || serverStatus.equals("good"))) {
            serverStatus = getStatus(htmlContent, SERVER_NAME);
            System.out.println(getStatusPrintable(htmlContent, SERVER_NAME));
            Thread.sleep(5000);
        }
        System.out.println("SERVER IS UP!!!!");
        playSound();
        Thread.sleep(5000);
    }

    private String getHtmlCall(String url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) (new URL(url)).openConnection();

        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        conn.setRequestMethod("GET");

        JSONObject data = new JSONObject();


        OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
        out.write(String.valueOf(data));

        StringBuilder fetchedHtml = new StringBuilder();

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        for (String line; (line = reader.readLine()) != null; ) {
            fetchedHtml.append(line).append("\n");
        }
        reader.close();
        out.close();

        return fetchedHtml.toString();
    }

    private String getStatusPrintable(String rawHtml, String serverName) {
        StringBuilder resultPrintable = new StringBuilder();

        String serverStatus = getStatus(rawHtml, serverName);

        String processingHtml = rawHtml;
        processingHtml = processingHtml.substring(processingHtml.indexOf("Last updated:"));
        processingHtml = processingHtml.substring(0, processingHtml.indexOf("   ") - 1);
        String timeOfLastUpdate = processingHtml;

        resultPrintable.append(timeOfLastUpdate).append("  ->  ").append("Server name: ").append(serverName)
                .append(": ").append(serverStatus).append("\n");

        return resultPrintable.toString();
    }

    private String getStatus(String rawHtml, String serverName) {
        String processingHtml = rawHtml;
        processingHtml = processingHtml.substring(processingHtml.indexOf(serverName));
        processingHtml = processingHtml.substring(processingHtml.indexOf("server-status--") + 15);
        processingHtml = processingHtml.substring(0, processingHtml.indexOf("\">"));
        return processingHtml;
    }

    private void playSound() {
        try {
            URL resource = StatusChecker.class.getClassLoader().getResource("exit_song.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(resource);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }
}
