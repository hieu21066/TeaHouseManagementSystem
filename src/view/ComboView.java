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
            System.out.println("7. Order Combo By Name (New)"); // Thêm lựa chọn 7 tại đây
            System.out.println("0. Back");
            System.out.println("==============================");
            System.out.print("Choose: ");

            try {
                choose = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số nguyên!");
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
                case 7:
                    // Gọi hàm Order Combo trực tiếp từ Menu quản lý
                    orderByComboName(sc, comboService);
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
        System.out.println("\n===== ADD NEW COMBO =====");
        String teaType = chooseTeaType(sc);
        String id = service.generateComboId(teaType);
        System.out.println("Generated ID : " + id);

        System.out.print("Enter Combo Name: ");
        String name = sc.nextLine();

        double price;
        while (true) {
            try {
                System.out.print("Enter Price: ");
                price = Double.parseDouble(sc.nextLine());
                if (price < 0) {
                    System.out.println("Price must be >= 0");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Invalid Price!");
            }
        }

        System.out.print("Enter Description: ");
        String des = sc.nextLine();

        Combo combo = new Combo(id, teaType, name, price, des);

        if (service.addCombo(combo)) {
            System.out.println("Add successfully!");
        } else {
            System.out.println("Add failed!");
        }
    }

    private static void handleUpdateCombo(Scanner sc, ComboService service) {
        System.out.println("\n===== UPDATE COMBO =====");
        System.out.print("Enter Combo ID: ");
        String id = sc.nextLine().toUpperCase();

        Combo oldCombo = service.searchById(id);
        if (oldCombo == null) {
            System.out.println("Combo not found!");
            return;
        }

        System.out.print("New Combo Name: ");
        String name = sc.nextLine();

        double price;
        while (true) {
            try {
                System.out.print("New Price: ");
                price = Double.parseDouble(sc.nextLine());
                if (price < 0) {
                    System.out.println("Price must be >=0");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Invalid Price!");
            }
        }

        System.out.print("New Description: ");
        String des = sc.nextLine();

        Combo combo = new Combo(id, oldCombo.getTeaType(), name, price, des);

        if (service.updateCombo(combo)) {
            System.out.println("Update successfully!");
        } else {
            System.out.println("Update failed!");
        }
    }

    private static void handleDeleteCombo(Scanner sc, ComboService service) {
        System.out.print("\nEnter Combo ID to delete: ");
        String id = sc.nextLine().trim().toUpperCase();

        if (service.deleteCombo(id)) {
            System.out.println("Delete successfully!");
        } else {
            System.out.println("Combo not found!");
        }
    }

    private static void handleSearchCombo(Scanner sc, ComboService service) {
        System.out.println("\n===== SEARCH COMBO =====");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Name");
        System.out.println("3. Search by Tea Type");
        System.out.print("Choose: ");

        int type;
        try {
            type = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid choice!");
            return;
        }

        switch (type) {
            case 1:
                System.out.print("Enter Combo ID: ");
                String id = sc.nextLine().trim().toUpperCase();
                service.displayById(id);
                break;
            case 2:
                System.out.print("Enter Combo Name: ");
                String name = sc.nextLine();
                ArrayList<Combo> result = service.searchByName(name);
                if (result.isEmpty()) {
                    System.out.println("No combo found!");
                } else {
                    Combo.displayHeader();
                    for (Combo combo : result) {
                        combo.display();
                    }
                }
                break;
            case 3:
                String teaType = chooseTeaType(sc);
                result = service.searchByTeaType(teaType);
                if (result.isEmpty()) {
                    System.out.println("No combo found!");
                } else {
                    Combo.displayHeader();
                    for (Combo combo : result) {
                        combo.display();
                    }
                }
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private static void handleSortCombo(Scanner sc, ComboService service) {
        System.out.println("\n===== SORT COMBO =====");
        System.out.println("1. Sort by Price Ascending");
        System.out.println("2. Sort by Price Descending");
        System.out.println("3. Sort by Name");
        System.out.print("Choose: ");

        int sortType;
        try {
            sortType = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid choice!");
            return;
        }

        switch (sortType) {
            case 1:
                service.sortByPriceAscending();
                service.save();
                System.out.println("Sorted successfully!");
                service.displayAll();
                break;
            case 2:
                service.sortByPriceDescending();
                service.save();
                System.out.println("Sorted successfully!");
                service.displayAll();
                break;
            case 3:
                service.sortByName();
                service.save();
                System.out.println("Sorted successfully!");
                service.displayAll();
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private static String chooseTeaType(Scanner sc) {
        while (true) {
            System.out.println("\n====== CHOOSE TEA TYPE ======");
            System.out.println("1. Green Tea");
            System.out.println("2. Black Tea");
            System.out.println("3. White Tea");
            System.out.println("4. Yellow Tea");
            System.out.println("5. Oolong Tea");
            System.out.println("6. Oriental Beauty");
            System.out.println("7. Tie Guan Yin");
            System.out.println("8. Raw Pu-erh");
            System.out.println("9. Ripe Pu-erh");
            System.out.print("Choose: ");

            switch (sc.nextLine()) {
                case "1": return "GT";
                case "2": return "RT";
                case "3": return "BT";
                case "4": return "YT";
                case "5": return "OT";
                case "6": return "OM";
                case "7": return "TQ";
                case "8": return "PS";
                case "9": return "PR";
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // ====================================================================
    // THÊM MỚI: Tự động xử lý Order Combo & Tự sinh mã hóa đơn bên trong hàm
    // ====================================================================
   // ====================================================================
    // THÊM MỚI: Tự động xử lý Order Combo & Tự sinh mã hóa đơn bên trong hàm
    // ====================================================================
   public static void orderByComboName(Scanner sc, ComboService comboService) {
        System.out.println("\n--- ORDER COMBO THEO TÊN HOẶC MÃ TRÀ ---");
        System.out.print("Nhập tên combo hoặc mã loại trà muốn mua (Ví dụ: Green dragon, RT, GT...): ");
        String searchInput = sc.nextLine().trim();
        
        // Tự động tìm kiếm linh hoạt: nếu nhập 2 chữ cái viết hoa thì tìm theo Tea Type, ngược lại tìm theo Tên
        ArrayList<Combo> matchedList;
        if (searchInput.length() == 2 && searchInput.equals(searchInput.toUpperCase())) {
            matchedList = comboService.searchByTeaType(searchInput);
        } else {
            matchedList = comboService.searchByName(searchInput);
        }

        if (matchedList.isEmpty()) {
            System.out.println("❌ Không tìm thấy gói Combo nào phù hợp với từ khóa trên!");
            return;
        }

        System.out.println("\n--- KẾT QUẢ TÌM KIẾM ---");
        for (int i = 0; i < matchedList.size(); i++) {
            Combo c = matchedList.get(i);
            System.out.printf("[%d] Mã: %s | Tên: %s | Giá: %,d VND\n", 
                (i + 1), c.getComboId(), c.getComboName(), (int)c.getPrice());
        }

        int choice = -1;
        // VÒNG LẶP CHỐNG GÕ SAI: Gõ sai số thứ tự sẽ bắt nhập lại thay vì tự hủy
        while (true) {
            System.out.print("\nChọn số thứ tự Combo khách mua (Nhập 0 để hủy): ");
            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice == 0) {
                    System.out.println("↩️ Đã hủy thao tác Order Combo.");
                    return;
                }
                if (choice > 0 && choice <= matchedList.size()) {
                    break; // Nhập đúng số thứ tự trong danh sách -> Thoát vòng lặp để xử lý tiếp
                }
                System.out.printf("❌ Số thứ tự không hợp lệ! Vui lòng chọn từ 1 đến %d.\n", matchedList.size());
            } catch (NumberFormatException e) {
                System.out.println("❌ Vui lòng nhập một số nguyên hợp lệ!");
            }
        }

        Combo selectedCombo = matchedList.get(choice - 1);
        int quantity = 0;
        
        // VÒNG LẶP CHỐNG NHẬP SAI SỐ LƯỢNG
        while (true) {
            System.out.print("Nhập số lượng gói combo: ");
            try {
                quantity = Integer.parseInt(sc.nextLine());
                if (quantity <= 0) {
                    System.out.println("❌ Số lượng mua phải lớn hơn 0!");
                    continue;
                }
                break; // Số lượng hợp lệ -> Thoát vòng lặp
            } catch (NumberFormatException e) {
                System.out.println("❌ Số lượng phải là số nguyên!");
            }
        }

        // TỰ ĐỘNG SINH MÃ HÓA ĐƠN
        String financeId = "CB" + (System.currentTimeMillis() % 10000);

        // GỌI LỆNH LƯU VÀO FILE (Đã nhân với 100.0 để ghi nhận đúng giá gốc chưa chia vào combopay.txt)
        file.ComboFile.saveComboPay(
            financeId, 
            selectedCombo.getComboId(), 
            selectedCombo.getComboName(), 
            quantity, 
            selectedCombo.getPrice() * 100.0
        );

        System.out.println("\n✅ Đã ghi nhận giao dịch mua Combo vào file combopay.txt thành công!");
        System.out.printf("👉 Mã giao dịch: %s\n", financeId);
        System.out.printf("👉 Khách mua: %s | Số lượng: %d | Tổng tiền: %,d VND\n", 
            selectedCombo.getComboName(), quantity, (int)(selectedCombo.getPrice() * quantity));
    }
}