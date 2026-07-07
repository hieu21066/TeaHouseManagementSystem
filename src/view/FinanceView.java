package view;

import finance.Finance;
import service.FinanceService;
import java.util.Scanner;

public class FinanceView {

    public static void displayFinancialReport(Scanner sc, FinanceService financeService) {
        // Load dữ liệu từ file lên Service trước khi tương tác
        financeService.setFinanceList(file.FinanceFile.load());
        
        int choose;
        do {
            System.out.println("\n====== FINANCE MANAGEMENT ======");
            System.out.println("1. Display Financial List");
            System.out.println("2. Create New Financial Period");
            System.out.println("3. Add Revenue");
            System.out.println("4. Add Expense");
            System.out.println("5. Calculate Profit");
            System.out.println("0. Back");
            System.out.println("================================");
            System.out.print("Choose: ");
            
            try {
                choose = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Lỗi: Vui lòng nhập số nguyên!");
                choose = -1; 
                continue;
            }

            switch (choose) {
                case 1: 
                    System.out.println("\n--- THÔNG TIN TÀI CHÍNH ---"); 
                    financeService.displayAll(); 
                    break;
                case 2: 
                    handleCreateFinance(sc, financeService); 
                    break;
                case 3: 
                    handleAddRevenue(sc, financeService); 
                    break;
                case 4: 
                    handleAddExpense(sc, financeService); 
                    break;
                case 5: 
                    handleCalculateProfit(sc, financeService); 
                    break;
                case 0:
                    // Lưu dữ liệu vào file trước khi thoát ra menu chính
                    file.FinanceFile.save(financeService.getFinanceList());
                    System.out.println("Returning to main menu..."); 
                    break;
            }
        } while (choose != 0);
    }

    // ==================== HELPER METHODS (HÀM BỔ TRỢ) ====================

    private static void handleCreateFinance(Scanner sc, FinanceService financeService) {
        System.out.print("Enter Finance ID (e.g. T07_2026): ");
        String id = sc.nextLine();
        if (financeService.addFinance(new Finance(id, 0, 0))) {
            System.out.println("✅ Tạo kỳ tài chính mới thành công!");
            file.FinanceFile.save(financeService.getFinanceList());
        } else {
            System.out.println("❌ Thất bại: ID này đã tồn tại!");
        }
    }

    private static void handleAddRevenue(Scanner sc, FinanceService financeService) {
        System.out.print("Enter Finance ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Revenue Amount: ");
        double amount = Double.parseDouble(sc.nextLine());
        if (financeService.addRevenue(id, amount)) {
            System.out.println("✅ Cộng doanh thu thành công!");
            file.FinanceFile.save(financeService.getFinanceList());
        } else {
            System.out.println("❌ Không tìm thấy mã tài chính!");
        }
    }

    private static void handleAddExpense(Scanner sc, FinanceService financeService) {
        System.out.print("Enter Finance ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Expense Amount: ");
        double amount = Double.parseDouble(sc.nextLine());
        if (financeService.addExpense(id, amount)) {
            System.out.println("✅ Cộng chi phí thành công!");
            file.FinanceFile.save(financeService.getFinanceList());
        } else {
            System.out.println("❌ Không tìm thấy mã tài chính!");
        }
    }

    private static void handleCalculateProfit(Scanner sc, FinanceService financeService) {
        System.out.print("Enter Finance ID: ");
        String id = sc.nextLine();
        if (financeService.isExist(id)) {
            System.out.println("💰 Lợi nhuận của kỳ [" + id + "] là: " + financeService.calculateProfit(id));
        } else {
            System.out.println("❌ Không tìm thấy mã tài chính!");
        }
    }
}