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

        if (!file.exists()) {
            return list;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                // Tách dữ liệu bằng dấu gạch đứng |
                String[] data = line.split("\\|");
                if (data.length >= 3) {
                    String invoiceId = data[0];
                    String empId = data[1];
                    double totalAmount = Double.parseDouble(data[2]);

                    // Khởi tạo đối tượng hóa đơn mới
                    Invoice invoice = new Invoice(invoiceId, empId);
                    
                    // FIX LỖI: Nạp số tiền doanh thu tích lũy vào hóa đơn bằng cách add một item giả lập,
                    // Giúp hệ thống vẫn nhận đủ tổng tiền mà bạn không cần tạo hàm setTotalAmount trong class Invoice.
                    invoice.addItem("SERVICE_FEE|Doanh thu tích lũy", totalAmount, 1);
                    
                    list.add(invoice);
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Load Order.txt thất bại!");
        }
        return list;
    } 

    // ==================== HÀM SAVE (GHI FILE) ====================
    public static void save(ArrayList<Invoice> list) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Invoice invoice : list) {
                // Tách lấy Mã nhân viên (ID) từ chuỗi thông tin (nếu chuỗi có dạng "Tên - ID")
                String empInfo = invoice.getEmployeeName(); 
                String empId = empInfo;
                if (empInfo.contains(" - ")) {
                    empId = empInfo.substring(empInfo.lastIndexOf(" - ") + 3).trim();
                }

                // Ghi dữ liệu xuống file theo định dạng: ID_Order|ID_Nhân_Viên|Tổng_Tiền
                // Số tiền được làm tròn về số nguyên để nhìn file lưu trữ sạch sẽ hơn
                String record = String.format("%s|%s|%.0f", 
                        invoice.getInvoiceId(), 
                        empId, 
                        invoice.getTotalAmount());
                
                bw.write(record);
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("❌ Save Order.txt thất bại!");
        }
    }
}