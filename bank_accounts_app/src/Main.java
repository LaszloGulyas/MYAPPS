public class Main {

    private static Portfolio myPortfolio = new Portfolio("");
    private static Menu menu = new Menu(myPortfolio);

    public static void main(String[] args) {
        menu.firstLogin(myPortfolio);
    }
}
