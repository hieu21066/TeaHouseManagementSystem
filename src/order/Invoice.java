package order;

import java.util.ArrayList;

public class Invoice {

    //==================== Attributes ====================
    private String invoiceId;
    private String employeeName;
    private ArrayList<OrderItem> itemList;
    private double totalAmount;

    //==================== Constructor ====================
    public Invoice() {
        invoiceId = "";
        employeeName = "";
        itemList = new ArrayList<>();
        totalAmount = 0;
    }

    public Invoice(String invoiceId, String employeeName) {
        this.invoiceId = invoiceId;
        this.employeeName = employeeName;
        this.itemList = new ArrayList<>();
        this.totalAmount = 0;
    }

    //==================== Getter ====================
    public String getInvoiceId() {
        return invoiceId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public ArrayList<OrderItem> getItemList() {
        return itemList;
    }

    public double getTotalAmount() {
        updateTotalAmount();
        return totalAmount;
    }

    //==================== Setter ====================
    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setItemList(ArrayList<OrderItem> itemList) {
        this.itemList = itemList;
        updateTotalAmount();
    }

    //==================== Business ====================
    private void updateTotalAmount() {
        totalAmount = 0;

        for (OrderItem item : itemList) {
            String rawName = item.getProductName();
            double rowTotal = item.getSubTotal(); // subTotal = price * quantity

            // Kiểm tra xem đây có phải là sản phẩm Trà hay không (chứa mã TE)
            if (rawName.startsWith("TE") || rawName.contains("|TE")) {
                // Nếu là Trà: Tự quy đổi sang VND bằng cách nhân 10 và làm tròn triệt để
                totalAmount += Math.round(rowTotal * 10);
            } else {
                // Nếu là dịch vụ thưởng trà hoặc "Doanh thu tích lũy" từ file đọc lên: Giữ nguyên
                totalAmount += rowTotal;
            }
        }
    }

    public void addItem(String name, double price, int qty) {
        OrderItem item = new OrderItem(name, price, qty);
        itemList.add(item);
        updateTotalAmount();
    }

    public void removeItem(int index) {
        if (index >= 0 && index < itemList.size()) {
            itemList.remove(index);
            updateTotalAmount();
        }
    }

    //==================== Display ====================
    public void printInvoice() {

        updateTotalAmount();

        System.out.println("\n======================================================");
        System.out.println("                 TEA HOUSE INVOICE");
        System.out.println("Invoice ID : " + invoiceId);
        System.out.println("Employee   : " + employeeName);
        System.out.println("------------------------------------------------------");

        for (OrderItem item : itemList) {
            System.out.printf("%-25s x%-3d %10.0f VND\n",
                    item.getProductName(),
                    item.getQuantity(),
                    item.getSubTotal());
        }

        System.out.println("------------------------------------------------------");
        System.out.printf("TOTAL : %.0f VND\n", totalAmount);
        System.out.println("======================================================");
    }

    //==================== Save File ====================
    @Override
    public String toString() {
        return invoiceId + "|"
                + employeeName + "|"
                + totalAmount;
    }
}
