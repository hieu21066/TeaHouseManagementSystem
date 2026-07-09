package file;

import order.Invoice;
import order.OrderItem;
import java.io.*;
import java.util.ArrayList;

public class OrderFile {
    private static final String FILE_NAME = "Order.txt";

    // ==================== LOAD ====================
    public static ArrayList<Invoice> load() {
        ArrayList<Invoice> list = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return list;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split("\\|");
                
                String invoiceId = data[0];
                String employeeName = data[1];
                // Bỏ qua giá trị tổng tiền lưu ở cột 2 vì hàm load sẽ tự tính lại từ danh sách món

                Invoice invoice = new Invoice(invoiceId, employeeName);

                // Nếu có danh sách món lồng phía sau
                if (data.length >= 4 && !data[3].trim().isEmpty()) {
                    String[] itemsData = data[3].split(";");
                    for (String itemStr : itemsData) {
                        String[] itemParts = itemStr.split(",");
                        if (itemParts.length == 3) {
                            String pName = itemParts[0];
                            double price = Double.parseDouble(itemParts[1]);
                            int qty = Integer.parseInt(itemParts[2]);
                            invoice.addItem(pName, price, qty);
                        }
                    }
                }
                list.add(invoice);
            }
        } catch (Exception e) {
            System.out.println("❌ Load Order.txt thất bại!");
        }
        return list;
    }

    // ==================== SAVE ====================
    public static void save(ArrayList<Invoice> list) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Invoice invoice : list) {
                // Tự build chuỗi lưu hóa đơn lồng danh sách món
                StringBuilder sb = new StringBuilder();
                sb.append(invoice.getInvoiceId()).append("|")
                  .append(invoice.getEmployeeName()).append("|")
                  .append(invoice.getTotalAmount()).append("|");

                ArrayList<OrderItem> items = invoice.getItemList();
                for (int i = 0; i < items.size(); i++) {
                    OrderItem item = items.get(i);
                    sb.append(item.getProductName()).append(",")
                      .append(item.getPrice()).append(",")
                      .append(item.getQuantity());
                    if (i < items.size() - 1) sb.append(";");
                }
                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("❌ Save Order.txt thất bại!");
        }
    }
}