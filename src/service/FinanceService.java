package service;

import finance.Finance;
import java.util.ArrayList;
import java.util.Comparator;

public class FinanceService {

    //==================== Attribute ====================

    private ArrayList<Finance> financeList;

    //==================== Constructor ====================

    public FinanceService() {
        financeList = new ArrayList<>();
    }

    //==================== Getter & Setter ====================

    public ArrayList<Finance> getFinanceList() {
        return financeList;
    }

    public void setFinanceList(ArrayList<Finance> financeList) {
        this.financeList = financeList;
    }

    //==================== ADD ====================

    public boolean addFinance(Finance finance) {

        if (isExist(finance.getFinanceId())) {
            return false;
        }

        financeList.add(finance);
        return true;
    }

    //==================== DELETE ====================

    public boolean deleteFinance(String financeId) {

        Finance finance = searchById(financeId);

        if (finance == null) {
            return false;
        }

        financeList.remove(finance);
        return true;
    }

    //==================== UPDATE ====================

    public boolean updateFinance(Finance newFinance) {

        Finance oldFinance = searchById(newFinance.getFinanceId());

        if (oldFinance == null) {
            return false;
        }

        oldFinance.setTotalRevenue(newFinance.getTotalRevenue());
        oldFinance.setTotalExpense(newFinance.getTotalExpense());

        return true;
    }

    //==================== SEARCH ====================

    public Finance searchById(String financeId) {

        for (Finance finance : financeList) {

            if (finance.getFinanceId().equalsIgnoreCase(financeId)) {
                return finance;
            }

        }

        return null;
    }

    //==================== DISPLAY ====================

    public void displayAll() {

        if (financeList.isEmpty()) {
            System.out.println("Finance list is empty!");
            return;
        }

        Finance.displayHeader();

        for (Finance finance : financeList) {
            finance.display();
        }
    }

    public void displayById(String financeId) {

        Finance finance = searchById(financeId);

        if (finance == null) {
            System.out.println("Finance not found!");
            return;
        }

        Finance.displayHeader();
        finance.display();
    }

    //==================== REVENUE ====================

    public boolean addRevenue(String financeId, double amount) {

        Finance finance = searchById(financeId);

        if (finance == null) {
            return false;
        }

        finance.setTotalRevenue(finance.getTotalRevenue() + amount);
        return true;
    }

    //==================== EXPENSE ====================

    public boolean addExpense(String financeId, double amount) {

        Finance finance = searchById(financeId);

        if (finance == null) {
            return false;
        }

        finance.setTotalExpense(finance.getTotalExpense() + amount);
        return true;
    }

    //==================== PROFIT ====================

    public double calculateProfit(String financeId) {

        Finance finance = searchById(financeId);

        if (finance == null) {
            return 0;
        }

        return finance.getTotalRevenue() - finance.getTotalExpense();
    }

    //==================== SORT ====================

    public void sortByRevenue() {

        financeList.sort(
                Comparator.comparingDouble(Finance::getTotalRevenue)
        );
    }

    public void sortByExpense() {

        financeList.sort(
                Comparator.comparingDouble(Finance::getTotalExpense)
        );
    }

    //==================== CHECK ====================

    public boolean isExist(String financeId) {

        return searchById(financeId) != null;
    }

    public int countFinance() {

        return financeList.size();
    }

    public void clearAll() {

        financeList.clear();
    }

    public void displayFinancialReport(java.util.Scanner sc) {
        this.setFinanceList(file.FinanceFile.load());
        int choose;
        do {
            System.out.println("\n====== FINANCE MANAGEMENT ======\n1. Display Financial List\n2. Create New Financial Period\n3. Add Revenue\n4. Add Expense\n5. Calculate Profit\n0. Back\n================================");
            System.out.print("Choose: ");
            try {
                choose = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Lỗi: Vui lòng nhập số nguyên!");
                choose = -1; continue;
            }

            switch (choose) {
                case 1: System.out.println("\n--- THÔNG TIN TÀI CHÍNH ---"); this.displayAll(); break;
                case 2: handleCreateFinance(sc); break;
                case 3: handleAddRevenue(sc); break;
                case 4: handleAddExpense(sc); break;
                case 5: handleCalculateProfit(sc); break;
                case 0:
                    file.FinanceFile.save(this.getFinanceList());
                    System.out.println("Returning to main menu..."); break;
            }
        } while (choose != 0);
    }

    // ==================== HELPER METHODS (HÀM BỔ TRỢ) ====================

    private void handleCreateFinance(java.util.Scanner sc) {
        System.out.print("Enter Finance ID (e.g. T07_2026): ");
        String id = sc.nextLine();
        if (this.addFinance(new Finance(id, 0, 0))) {
            System.out.println("✅ Tạo kỳ tài chính mới thành công!");
            file.FinanceFile.save(this.getFinanceList());
        } else {
            System.out.println("❌ Thất bại: ID này đã tồn tại!");
        }
    }

    private void handleAddRevenue(java.util.Scanner sc) {
        System.out.print("Enter Finance ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Revenue Amount: ");
        double amount = Double.parseDouble(sc.nextLine());
        if (this.addRevenue(id, amount)) {
            System.out.println("✅ Cộng doanh thu thành công!");
            file.FinanceFile.save(this.getFinanceList());
        } else {
            System.out.println("❌ Không tìm thấy mã tài chính!");
        }
    }

    private void handleAddExpense(java.util.Scanner sc) {
        System.out.print("Enter Finance ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Expense Amount: ");
        double amount = Double.parseDouble(sc.nextLine());
        if (this.addExpense(id, amount)) {
            System.out.println("✅ Cộng chi phí thành công!");
            file.FinanceFile.save(this.getFinanceList());
        } else {
            System.out.println("❌ Không tìm thấy mã tài chính!");
        }
    }

    private void handleCalculateProfit(java.util.Scanner sc) {
        System.out.print("Enter Finance ID: ");
        String id = sc.nextLine();
        if (this.isExist(id)) {
            System.out.println("💰 Lợi nhuận của kỳ [" + id + "] là: " + this.calculateProfit(id));
        } else {
            System.out.println("❌ Không tìm thấy mã tài chính!");
        }
    }
    
}