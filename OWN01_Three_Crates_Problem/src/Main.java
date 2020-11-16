public class Main {

    public static void main(String[] args) {

        solver();
        //proba comment
    }

    public static void solver() {

        Statements statements = new Statements();

        System.out.println("Lehetséges megoldások száma minden létező felirat kombinációra: ");

        System.out.print(tryAllChest(statements, 1,3,5) + "; ");
        System.out.print(tryAllChest(statements, 1,4,5) + "; ");
        System.out.print(tryAllChest(statements, 1,3,6) + "; ");
        System.out.print(tryAllChest(statements, 1,4,6) + "; ");


        System.out.print(tryAllChest(statements, 2,3,5) + "; ");
        System.out.print(tryAllChest(statements, 2,4,5) + "; ");
        System.out.print(tryAllChest(statements, 2,3,6) + "; ");
        System.out.print(tryAllChest(statements, 2,4,6) + "; ");
        System.out.println();
        System.out.println();


        System.out.println("Eredeti feliratok a ládákon: ");
        System.out.println("Arany láda: " + statements.getOriginalA());
        System.out.println("Ezüst láda: " + statements.getOriginalB());
        System.out.println("Ólom láda: " + statements.getOriginalC());
        System.out.println();

        statements.calculateFinalChestSolution();

        System.out.println("A ládák tartalma: ");
        System.out.println("Arany láda: " + statements.getSolutionArany());
        System.out.println("Ezüst láda: " + statements.getSolutionEzust());
        System.out.println("Ólom láda: " + statements.getSolutionOlom());
    }

    public static int tryAllChest(Statements statements, int statementA, int statementB, int statementC) {

        statements.setStatementCombination(statementA, statementB, statementC);
        int counter = 0;

        counter += statements.isStatementCombinationTrue(-1,0,1);
        counter += statements.isStatementCombinationTrue(-1,1,0);
        counter += statements.isStatementCombinationTrue(0,-1,1);
        counter += statements.isStatementCombinationTrue(0,1,-1);
        counter += statements.isStatementCombinationTrue(1,-1,0);
        counter += statements.isStatementCombinationTrue(1,0,-1);

        if(counter == 1) {
            statements.setFinalStatements();
        }

        return counter;
    }
}
