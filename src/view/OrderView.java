package view;

import order.Invoice;
import order.OrderItem;
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
                System.out.println("Lỗi: Vui lòng nhập một số nguyên!");
                choose = -1;
                continue;
            }

            switch (choose) {
                case 1:
                    System.out.println("\n--- DANH SÁCH HÓA ĐƠN ---");
                    for (Invoice inv : orderService.getInvoiceList()) {
                        System.out.printf("Mã HD: %s | Nhân viên: %s\n", 
                                inv.getInvoiceId(), inv.getEmployeeName());
                    }
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
                    String searchId = sc.nextLine().trim();
                    boolean found = false;
                    for (Invoice inv : orderService.getInvoiceList()) {
                        if (inv.getInvoiceId().equalsIgnoreCase(searchId)) {
                            printInvoiceTable(inv, orderService.getServiceFee());
                            found = true;
                            break;
                        }
                    }
                    if (!found) System.out.println("❌ Không tìm thấy hóa đơn!");
                    break;
                case 6:
                    orderService.sortByTotalAmount();
                    System.out.println("✅ Đã sắp xếp hóa đơn theo tổng tiền tăng dần!");
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
    String autoInvoiceId = service.generateNextInvoiceId();
    System.out.println("Invoice ID (Auto Generated): " + autoInvoiceId);

    String matchedEmpName = null;
    String empId = "";
    while (matchedEmpName == null) {
        System.out.print("Enter Employee ID (Mã nhân viên): ");
        empId = sc.nextLine().trim();
        matchedEmpName = service.findEmployeeNameById(empId);
        if (matchedEmpName == null) {
            System.out.println("Không tìm thấy nhân viên ứng với ID này! Vui lòng nhập lại.");
        }
    }
    System.out.println("-> Nhân viên lập đơn: " + matchedEmpName);

    String fullEmployeeInfo = matchedEmpName + " - " + empId;
    Invoice invoice = new Invoice(autoInvoiceId, fullEmployeeInfo);
    boolean hasItemBought = false;

    while (true) {
        System.out.println("\n--- DANH SÁCH SẢN PHẨM CÒN HÀNG TRONG KHO ---");
        System.out.printf("%-10s | %-20s | %-15s | %-12s\n", "ID", "Tên Sản Phẩm", "Giá Bán (Đã có lãi)", "Tồn Kho Hiện Tại");
        System.out.println("-------------------------------------------------------------------------");

        int availableCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("ProductCatalog.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] catData = line.split("\\|");
                String id = catData[0];
                if (catData[1].equalsIgnoreCase("Tea") && service.getStorageQuantityInGrams(id) > 0) {
                    
                    // ==================== THAY ĐỔI TẠI ĐÂY ====================
                    // Lấy giá gốc từ file catalog
                    // ĐỔI THÀNH CODE MỚI NÀY:
double basePrice = Double.parseDouble(catData[4]);
double sellingPrice = service.calculateSellingPrice(basePrice);
// Nhân 10 và làm tròn để ra đúng 2,300 VND
int catalogPriceVND = (int) Math.round(sellingPrice * 10);
                    // =========================================================

                    System.out.printf("%-10s | %-20s | %,d VND     | %-12s\n", 
                            id, catData[3], catalogPriceVND, service.getStorageQuantityInGrams(id) + " g");
                    availableCount++;
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi hiển thị danh sách kho!");
        }

        if (availableCount == 0) {
            System.out.println("Hiện không còn loại trà nào trong kho!");
            break;
        }

        System.out.print("\nNhập ID sản phẩm trà muốn chọn: ");
        String prodId = sc.nextLine().trim();

        String[] matchedCatalog = service.findCatalogProduct(prodId);
        if (matchedCatalog == null || !matchedCatalog[1].equalsIgnoreCase("Tea")) {
            System.out.println("Invalid Tea ID");
            continue;
        }

        System.out.print("Nhập số lượng (Grams): ");
        int quantity;
        try {
            quantity = Integer.parseInt(sc.nextLine());
            if (quantity <= 0) continue;
        } catch (Exception e) {
            continue;
        }

        if (service.takeOrderItem(invoice, prodId, quantity)) {
            System.out.println("Đã thêm trà vào đơn thành công.");
            hasItemBought = true;
        }

        System.out.print("Bạn có muốn chọn thêm món không? (y/n): ");
        if (sc.nextLine().trim().equalsIgnoreCase("n")) break;
    }

    if (hasItemBought) {
        invoice.addItem("SERVICE_FEE|Phí dịch vụ thưởng trà", service.getServiceFee(), 1);

        if (service.addInvoice(invoice)) {
            System.out.println("\n✔️ Tạo hóa đơn thành công!");
            printInvoiceTable(invoice, service.getServiceFee()); 
        }
    } else {
        System.out.println("⚠️ Hóa đơn rỗng. Hủy thao tác.");
    } 
}

    // ==================== BẢNG IN HÓA ĐƠN HOÀN CHỈNH BỔ SUNG ID SẢN PHẨM ====================
    // ==================== BẢNG IN HÓA ĐƠN HOÀN CHỈNH BỔ SUNG ID SẢN PHẨM ====================
    private static void printInvoiceTable(Invoice invoice, double serviceFee) {
        System.out.println("\n=========================================================================================");
        System.out.println("                                    TEA HOUSE INVOICE                                    ");
        System.out.println("Invoice ID : " + invoice.getInvoiceId());
        System.out.println("Employee   : " + invoice.getEmployeeName()); 
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.printf("%-10s | %-30s | %-18s | %-10s | %-15s\n", "ID SP", "Tên sản phẩm", "Đơn giá / Gram", "Số lượng", "Thành tiền");
        System.out.println("-----------------------------------------------------------------------------------------");

        int finalTotalInvoiceVND = 0;

        for (OrderItem item : invoice.getItemList()) {
            String rawName = item.getProductName();
            String pId = "";
            String pName = rawName;

            if (rawName.contains("|")) {
                String[] parts = rawName.split("\\|");
                pId = parts[0];
                pName = parts[1];
            }

            String qtyStr = "";
            String priceStr = "";
            int itemPriceFinalVND = 0;
            int rowTotalFinalVND = 0;

            double rowTotal = item.getPrice() * item.getQuantity();
            
            // PHÂN LOẠI ĐỂ TÍNH GIÁ TIỀN HỢP LÝ:
            if (pId.startsWith("TE")) {
    // Nhân 10 và làm tròn bằng Math.round để đồng bộ với danh sách kho
    itemPriceFinalVND = (int) Math.round(item.getPrice() * 10);
    rowTotalFinalVND = (int) Math.round(rowTotal * 10);
    
    priceStr = String.format("%,d VND", itemPriceFinalVND);
    qtyStr = item.getQuantity() + " g"; 
} else {
                // Nếu là SERVICE_FEE hoặc các dịch vụ khác: Giữ nguyên giá trị gốc 
                itemPriceFinalVND = (int) item.getPrice();
                rowTotalFinalVND = (int) rowTotal;
                
                if (pId.equals("SERVICE_FEE")) {
                    pId = ""; // Ẩn chữ SERVICE_FEE ở cột mã sản phẩm cho đẹp mắt
                }
                priceStr = String.format("%,d VND", itemPriceFinalVND);
                qtyStr = String.valueOf(item.getQuantity());
            }

            // Tích lũy vào tổng tiền VND thực tế hiển thị trên hóa đơn
            finalTotalInvoiceVND += rowTotalFinalVND;

            System.out.printf("%-10s | %-30s | %-18s | %-10s | %-15s\n", 
                    pId, pName, priceStr, qtyStr, String.format("%,d VND", rowTotalFinalVND));
        }
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.printf("TOTAL : %,d VND\n", finalTotalInvoiceVND);
        System.out.println("=========================================================================================");
    }

    private static void handleUpdateInvoice(Scanner sc, OrderService service) {
        System.out.print("\nEnter Invoice ID to update: ");
        String id = sc.nextLine().trim();
        if (!service.isExist(id)) {
            System.out.println("❌ Không tìm thấy hóa đơn cần sửa!");
            return;
        }
        System.out.print("Enter New Employee ID: ");
        String newEmpId = sc.nextLine().trim();
        String matchedName = service.findEmployeeNameById(newEmpId);
        if(matchedName == null) {
            System.out.println("❌ Mã nhân viên mới không tồn tại!");
            return;
        }
        
        Invoice newInvoice = new Invoice(id, matchedName + " - " + newEmpId);
        if (service.updateInvoice(newInvoice)) {
            System.out.println("✅ Cập nhật thông tin nhân viên thành công!");
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