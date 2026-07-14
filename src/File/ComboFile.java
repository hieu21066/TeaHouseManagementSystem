package file;

import combo.Combo;
import java.io.*;
import java.util.ArrayList;

public class ComboFile {

    private static final String FILE_NAME = "Combo.txt";

    //==================== CREATE FILE ====================
    private static void createFileIfNotExists() {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Create Combo.txt successfully!");
            }
        } catch (IOException e) {
            System.out.println("Cannot create Combo.txt!");
            e.printStackTrace();
        }
    }

    //==================== LOAD COMBO ====================
    public static ArrayList<Combo> load() {
        createFileIfNotExists();
        ArrayList<Combo> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split("\\|");
                if (data.length != 5) {
                    continue;
                }

                try {
                    String comboId = data[0];
                    String teaType = data[1];
                    String comboName = data[2];
                    
                    // SỬA TẠI ĐÂY: Chia cho 100.0 khi đọc từ file vào chương trình
                    double price = Double.parseDouble(data[3]) / 100.0;
                    
                    String description = data[4];

                    Combo combo = new Combo(
                            comboId,
                            teaType,
                            comboName,
                            price,
                            description
                    );
                    list.add(combo);

                } catch (NumberFormatException e) {
                    System.out.println("Invalid data: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Load Combo.txt failed!");
            e.printStackTrace();
        }
        return list;
    }

    //==================== SAVE COMBO ====================
    public static void save(ArrayList<Combo> list) {
        createFileIfNotExists();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Combo combo : list) {
                // SỬA TẠI ĐÂY: Nhân ngược lại 100.0 trước khi ghi đè lại file Combo.txt
                double originalPrice = combo.getPrice() * 100.0;

                // Khởi tạo chuỗi ghi theo đúng cấu trúc ban đầu của Combo.toString() nhưng với giá gốc
                String line = combo.getComboId() + "|" 
                            + combo.getTeaType() + "|" 
                            + combo.getComboName() + "|" 
                            + originalPrice + "|" 
                            + combo.getDescription();

                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Save Combo.txt failed!");
            e.printStackTrace();
        }
    }

    // ====================================================================
    // THÊM MỚI: Xử lý ghi/đọc lịch sử mua Combo của Khách (Lưu vào combopay.txt)
    // ====================================================================

    // Hàm tự động kiểm tra và tạo file combopay.txt nếu chưa tồn tại
    private static void createComboPayFileIfNotExists() {
        try {
            java.io.File file = new java.io.File("combopay.txt");
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Create combopay.txt successfully!");
            }
        } catch (java.io.IOException e) {
            System.out.println("Cannot create combopay.txt!");
            e.printStackTrace();
        }
    }

    /**
      Ghi nối tiếp (Append) một giao dịch mua combo của khách hàng xuống file combopay.txt
      Định dạng lưu trữ: financeId|comboId|comboName|quantity|price
     */
    public static void saveComboPay(String financeId, String comboId, String comboName, int quantity, double price) {
        createComboPayFileIfNotExists();
        try (java.io.BufferedWriter bw = new java.io.BufferedWriter(
                new java.io.OutputStreamWriter(new java.io.FileOutputStream("combopay.txt", true), java.nio.charset.StandardCharsets.UTF_8))) {
            
            // SỬA TẠI ĐÂY: Nhân ngược lại 100.0 để ghi giá gốc vào file combopay.txt cho đồng bộ
            double originalPrice = price * 100.0;

            String line = financeId + "|" + comboId + "|" + comboName + "|" + quantity + "|" + originalPrice;
            bw.write(line);
            bw.newLine();
        } catch (java.io.IOException e) {
            System.out.println("Lỗi khi ghi lịch sử mua combo vào file combopay.txt: " + e.getMessage());
        }
    }

    public static java.util.ArrayList<String[]> loadComboPay() {
        java.util.ArrayList<String[]> payList = new java.util.ArrayList<>();
        java.io.File file = new java.io.File("combopay.txt");
        
        if (!file.exists()) {
            return payList;
        }

        try (java.io.BufferedReader br = new java.io.BufferedReader(
                new java.io.InputStreamReader(new java.io.FileInputStream(file), java.nio.charset.StandardCharsets.UTF_8))) {
            
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                String[] data = line.split("\\|");
                if (data.length == 5) {
                    try {
                        // SỬA TẠI ĐÂY: Chia cho 100.0 cho cột giá tiền (data[4]) để hiển thị cân đối
                        double parsedPrice = Double.parseDouble(data[4]) / 100.0;
                        data[4] = String.valueOf(parsedPrice);
                    } catch (NumberFormatException e) {
                        // Bỏ qua nếu dòng bị lỗi định dạng số
                    }
                    payList.add(data);
                }
            }
        } catch (java.io.IOException e) {
            System.out.println("Lỗi khi đọc lịch sử mua combo từ file combopay.txt: " + e.getMessage());
        }
        
        return payList;
    }
}