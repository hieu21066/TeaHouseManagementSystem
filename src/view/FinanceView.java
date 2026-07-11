package view;

import finance.Finance;
import service.FinanceService;
import service.OrderService;
import service.ProductService;
import service.ComboService;
import java.util.Scanner;

public class FinanceView {

    public static void displayFinancialReport(Scanner sc, FinanceService financeService, OrderService orderService, ProductService productService, ComboService comboService) {
        financeService.setFinanceList(file.FinanceFile.load());
        
        int choose;
        do {
            System.out.println("\n====== FINANCE MANAGEMENT ======");
            System.out.println("1. Display Financial List");
            System.out.println("2. Create New Financial Period");
            System.out.println("3. Add Revenue (Manual)");
            System.out.println("4. Add Expense (Manual)");
            System.out.println("5. Auto Calculate Profit from Files 🔄");
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
                case 2: handleCreateFinance(sc, financeService); break;
                case 3: handleAddRevenue(sc, financeService); break;
                case 4: handleAddExpense(sc, financeService); break;
                case 5: 
                    handleAutoCalculate(sc, financeService, orderService, productService, comboService); 
                    break;
                case 0:
                    file.FinanceFile.save(financeService.getFinanceList());
                    System.out.println("Returning to main menu..."); 
                    break;
            }
        } while (choose != 0);
    }

    private static void handleCreateFinance(Scanner sc, FinanceService financeService) {
        System.out.print("Enter Finance ID (e.g. F2026_01): ");
        String id = sc.nextLine().trim();
        if (financeService.addFinance(new Finance(id, 0, 0))) {
            System.out.println("✅ Tạo kỳ tài chính mới thành công!");
            file.FinanceFile.save(financeService.getFinanceList());
        } else {
            System.out.println("❌ Thất bại: ID này đã tồn tại!");
        }
    }

    private static void handleAddRevenue(Scanner sc, FinanceService financeService) {
        System.out.print("Enter Finance ID: ");
        String id = sc.nextLine().trim();
        System.out.print("Enter Revenue Amount: ");
        try {
            double amount = Double.parseDouble(sc.nextLine());
            if (financeService.addRevenue(id, amount)) {
                System.out.println("✅ Cộng doanh thu thành công!");
                file.FinanceFile.save(financeService.getFinanceList());
            } else {
                System.out.println("❌ Không tìm thấy mã tài chính!");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Lỗi: Số tiền không hợp lệ!");
        }
    }

    private static void handleAddExpense(Scanner sc, FinanceService financeService) {
        System.out.print("Enter Finance ID: ");
        String id = sc.nextLine().trim();
        System.out.print("Enter Expense Amount: ");
        try {
            double amount = Double.parseDouble(sc.nextLine());
            if (financeService.addExpense(id, amount)) {
                System.out.println("✅ Cộng chi phí thành công!");
                file.FinanceFile.save(financeService.getFinanceList());
            } else {
                System.out.println("❌ Không tìm thấy mã tài chính!");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Lỗi: Số tiền không hợp lệ!");
        }
    }

    private static void handleAutoCalculate(Scanner sc, FinanceService financeService, OrderService orderService, ProductService productService, ComboService comboService) {
        System.out.print("Enter Finance ID to sync: ");
        String id = sc.nextLine().trim();

        System.out.println("⏳ Đang quét dữ liệu trực tiếp từ các file hệ thống (Hóa đơn, Kho hàng, Combo)...");
        
        if (financeService.autoCalculateFinance(id, orderService, productService, comboService)) {
            System.out.println("✅ ĐỒNG BỘ HÓA VÀ CẬP NHẬT FILE THÀNH CÔNG!");
            
            System.out.println("\n--- BÁO CÁO TỰ ĐỘNG KỲ [" + id + "] ---");
            financeService.displayById(id);
            
            // Lưu dữ liệu mới xuống file Finance.txt ngay tức khắc
            file.FinanceFile.save(financeService.getFinanceList());
        } else {
            System.out.println("❌ Thất bại: Không tìm thấy mã tài chính!");
        }
    }
}