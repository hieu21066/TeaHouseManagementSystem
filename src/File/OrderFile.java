package file;

import order.Invoice;
import java.io.*;
import java.util.ArrayList;

public class OrderFile {
    private static final String FILE_NAME = "Order.txt";

    // ==================== HÀM LOAD (ĐỌC FILE) ====================
    public static ArrayList<Invoice> load() {
    ArrayList<Invoice> list = new ArrayList<>();
    File file = new File(FILE_NAME);
    if (!file.exists()) return list;

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;
            
            String[] data = line.split("\\|");
            if (data.length >= 3) {
                String invoiceId = data[0];
                String empId = data[1];
                Invoice invoice = new Invoice(invoiceId, empId);

                // Đọc các item ở giữa (MãSP-SốLượng)
                for (int i = 2; i < data.length - 1; i++) {
                    String itemStr = data[i];
                    if (!itemStr.contains("-")) continue;

                    String[] itemParts = itemStr.split("-");
                    String pId = itemParts[0];
                    int quantity = Integer.parseInt(itemParts[1]);

                    if (pId.startsWith("TE")) {
                        String pName = "Tea (Gram)";
                        double price = 200.0;
                        // Quét file catalog lấy tên và giá gốc
                        try (BufferedReader catBr = new BufferedReader(new FileReader("ProductCatalog.txt"))) {
                            String catLine;
                            while ((catLine = catBr.readLine()) != null) {
                                String[] catData = catLine.split("\\|");
                                if (catData[0].equalsIgnoreCase(pId)) {
                                    pName = catData[3] + " (Gram)";
                                    price = Double.parseDouble(catData[4]) * 1.15;
                                    break;
                                }
                            }
                        } catch (Exception e) {}
                        invoice.addItem(pId + "|" + pName, price, quantity);
                    } else if (pId.equals("SERVICE_FEE")) {
                        invoice.addItem("SERVICE_FEE|Phí dịch vụ thưởng trà", 30000.0, quantity);
                    }
                }
                list.add(invoice);
            }
        }
    } catch (Exception e) {
        System.out.println("Load Order.txt thất bại!");
    }
    return list;
}

    // ==================== HÀM SAVE (GHI FILE) ====================
    public static void save(ArrayList<Invoice> list) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
        for (Invoice invoice : list) {
            // Lấy ID nhân viên
            String empInfo = invoice.getEmployeeName(); 
            String empId = empInfo;
            if (empInfo.contains(" - ")) {
                empId = empInfo.substring(empInfo.lastIndexOf(" - ") + 3).trim();
            }

            // Tiến hành xây dựng chuỗi các sản phẩm: MãSP-SốLượng
            StringBuilder itemsBuilder = new StringBuilder();
            for (order.OrderItem item : invoice.getItemList()) {
                String rawName = item.getProductName();
                String pId = rawName;
                if (rawName.contains("|")) {
                    pId = rawName.split("\\|")[0]; // Tách lấy mã (VD: TE001 hoặc SERVICE_FEE)
                }
                
                // Nối chuỗi, ngăn cách các món bằng dấu gạch đứng |
                itemsBuilder.append(pId).append("-").append(item.getQuantity()).append("|");
            }

            String record = String.format("%s|%s|%s%.0f", 
                    invoice.getInvoiceId(), 
                    empId, 
                    itemsBuilder.toString(), // Chuỗi các món đã có sẵn dấu | ở cuối
                    invoice.getTotalAmount());
            
            bw.write(record);
            bw.newLine();
        }
    } catch (Exception e) {
        System.out.println("Save " + FILE_NAME+" thất bại!");
    }
}
}