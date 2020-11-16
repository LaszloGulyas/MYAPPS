import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.logging.Level;

public class Main {

    public static void main(String[] args) {

        Scrapper instaScrapper = new Scrapper();

        String baseUrl = "https://www.instagram.com/laca0911/";

      fetchByHtmlUnit(baseUrl);
        //instaScrapper.run(fetchByHtmlUnit(baseUrl));

    }


    public static String fetchBySelenium(String baseUrl) {

        System.setProperty("webdriver.chrome.drive", ".\\drivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.get(baseUrl);

        return null;
    }


    public static String fetchByHtmlUnit(String baseUrl) {
        //        WebClient client = new WebClient();
//        client.getOptions().setJavaScriptEnabled(false);
//        client.getOptions().setCssEnabled(false);
//        client.getOptions().setUseInsecureSSL(true);
//
//        try {
//            HtmlPage page = client.getPage(baseUrl);
//            System.out.println(page.asXml());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http.client").setLevel(Level.OFF);

        // HtmlUnit emulation browser

        try {
            WebClient webClient = new WebClient(BrowserVersion.CHROME);
            webClient.getOptions().setJavaScriptEnabled(true); // Enable JS interpreter, default is true
            webClient.getOptions().setCssEnabled(false); // Disable css support
            webClient.getOptions().setThrowExceptionOnScriptError(false); // Whether to throw an exception when js runs incorrectly
            webClient.getOptions().setUseInsecureSSL(true);

            //webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            //webClient.getOptions().setTimeout(10 * 1000); // Set the connection timeout
            HtmlPage page = webClient.getPage(baseUrl);

            System.out.println(page.getBody().getChildElementCount());
            System.out.println(page.getBody().getFirstChild().getNodeValue());
            System.out.println(page.getBody().getFirstChild().getNodeName());
            System.out.println(page.getBody().getFirstChild().getBaseURI());
            System.out.println(page.getBody().getFirstChild().asText());


            // HtmlElement button = page.getHtml"button.tCibT.qq7_A.z4xUb.w5S7h");
           // System.out.println(button.toString());

            //System.out.println(button.getType());
            //DomElement button = page.getElementById("_7UhW9xLCgtqyrsmh_zdquL8Hvl4b0S");


            //HtmlElement htmlElement = page.getFirstByXPath("//*[@id=\"ctl00_ctl00_ctl00_cph1_cph1_QuickSearchAll1_QuickFlightSearchControl1_btnSearch_Table\"]/tbody/tr/td[2]");
            //webClient.waitForBackgroundJavaScript(1000);


            //webClient.waitForBackgroundJavaScript(30 * 1000); // Wait for js to execute in the background for 30 seconds

            String pageAsXml = page.asXml();
            return pageAsXml;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}