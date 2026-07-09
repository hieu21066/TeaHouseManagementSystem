package view;

import order.Invoice;
import service.OrderService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class OrderView {

    public static void orderMenu(Scanner sc, OrderService orderService) {
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
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("❌ Lựa chọn không hợp lệ!");
            }
        } while (choose != 0);
    }

    private static void handleCreateInvoice(Scanner sc, OrderService service) {
        System.out.println("\n--- CREATE NEW INVOICE ---");
        
        // 1. Tự sinh mã hóa đơn tự động dạng ODxxx
        String autoInvoiceId = service.generateNextInvoiceId();
        System.out.println("Invoice ID (Auto Generated): " + autoInvoiceId);

        System.out.print("Enter Employee Name (Tên người lập đơn): ");
        String empName = sc.nextLine().trim();
        System.out.print("Enter Employee ID (Mã nhân viên): ");
        String empId = sc.nextLine().trim();

        // Định dạng ghép chuỗi theo yêu cầu: vu minh hieu - AD001
        String fullEmployeeInfo = empName + " - " + empId;

        Invoice invoice = new Invoice(autoInvoiceId, fullEmployeeInfo);
        boolean hasItemBought = false;

        while (true) {
            System.out.println("\n--- DANH SÁCH SẢN PHẨM CÒN HÀNG TRONG KHO ---");
            System.out.printf("%-10s | %-20s | %-12s | %-12s\n", "ID", "Tên Sản Phẩm", "Giá Catalog", "Tồn Kho Hiện Tại");
            System.out.println("----------------------------------------------------------------------");

            int availableCount = 0;
            // Đọc trực tiếp cả 2 file txt lên màn hình để kiểm tra số lượng thực
            try (BufferedReader br = new BufferedReader(new FileReader("ProductCatalog.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] catData = line.split("\\|");
                    String id = catData[0];
                    double currentGrams = service.getStorageQuantityInGrams(id, catData);

                    // Chỉ in sản phẩm có lượng tồn kho > 0
                    if (currentGrams > 0) {
                        String unitStr = catData[1].equalsIgnoreCase("Tea") ? " g" : " Cái";
                        System.out.printf("%-10s | %-20s | %-12s | %-12s\n", 
                                id, catData[3], catData[4] + " VND", (int)currentGrams + unitStr);
                        availableCount++;
                    }
                }
            } catch (Exception e) {
                System.out.println("❌ Không thể đọc danh sách menu kho hiện tại!");
            }

            if (availableCount == 0) {
                System.out.println("⚠️ Kho hiện đã hết sạch hàng!");
                break;
            }

            // 2. Nhập ID món hàng
            System.out.print("\nNhập ID sản phẩm muốn chọn (gõ '0' để chốt đơn): ");
            String prodId = sc.nextLine().trim();
            if (prodId.equals("0")) {
                break;
            }

            String[] matchedCatalog = service.findCatalogProduct(prodId);
            if (matchedCatalog == null) {
                System.out.println("❌ Mã sản phẩm không hợp lệ!");
                continue;
            }

            String askLabel = matchedCatalog[1].equalsIgnoreCase("Tea") ? "số lượng (Grams)" : "số lượng (Cái)";
            System.out.print("Nhập " + askLabel + ": ");
            
            int quantity;
            try {
                quantity = Integer.parseInt(sc.nextLine());
                if (quantity <= 0) {
                    System.out.println("❌ Số lượng chọn mua phải lớn hơn 0!");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Vui lòng nhập số nguyên hợp lệ!");
                continue;
            }

            // Đẩy sang dịch vụ xử lý giá và cập nhật trừ kho luôn
            if (service.takeOrderItem(invoice, prodId, quantity)) {
                System.out.println("✅ Thêm thành công.");
                hasItemBought = true;
            }
        }

        // 3. Nếu khách có mua trà, tự động chèn thêm dòng phí dịch vụ cố định vào cuối hóa đơn
        if (hasItemBought) {
            invoice.addItem("Phí dịch vụ thưởng trà", service.getServiceFee(), 1);
            
            if (service.addInvoice(invoice)) {
                System.out.println("\n✔️ Tạo hóa đơn thành công!");
                invoice.printInvoice();
            }
        } else {
            System.out.println("⚠️ Hóa đơn rỗng. Hủy thao tác.");
        }
    }

    private static void handleUpdateInvoice(Scanner sc, OrderService service) {
        System.out.print("\nEnter Invoice ID to update: ");
        String id = sc.nextLine().trim();
        if (!service.isExist(id)) {
            System.out.println("❌ Không tìm thấy hóa đơn cần sửa!");
            return;
        }
        System.out.print("Enter New Employee Name: ");
        String newEmpName = sc.nextLine();
        Invoice newInvoice = new Invoice(id, newEmpName);
        if (service.updateInvoice(newInvoice)) {
            System.out.println("✅ Cập nhật tên nhân viên thành công!");
        }
    }

    private static void handleDeleteInvoice(Scanner sc, OrderService service) {
        System.out.print("\nEnter Invoice ID to delete: ");
        String id = sc.nextLine().trim();
        if (service.deleteInvoice(id)) {
            System.out.println("✅ Xóa hóa đơn thành công!");
        } else {
            System.out.println("❌ Mã hóa đơn không tồn tại!");
        }
    }
}