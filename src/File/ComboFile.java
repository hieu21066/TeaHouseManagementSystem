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

    //==================== LOAD ====================
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
                    double price = Double.parseDouble(data[3]);
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

    //==================== SAVE ====================
    public static void save(ArrayList<Combo> list) {

        createFileIfNotExists();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (Combo combo : list) {

                bw.write(combo.toString());
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
            String line = financeId + "|" + comboId + "|" + comboName + "|" + quantity + "|" + price;
            bw.write(line);
            bw.newLine();
        } catch (java.io.IOException e) {
            System.out.println("Lỗi khi ghi lịch sử mua combo vào file combopay.txt: " + e.getMessage());
        }
    }

    
    public static java.util.ArrayList<String[]> loadComboPay() {
        java.util.ArrayList<String[]> payList = new java.util.ArrayList<>();
        java.io.File file = new java.io.File("combopay.txt");
        
        // Nếu file chưa tồn tại (chưa có ai mua combo nào), trả về danh sách rỗng luôn
        if (!file.exists()) {
            return payList;
        }

        // Ép kiểu đọc bằng UTF_8 để không bị lỗi font tiếng Việt có dấu
        try (java.io.BufferedReader br = new java.io.BufferedReader(
                new java.io.InputStreamReader(new java.io.FileInputStream(file), java.nio.charset.StandardCharsets.UTF_8))) {
            
            String line;
            while ((line = br.readLine()) != null) {
                // Bỏ qua dòng trống nếu có
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                // Tách chuỗi dựa trên ký tự gạch đứng |
                String[] data = line.split("\\|");
                if (data.length == 5) {
                    payList.add(data);
                }
            }
        } catch (java.io.IOException e) {
            System.out.println("Lỗi khi đọc lịch sử mua combo từ file combopay.txt: " + e.getMessage());
        }
        
        return payList;
    }
}