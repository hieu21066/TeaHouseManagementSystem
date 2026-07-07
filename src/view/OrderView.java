package view;

import order.Invoice;
import order.OrderItem;
import service.OrderService;
import java.util.Scanner;

public class OrderView {

    public static void orderMenu(Scanner sc, OrderService orderService) {
        // Nếu bạn có file lưu trữ cho Order, có thể bỏ comment dòng dưới đây:
        // orderService.setInvoiceList(file.OrderFile.load());

        int choose;
        do {
            System.out.println("\n====== ORDER MANAGEMENT ======");
            System.out.println("1. Display All Invoices (Hóa đơn)");
            System.out.println("2. Create New Invoice (Tạo hóa đơn)");
            System.out.println("3. Update Invoice Info");
            System.out.println("4. Delete Invoice");
            System.out.println("5. Search Invoice By ID");
            System.out.println("6. Sort Invoices By Total Amount");
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
                    System.out.println("\n--- DANH SÁCH HÓA ĐƠN ---");
                    orderService.displayAll();
                    break;
                case 2:
                    handleCreateInvoice(sc, orderService);
                    break;
                case 3:
                    handleUpdateInvoice(sc, orderService);
                    break;
                case 4:
                    handleDeleteInvoice(sc, orderService);
                    break;
                case 5:
                    System.out.print("\nEnter Invoice ID to search: ");
                    String searchId = sc.nextLine();
                    orderService.displayById(searchId);
                    break;
                case 6:
                    orderService.sortByTotalAmount();
                    System.out.println("✅ Đã sắp xếp hóa đơn theo tổng tiền tăng dần!");
                    orderService.displayAll();
                    break;
                case 0:
                    // Lưu file trước khi thoát nếu cần:
                    // file.OrderFile.save(orderService.getInvoiceList());
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("❌ Lựa chọn không hợp lệ!");
            }
        } while (choose != 0);
    }

    // ==================== HÀM BỔ TRỢ NHẬP XUẤT (HELPER METHODS) ====================

    private static void handleCreateInvoice(Scanner sc, OrderService service) {
        System.out.println("\n--- CREATE NEW INVOICE ---");
        System.out.print("Enter Invoice ID: ");
        String id = sc.nextLine();

        if (service.isExist(id)) {
            System.out.println("❌ Lỗi: Mã hóa đơn này đã tồn tại!");
            return;
        }

        System.out.print("Enter Employee Name (Người lập đơn): ");
        String empName = sc.nextLine();

        // Khởi tạo đối tượng invoice bằng constructor 2 tham số hợp lệ của bạn
        Invoice invoice = new Invoice(id, empName);

        String continueAdding = "";
        System.out.println("\n--- Add Items to Invoice ---");
        do {
            System.out.print("Enter Product Name: ");
            String productName = sc.nextLine();
            
            double price;
            try {
                System.out.print("Enter Price: ");
                price = Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Lỗi: Giá tiền không hợp lệ! Món này chưa được thêm.");
                continue;
            }

            int quantity;
            try {
                System.out.print("Enter Quantity: ");
                quantity = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Lỗi: Số lượng không hợp lệ! Món này chưa được thêm.");
                continue;
            }

            // Truyền đúng 3 tham số theo đúng thứ tự Constructor của OrderItem
            OrderItem orderItem = new OrderItem(productName, price, quantity);
            invoice.getItemList().add(orderItem);

            System.out.print("Do you want to add more items? (y/n): ");
            continueAdding = sc.nextLine();
        } while (continueAdding.equalsIgnoreCase("y"));

        if (service.addInvoice(invoice)) {
            System.out.println("✅ Tạo hóa đơn thành công!");
            invoice.printInvoice();
        } else {
            System.out.println("❌ Tạo hóa đơn thất bại!");
        }
    }

    private static void handleUpdateInvoice(Scanner sc, OrderService service) {
        System.out.println("\n--- UPDATE INVOICE ---");
        System.out.print("Enter Invoice ID to update: ");
        String id = sc.nextLine();

        if (!service.isExist(id)) {
            System.out.println("❌ Không tìm thấy hóa đơn cần sửa!");
            return;
        }

        System.out.print("Enter New Employee Name: ");
        String newEmpName = sc.nextLine();

        // Khởi tạo đối tượng invoice mới
        Invoice newInvoice = new Invoice(id, newEmpName);

        String continueAdding = "";
        System.out.println("\n--- Re-enter Items for Invoice ---");
        do {
            System.out.print("Enter Product Name: ");
            String productName = sc.nextLine();
            
            double price;
            try {
                System.out.print("Enter Price: ");
                price = Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Lỗi: Giá tiền không hợp lệ! Bỏ qua món này.");
                continue;
            }

            int quantity;
            try {
                System.out.print("Enter Quantity: ");
                quantity = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Lỗi: Số lượng không hợp lệ! Bỏ qua món này.");
                continue;
            }

            // Truyền đúng 3 tham số theo Constructor của OrderItem
            OrderItem orderItem = new OrderItem(productName, price, quantity);
            newInvoice.getItemList().add(orderItem);

            System.out.print("Add more items? (y/n): ");
            continueAdding = sc.nextLine();
        } while (continueAdding.equalsIgnoreCase("y"));

        if (service.updateInvoice(newInvoice)) {
            System.out.println("✅ Cập nhật hóa đơn thành công!");
        } else {
            System.out.println("❌ Cập nhật thất bại!");
        }
    }

    private static void handleDeleteInvoice(Scanner sc, OrderService service) {
        System.out.print("\nEnter Invoice ID to delete: ");
        String id = sc.nextLine();
        if (service.deleteInvoice(id)) {
            System.out.println("✅ Xóa hóa đơn thành công!");
        } else {
            System.out.println("❌ Không tìm thấy mã hóa đơn này!");
        }
    }
}