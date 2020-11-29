import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class Downloader {

    public static final String TEST_URL = "https://opradre.com/wp-content/uploads/2020/07/The-Weeknd-Blinding-Lights_OpraDre.com_.mp3";

    public void download(String link) throws MalformedURLException {

        URL downloadURL = new URL(link);
//        URL downloadURL = new URL(TEST_URL); //-------------test

        System.out.println("Try to download...");
        System.out.println("Expected file size: " + getFileSize(downloadURL));

        try (ReadableByteChannel readableByteChannel = Channels.newChannel(downloadURL.openStream())) {
            String fileName = getFileName(downloadURL.toString());
            if (fileName == null) {
                throw new IllegalArgumentException("Invalid link");
            }

            try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
                FileChannel fileChannel = fileOutputStream.getChannel();;
                fileOutputStream.getChannel()
                        .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
                System.out.println(fileChannel.size());
                fileOutputStream.close();
                System.out.println("Downloading finished");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getFileName(String fullLink) {
        //String fileName = fullLink.substring(fullLink.lastIndexOf('/') + 1);
        String fileName = "proba.mp4";
        return fileName;
    }

    private int getFileSize(URL url) {
        URLConnection conn = null;
        try {
            conn = url.openConnection();
            if (conn instanceof HttpURLConnection) {
                ((HttpURLConnection) conn).setRequestMethod("HEAD");
            }
            conn.getInputStream();
            return conn.getContentLength();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn instanceof HttpURLConnection) {
                ((HttpURLConnection) conn).disconnect();
            }
        }
    }
}
