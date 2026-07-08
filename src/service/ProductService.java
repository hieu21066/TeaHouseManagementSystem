package product;

import file.ProductFile;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductService {
    // Chứa dữ liệu mẫu chỉ đọc để đối chiếu ID
    private ArrayList<Product> catalogList;
    // Chứa dữ liệu kho đang chạy thực tế của quán (Biến động số lượng)
    private ArrayList<Product> activeProductList;

    public ProductService() {
        // Tải danh mục gốc làm chuẩn so sánh
        this.catalogList = ProductFile.loadCatalog();
        
        // Tạo danh sách chạy thực tế bằng cách nhân bản từ danh mục mẫu sang
        this.activeProductList = ProductFile.loadCatalog();
        
        // Nạp đè số lượng thực tế từ file kho lên danh sách đang chạy
        ProductFile.loadStorage(this.activeProductList);
    }

    // Chức năng hiển thị danh sách kho hàng thực tế của quán trà
    public void displayAllProducts() {
        if (activeProductList.isEmpty()) {
            System.out.println("No products available in the system.");
            return;
        }
        Product.displayHeader();
        for (Product p : activeProductList) {
            p.display();
        }
    }

    // Chức năng NHẬP HÀNG (Import) chuẩn tư duy: Chỉ nhập ID có sẵn -> Tìm thông tin -> Nạp số lượng
    public void importProduct() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Product ID to import (e.g., TE001, TP001): ");
        String inputId = sc.nextLine().trim().toUpperCase();

        // 1. Kiểm tra xem ID nhập vào có nằm trong Danh mục gốc (Catalog) không
        Product catalogItem = null;
        for (Product p : catalogList) {
            if (p.getId().equalsIgnoreCase(inputId)) {
                catalogItem = p;
                break;
            }
        }

        if (catalogItem == null) {
            System.out.println("❌ Error: Product ID [" + inputId + "] does not exist in the master catalog!");
            return;
        }

        // 2. Định vị sản phẩm đó trong Kho thực tế để chuẩn bị cộng dồn số lượng
        Product activeItem = null;
        for (Product p : activeProductList) {
            if (p.getId().equalsIgnoreCase(inputId)) {
                activeItem = p;
                break;
            }
        }

        // 3. In thông tin thô của sản phẩm ra màn hình để nhân viên xác nhận trước khi nhập số lượng
        System.out.println("\n=== PRODUCT VALIDATED ===");
        System.out.println("» Name: " + catalogItem.getName());
        System.out.println("» Price: " + catalogItem.getPrice() + " USD");
        System.out.println("» Current Stock: " + (activeItem != null ? activeItem.getQuantity() : 0));
        
        // Tự động nhận diện đơn vị tính: Trà tính bằng gram, các thứ khác tính bằng chiếc (pieces)
        String unit = (catalogItem instanceof Tea) ? "grams" : "pieces";
        System.out.print("Enter quantity to add (" + unit + "): ");
        
        try {
            int amount = Integer.parseInt(sc.nextLine());
            if (amount <= 0) {
                System.out.println("❌ Quantity must be greater than 0. Import canceled.");
                return;
            }

            // 4. Tiến hành cộng dồn vào kho thực tế và lưu file ngay lập tức
            if (activeItem != null) {
                activeItem.setQuantity(activeItem.getQuantity() + amount);
                
                // Lưu lại file kho thực tế Storage.txt
                ProductFile.saveStorage(activeProductList);
                System.out.println("✅ Successfully imported " + amount + " " + unit + " for [" + catalogItem.getName() + "].");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid number format. Import canceled.");
        }
    }

    // Tìm kiếm nhanh một sản phẩm trong kho phục vụ cho module Order bán hàng
    public Product findProductById(String id) {
        for (Product p : activeProductList) {
            if (p.getId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }

    // Ép hệ thống lưu file Storage từ bên ngoài khi có biến động (như khi Order trừ kho)
    public void saveStorageFile() {
        ProductFile.saveStorage(activeProductList);
    }
}