import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LinkConvInstagram {

    public List<URL> getDownloadableLinks(List<String> rawLinesOfHtml) {
        return rawLinesOfHtml.stream()
                .filter(item -> item.contains("jpg"))
                .flatMap(item -> Arrays.stream(item.split(",")))
                .filter(item -> item.contains("jpg") && item.contains("e35"))
                .filter(item -> !item.contains("s150") && !item.contains("s240") && !item.contains("s320") && !item.contains("s480") && !item.contains("s640"))
                .distinct()
                .map(this::makeURL)
                .collect(Collectors.toList());
    }

    private URL makeURL(String link) {
        try {
            link = link.substring(link.indexOf("http"), link.length() - 1);
            link = link.replace("\\u0026", "&");
            return new URL(link);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
