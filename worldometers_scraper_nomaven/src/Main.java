import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {

        if(args.length<=0) {
            return;
        }

        LinkedList<String> countryData = new LinkedList<>();
        for(int i=0; i<args.length; i++) {
            queryCountry(args[i], countryData);
        }
        printToFile(countryData);

    }

    public static void queryCountry(String country, LinkedList<String> countryData) {
        try {
            String dataSource = "https://www.worldometers.info/coronavirus/country/" + country + "/";
            Document document = Jsoup.connect(dataSource).get();
            Element body = document.body();
            Elements b = body.getElementsByAttributeValue("type", "text/javascript");
//            countryData.add(country);
            countryData.add(getDate(b, country));
            countryData.add(getData(b, "Total Coronavirus Cases"));
            countryData.add(getData(b, "Currently Infected"));
            countryData.add(getData(b, "Total Coronavirus Deaths"));
            countryData.add(getData(b, "Daily Deaths"));
            countryData.add(getData(b, "New Recoveries"));

        } catch (IOException e) {
            e.getMessage();
        }
    }

    public static String getData(Elements b, String chartType) {
        String s = "";
        for(int i=0; i<b.size(); i++) {
            s = b.get(i).toString();
            if(s.contains(chartType)) {
                int dataIndexFrom = s.indexOf("[", s.indexOf("data:", s.indexOf(chartType)));
                int dataIndexTo = s.indexOf("]", dataIndexFrom);
                s = chartType + "," + s.substring(dataIndexFrom+1, dataIndexTo);
                return s;
            }
        }
        return chartType+" FAIL";
    }

    public static String getDate(Elements b, String country) {
        String s = "";
        String chartType = "Total Coronavirus Cases";
        for(int i=0; i<b.size(); i++) {
            s = b.get(i).toString();
            if(s.contains(chartType)) {
                int dataIndexFrom = s.indexOf("[", s.indexOf("categories:", s.indexOf(chartType)));
                int dataIndexTo = s.indexOf("]", dataIndexFrom);
                s = country + "," + s.substring(dataIndexFrom+1, dataIndexTo);
                return s;
            }
        }
        return country;
    }

    public static void  printToFile(LinkedList<String> countryData) {
        try {
            PrintWriter out = new PrintWriter("filename.txt");
            for(int i=0; i<countryData.size(); i++) {
                out.println(countryData.get(i));
            }
            out.close();

        } catch (Exception e) {
            e.getMessage();
        }
    }
}
