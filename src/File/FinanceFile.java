package file;

import finance.Finance;
import service.FinanceService;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FinanceFile {

    private static final String FILE_NAME = "Finance.txt";

    //================ LOAD =================
    public static ArrayList<Finance> load() {
        ArrayList<Finance> list = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return list;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                String financeId = data[0];
                double totalRevenue = Double.parseDouble(data[1]);
                double totalExpense = Double.parseDouble(data[2]);

                Finance finance = new Finance(financeId, totalRevenue, totalExpense);
                list.add(finance);
            }

        } catch (Exception e) {
            System.out.println("Load Finance.txt failed!");
        }

        return list;
    }

    //================ SAVE =================
    public static void save(ArrayList<Finance> list) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (Finance finance : list) {
                bw.write(finance.toString());
                bw.newLine();
            }

        } catch (Exception e) {
            System.out.println("Save Finance.txt failed!");
        }
    }

    //================ MENU QUẢN LÝ TÀI CHÍNH =================
    public static void financeMenu(FinanceService financeService, Scanner sc) {
        // Tự động load dữ liệu từ file lên khi mở menu
        financeService.setFinanceList(load());

        int choose;
        do {
            System.out.println("\n====== FINANCE MANAGEMENT ======");
            System.out.println("1. Display Financial List (In file ra màn hình)");
            System.out.println("2. Create New Financial Period (Tạo kỳ tài chính mới)");
            System.out.println("3. Add Revenue (Cập nhật Doanh thu)");
            System.out.println("4. Add Expense (Cập nhật Chi phí)");
            System.out.println("5. Calculate Profit (Tính lợi nhuận)");
            System.out.println("0. Back");
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
                    System.out.println("\n--- THÔNG TIN TÀI CHÍNH TỪ FILE TEXT ---");
                    financeService.displayAll();
                    break;

                case 2:
                    System.out.print("Enter Finance ID (e.g. T07_2026): ");
                    String id = sc.nextLine();
                    Finance f = new Finance(id, 0, 0); 
                    if (financeService.addFinance(f)) {
                        System.out.println("✅ Tạo kỳ tài chính mới thành công!");
                        save(financeService.getFinanceList()); // Lưu lại file ngay
                    } else {
                        System.out.println("❌ Thất bại: ID này đã tồn tại!");
                    }
                    break;

                case 3:
                    System.out.print("Enter Finance ID: ");
                    String revId = sc.nextLine();
                    System.out.print("Enter Revenue Amount: ");
                    double revAmount = Double.parseDouble(sc.nextLine());
                    if (financeService.addRevenue(revId, revAmount)) {
                        System.out.println("✅ Cộng doanh thu thành công!");
                        save(financeService.getFinanceList()); // Lưu lại file ngay
                    } else {
                        System.out.println("❌ Không tìm thấy mã tài chính vừa nhập!");
                    }
                    break;

                case 4:
                    System.out.print("Enter Finance ID: ");
                    String expId = sc.nextLine();
                    System.out.print("Enter Expense Amount: ");
                    double expAmount = Double.parseDouble(sc.nextLine());
                    if (financeService.addExpense(expId, expAmount)) {
                        System.out.println("✅ Cộng chi phí thành công!");
                        save(financeService.getFinanceList()); // Lưu lại file ngay
                    } else {
                        System.out.println("❌ Không tìm thấy mã tài chính vừa nhập!");
                    }
                    break;

                case 5:
                    System.out.print("Enter Finance ID: ");
                    String profitId = sc.nextLine();
                    if (financeService.isExist(profitId)) {
                        double profit = financeService.calculateProfit(profitId);
                        System.out.println("💰 Lợi nhuận của kỳ [" + profitId + "] là: " + profit);
                    } else {
                        System.out.println("❌ Không tìm thấy mã tài chính!");
                    }
                    break;

                case 0:
                    // Tự động save lại lần cuối trước khi quay lại Main menu
                    save(financeService.getFinanceList());
                    System.out.println("Returning to main menu...");
                    break;
            }
        } while (choose != 0);
    }
}