package order;

import java.util.ArrayList;

public class Invoice {
    // 1. Các thuộc tính ẩn giấu (Encapsulation)
    private String invoiceId;
    private String employeeName;
    private ArrayList<OderItem> itemList;
    private double totalAmount;

    // 2. Constructor không tham số
    public Invoice() {
        this.invoiceId = "";
        this.employeeName = "";
        this.itemList = new ArrayList<>();
        this.totalAmount = 0.0;
    }

    // 3. Constructor có tham số chính (Thường dùng khi bắt đầu tạo một hóa đơn mới)
    public Invoice(String invoiceId, String employeeName) {
        this.invoiceId = invoiceId;
        this.employeeName = employeeName;
        this.itemList = new ArrayList<>();
        this.totalAmount = 0.0;
    }

    // 4. Các hàm Getter và Setter
    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public ArrayList<OderItem> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<OderItem> itemList) {
        this.itemList = itemList;
        // Cập nhật lại tổng tiền nếu danh sách món thay đổi
        updateTotalAmount();
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    // Vì tổng tiền phụ thuộc vào danh sách món, không nên cho phép set tùy tiện từ bên ngoài
    // Thay vào đó ta dùng hàm private để tự động cập nhật nội bộ lớp
    private void updateTotalAmount() {
        this.totalAmount = 0.0;
        for (OderItem item : itemList) {
            this.totalAmount += item.getSubTotal();
        }
    }

    // 5. Hàm nghiệp vụ: Thêm một món mới vào hóa đơn
    public void addItem(String name, double price, int qty) {
        OderItem item = new OderItem(name, price, qty);
        this.itemList.add(item);
        this.totalAmount += item.getSubTotal(); // Cộng dồn trực tiếp vào tổng tiền
    }

    // 6. Hàm hiển thị hóa đơn ra màn hình Console
    public void printInvoice() {
        System.out.println("\n==================================================");
        System.out.println("               TEA REWARD RECEIPT              ");
        System.out.println("Mã Hóa Đơn: " + this.invoiceId + " | On-duty staff: " + this.employeeName);
        System.out.println("--------------------------------------------------");
        for (OderItem item : this.itemList) {
            System.out.printf("- %-25s x%-3d: %10.0f VND\n", 
                    item.getProductName(), item.getQuantity(), item.getSubTotal());
        }
        System.out.println("--------------------------------------------------");
        System.out.printf("TOTAL: %35.0f VND\n", this.totalAmount);
        System.out.println("==================================================");
    }

    // 7. Hàm toString() chuẩn hóa cấu trúc đối tượng
    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceId='" + invoiceId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", itemsCount=" + (itemList != null ? itemList.size() : 0) +
                ", totalAmount=" + totalAmount +
                '}';
    }
}