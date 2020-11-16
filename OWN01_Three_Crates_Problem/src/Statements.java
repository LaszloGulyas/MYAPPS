public class Statements {

    private int arany;
    private int ezust;
    private int olom;
    private int statementA;
    private int statementB;
    private int statementC;

    private int solutionArany;
    private int solutionEzust;
    private int solutionOlom;
    private int originalA;
    private int originalB;
    private int originalC;

    public int getOriginalA() {
        return originalA;
    }

    public int getOriginalB() {
        return originalB;
    }

    public int getOriginalC() {
        return originalC;
    }


    public String getSolutionArany() {
        return converter(solutionArany);
    }

    public String getSolutionEzust() {
        return converter(solutionEzust);
    }

    public String getSolutionOlom() {
        return converter(solutionOlom);
    }


    public String converter(int content) {
        switch (content) {
            case -1:
                return "Rossz";
            case 0:
                return "Üres";
            case 1:
                return "Jó";
            default:
                System.out.println("ERROR");
                return "-1";
        }
    }

    public void setFinalStatements() {
         originalA = statementA;
         originalB = statementB;
         originalC = statementC;
    }

    public void setFinalChestSolution() {
        solutionArany = arany;
        solutionEzust = ezust;
        solutionOlom = olom;
    }

    public void calculateFinalChestSolution() {
        setStatementCombination(originalA, originalB, originalC);

        if(isStatementCombinationTrue(-1,0,1) == 1) {
            setFinalChestSolution();
        }
        if(isStatementCombinationTrue(-1,1,0) == 1) {
            setFinalChestSolution();
        }
        if(isStatementCombinationTrue(0,-1,1) == 1) {
            setFinalChestSolution();
        }
        if(isStatementCombinationTrue(0,1,-1) == 1) {
            setFinalChestSolution();
        }
        if(isStatementCombinationTrue(1,-1,0) == 1) {
            setFinalChestSolution();
        }
        if(isStatementCombinationTrue(1,0,-1) == 1) {
            setFinalChestSolution();
        }
    }


    public void setChestCombination(int arany, int ezust, int olom) {
        this.arany = arany;
        this.ezust = ezust;
        this.olom = olom;
    }

    public void setStatementCombination(int statementA, int statementB, int statementC) {
        this.statementA = statementA;
        this.statementB = statementB;
        this.statementC = statementC;
    }

    public int isStatementCombinationTrue (int arany, int ezust, int olom) {
        setChestCombination(arany, ezust, olom);
        return isStatementCombinationTrue();
    }

    public int isStatementCombinationTrue () {
        if(isStatementTrue(this.statementA) && isStatementTrue(this.statementB) && isStatementTrue(this.statementC)) {
            return 1;
        } else {
            return 0;
        }
    }

    public boolean isStatementTrue (int index) {

        switch (index) {
            case 1:
                return this.A1(this.arany, this.ezust, this.olom);
            case 2:
                return this.A2(this.arany, this.ezust, this.olom);
            case 3:
                return this.B1(this.arany, this.ezust, this.olom);
            case 4:
                return this.B2(this.arany, this.ezust, this.olom);
            case 5:
                return this.C1(this.arany, this.ezust, this.olom);
            case 6:
                return this.C2(this.arany, this.ezust, this.olom);
            default:
                System.out.println("ERROR");
                return false;
        }
    }

    //arany lada feliratai
    private boolean A1(int arany, int ezust, int olom) {

        if (ezust != -1 && olom == -1) {
            return true;
        } else if (ezust == -1) {
            return true;
        } else {
            return false;
        }
    }

    private boolean A2(int arany, int ezust, int olom) {

        if (olom != 0 && ezust == 0) {
            return true;
        } else if (olom == 0) {
            return true;
        } else {
            return false;
        }
    }


    //ezust lada feliratai
    private boolean B1(int arany, int ezust, int olom) {

        if (ezust == 0 || olom == 1 || arany == -1) {
            return true;
        } else {
            return false;
        }
    }

    private boolean B2(int arany, int ezust, int olom) {

        if (olom != -1 && arany == -1) {
            return true;
        } else if (olom == -1) {
            return true;
        } else {
            return false;
        }
    }


    //olom lada feliratai
    private boolean C1(int arany, int ezust, int olom) {

        if (arany != -1 && ezust == -1) {
            return true;
        } else if (arany == -1) {
            return true;
        } else {
            return false;
        }
    }

    private boolean C2(int arany, int ezust, int olom) {

        if (ezust != 0 && arany == 0) {
            return true;
        } else if (ezust == 0) {
            return true;
        } else {
            return false;
        }
    }
}
