public class Main {

    public static void main(String[] args) {
        System.out.println("asdasd");
        Portfolio pf = new Portfolio();
        DBoperations ctrl = new DBoperations(pf);


        System.out.println(pf.listOfBanks());
        System.out.println(pf.sumOfBanks());


        ctrl.updateDB();
    }
}