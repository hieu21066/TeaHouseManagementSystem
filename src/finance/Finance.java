package finance;

public class Finance {

    //==================== Attributes ====================
    private String financeId;
    private double totalRevenue;
    private double totalExpense;

    //==================== Constructor ====================
    public Finance() {
    }

    public Finance(String financeId, double totalRevenue, double totalExpense) {
        this.financeId = financeId;
        this.totalRevenue = totalRevenue;
        this.totalExpense = totalExpense;
    }

    //==================== Getter & Setter ====================
    public String getFinanceId() {
        return financeId;
    }

    public void setFinanceId(String financeId) {
        this.financeId = financeId;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(double totalExpense) {
        this.totalExpense = totalExpense;
    }

    //==================== Save File ====================
    @Override
    public String toString() {
        return financeId + "|"
                + totalRevenue + "|"
                + totalExpense;
    }

    //==================== Display ====================
    public static void displayHeader() {

        System.out.println("======================================================================");
        System.out.printf("| %-8s | %-15s | %-15s | %-15s |%n",
                "ID", "Revenue", "Expense", "Profit");
        System.out.println("======================================================================");
    }

    public void display() {

        System.out.printf("| %-8s | %-15.0f | %-15.0f | %-15.0f |%n",
                financeId,
                totalRevenue,
                totalExpense,
                (totalRevenue - totalExpense));
    }
}
