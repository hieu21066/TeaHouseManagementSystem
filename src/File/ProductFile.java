package file;

import product.*;
import java.io.*;
import java.util.ArrayList;

public class ProductFile {
    private static final String CATALOG_FILE = "ProductCatalog.txt";
    private static final String STORAGE_FILE = "ProductStorage.txt";

    // 1. Đọc danh mục sản phẩm chuẩn từ Catalog (Mặc định quantity = 0)
    public static ArrayList<Product> loadCatalog() {
        ArrayList<Product> catalogList = new ArrayList<>();
        File file = new File(CATALOG_FILE);
        if (!file.exists()) {
            System.out.println("❌ Catalog file not found at: " + CATALOG_FILE);
            return catalogList;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split("\\|");
                
                String id = data[0].trim();
                String className = data[1].trim();
                String category = data[2].trim();
                String name = data[3].trim();
                double price = Double.parseDouble(data[4].trim());
                String desc = data[5].trim();

                Product p = null;
                // Khởi tạo đối tượng trực tiếp bằng ID lấy từ file, quantity mặc định gán bằng 0
                switch (className) {
                    case "Tea":
                        int weight = Integer.parseInt(data[6].trim());
                        p = new Tea(id, name, price, 0, desc, category, weight);
                        break;
                    case "Teapot":
                        String clay = data[6].trim();
                        String shape = data[7].trim();
                        int capPot = Integer.parseInt(data[8].trim());
                        p = new Teapot(id, name, price, 0, desc, clay, shape, capPot);
                        break;
                    case "TeaCup":
                        String role = data[6].trim();
                        int capCup = Integer.parseInt(data[7].trim());
                        p = new TeaCup(id, name, price, 0, desc, role, capCup);
                        break;
                    case "TeaAccessories":
                        String accType = data[6].trim();
                        p = new TeaAccessories(id, name, price, 0, desc, accType);
                        break;
                }
                if (p != null) {
                    catalogList.add(p);
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Error reading catalog file: " + e.getMessage());
        }
        return catalogList;
    }

    // 2. Nạp số lượng/số gram tồn kho thực tế từ file Storage (Khắc phục hoàn toàn lỗi gạch đỏ)
    public static void loadStorage(ArrayList<Product> activeList) {
        File file = new File(STORAGE_FILE);
        if (!file.exists()) return; // Nếu lần đầu chạy chưa có kho thực tế thì bỏ qua

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split("\\|");
                String id = data[0].trim();
                int qty = Integer.parseInt(data[1].trim());

                // Tìm sản phẩm khớp ID để cập nhật số lượng tồn kho
                for (Product p : activeList) {
                    if (p.getId().equalsIgnoreCase(id)) {
                        p.setQuantity(qty);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Error reading storage file: " + e.getMessage());
        }
    }

    // 3. Ghi dữ liệu số lượng thực tế trong kho vào file Storage (Chỉ lưu ID|Số_lượng)
    public static void saveStorage(ArrayList<Product> activeList) {
        File file = new File(STORAGE_FILE);
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs(); // Tự tạo thư mục DATA nếu chưa có
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Product p : activeList) {
                bw.write(p.getId() + "|" + p.getQuantity());
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("❌ Error saving storage file: " + e.getMessage());
        }
    }
}