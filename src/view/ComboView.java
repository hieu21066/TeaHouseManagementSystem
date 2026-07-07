package view;

import combo.Combo;
import service.ComboService;
import java.util.ArrayList;
import java.util.Scanner;

public class ComboView {

    public static void comboMenu(Scanner sc, ComboService comboService) {
        int choose;
        do {
            System.out.println("\n====== COMBO MANAGEMENT ======");
            System.out.println("1. Display All Combos");
            System.out.println("2. Add New Combo");
            System.out.println("3. Update Combo");
            System.out.println("4. Delete Combo");
            System.out.println("5. Search Combo");
            System.out.println("6. Sort Combos");
            System.out.println("0. Back");
            System.out.println("==============================");
            System.out.print("Choose: ");

            try {
                choose = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Lỗi: Vui lòng nhập một số nguyên!");
                choose = -1;
                continue;
            }

            switch (choose) {
                case 1:
                    System.out.println("\n--- DANH SÁCH COMBO ---");
                    comboService.displayAll();
                    break;
                case 2:
                    handleAddCombo(sc, comboService);
                    break;
                case 3:
                    handleUpdateCombo(sc, comboService);
                    break;
                case 4:
                    handleDeleteCombo(sc, comboService);
                    break;
                case 5:
                    handleSearchCombo(sc, comboService);
                    break;
                case 6:
                    handleSortCombo(sc, comboService);
                    break;
                case 0:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("❌ Lựa chọn không hợp lệ!");
            }
        } while (choose != 0);
    }

    // ==================== HÀM BỔ TRỢ NHẬP XUẤT (HELPER METHODS) ====================

    private static void handleAddCombo(Scanner sc, ComboService service) {
        System.out.println("\n--- ADD NEW COMBO ---");
        System.out.print("Enter Combo ID: ");
        String id = sc.nextLine();

        if (service.isExist(id)) {
            System.out.println("❌ Lỗi: Mã Combo này đã tồn tại!");
            return;
        }

        System.out.print("Enter Combo Name: ");
        String name = sc.nextLine();
        
        double price;
        try {
            System.out.print("Enter Price: ");
            price = Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Lỗi: Giá tiền phải là một số!");
            return;
        }

        System.out.print("Enter Description: ");
        String desc = sc.nextLine();

        Combo combo = new Combo(id, name, price, desc);
        if (service.addCombo(combo)) {
            System.out.println("✅ Thêm combo thành công!");
        } else {
            System.out.println("❌ Thêm combo thất bại!");
        }
    }

    private static void handleUpdateCombo(Scanner sc, ComboService service) {
        System.out.println("\n--- UPDATE COMBO ---");
        System.out.print("Enter Combo ID to update: ");
        String id = sc.nextLine();

        if (!service.isExist(id)) {
            System.out.println("❌ Không tìm thấy Combo cần sửa!");
            return;
        }

        System.out.print("Enter New Combo Name: ");
        String name = sc.nextLine();
        
        double price;
        try {
            System.out.print("Enter New Price: ");
            price = Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Lỗi: Giá tiền phải là một số!");
            return;
        }

        System.out.print("Enter New Description: ");
        String desc = sc.nextLine();

        Combo newCombo = new Combo(id, name, price, desc);
        if (service.updateCombo(newCombo)) {
            System.out.println("✅ Cập nhật combo thành công!");
        } else {
            System.out.println("❌ Cập nhật thất bại!");
        }
    }

    private static void handleDeleteCombo(Scanner sc, ComboService service) {
        System.out.print("\nEnter Combo ID to delete: ");
        String id = sc.nextLine();
        if (service.deleteCombo(id)) {
            System.out.println("✅ Xóa combo thành công!");
        } else {
            System.out.println("❌ Không tìm thấy Combo này!");
        }
    }

    private static void handleSearchCombo(Scanner sc, ComboService service) {
        System.out.println("\n--- SEARCH COMBO ---");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Name (Chứa từ khóa)");
        System.out.print("Choose: ");
        int type = Integer.parseInt(sc.nextLine());

        if (type == 1) {
            System.out.print("Enter Combo ID: ");
            String id = sc.nextLine();
            service.displayById(id);
        } else if (type == 2) {
            System.out.print("Enter Name Keyword: ");
            String name = sc.nextLine();
            ArrayList<Combo> result = service.searchByName(name);
            if (result.isEmpty()) {
                System.out.println("❌ Không tìm thấy combo nào phù hợp!");
            } else {
                Combo.displayHeader();
                for (Combo combo : result) {
                    combo.display();
                }
            }
        } else {
            System.out.println("❌ Lựa chọn không hợp lệ!");
        }
    }

    private static void handleSortCombo(Scanner sc, ComboService service) {
        System.out.println("\n--- SORT OPTIONS ---");
        System.out.println("1. Sort by Price Ascending (Giá tăng dần)");
        System.out.println("2. Sort by Price Descending (Giá giảm dần)");
        System.out.println("3. Sort by Name (A-Z)");
        System.out.print("Choose: ");
        int sortType = Integer.parseInt(sc.nextLine());

        switch (sortType) {
            case 1:
                service.sortByPriceAscending();
                System.out.println("✅ Đã sắp xếp theo giá tăng dần!");
                service.displayAll();
                break;
            case 2:
                service.sortByPriceDescending();
                System.out.println("✅ Đã sắp xếp theo giá giảm dần!");
                service.displayAll();
                break;
            case 3:
                service.sortByName();
                System.out.println("✅ Đã sắp xếp theo tên từ A-Z!");
                service.displayAll();
                break;
            default:
                System.out.println("❌ Lựa chọn sai!");
        }
    }
}