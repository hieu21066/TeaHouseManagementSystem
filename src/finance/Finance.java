package finance;

public class Finance {
    private double totalRevenue = 0;
    private double totalExpense = 0;

    public void addRevenue(double amount) { this.totalRevenue += amount; }
    public void addExpense(double amount) { this.totalExpense += amount; }

    public void displayFinancialReport() {
        System.out.println("\n====== TEA SHOP FINANCIAL REPORT ======");
        System.out.printf(" Total revenue received: %,15.0f VND\n", totalRevenue);
        System.out.printf(" Total cost:    %,15.0f VND\n", totalExpense);
        System.out.printf(" PROFIT REVENUE:        %,15.0f VND\n", (totalRevenue - totalExpense));
        System.out.println("========================================");
    }
}