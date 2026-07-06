package service;

import java.util.ArrayList;
import product.Accessory;
import product.Accessory;
import product.Product;
import product.Product;
import product.Tea;
import product.Tea;
import product.TeaWare;
import product.TeaWare;


public class ProductService {
    // ArrayList dùng để quản lý toàn bộ sản phẩm trong hệ thống (Tính đa hình)
    private ArrayList<Product> productList;

    // Constructor khởi tạo danh sách
    public ProductService() {
        this.productList = new ArrayList<>();
    }

    // 1. THÊM SẢN PHẨM MỚI (Thêm được cả Tea, TeaWare, Accessory)
    public void addProduct(Product product) {
        // Kiểm tra xem mã sản phẩm (ID) đã tồn tại chưa để tránh trùng lặp
        for (Product p : productList) {
            if (p.getId().equalsIgnoreCase(product.getId())) {
                System.out.println("❌ Lỗi: Mã sản phẩm " + product.getId() + " đã tồn tại!");
                return;
            }
        }
        productList.add(product);
        System.out.println("✅ Thêm sản phẩm thành công: " + product.getName());
    }

    // 2. LẤY TOÀN BỘ DANH SÁCH SẢN PHẨM
    public ArrayList<Product> getAllProducts() {
        return this.productList;
    }

    // 3. HIỂN THỊ TẤT CẢ SẢN PHẨM RA MÀN HÌNH
    public void printAllProducts() {
        if (productList.isEmpty()) {
            System.out.println("📭 Danh sách sản phẩm hiện tại đang trống.");
            return;
        }
        System.out.println("\n--- DANH SÁCH TẤT CẢ SẢN PHẨM ---");
        for (Product p : productList) {
            System.out.println(p); // Tự động gọi hàm toString() của từng lớp con
        }
    }

    // 4. TÌM KIẾM SẢN PHẨM THEO MÃ (ID)
    public Product findProductById(String id) {
        for (Product p : productList) {
            if (p.getId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null; // Trả về null nếu không tìm thấy
    }

    // 5. XÓA SẢN PHẨM THEO MÃ (ID)
    public boolean deleteProductById(String id) {
        Product found = findProductById(id);
        if (found != null) {
            productList.remove(found);
            System.out.println("✅ Đã xóa sản phẩm có mã: " + id);
            return true;
        }
        System.out.println("❌ Không tìm thấy sản phẩm có mã " + id + " để xóa.");
        return false;
    }

    // 6. LỌC SẢN PHẨM THEO TỪNG LOẠI CỤ THỂ (Sử dụng instanceof)
    
    // 6a. Chỉ lấy các loại Trà (Tea)
    public ArrayList<Tea> getOnlyTeas() {
        ArrayList<Tea> teas = new ArrayList<>();
        for (Product p : productList) {
            if (p instanceof Tea) {
                teas.add((Tea) p); // Ép kiểu từ Product về Tea
            }
        }
        return teas;
    }

    // 6b. Chỉ lấy các loại Trà Cụ (TeaWare)
    public ArrayList<TeaWare> getOnlyTeaWares() {
        ArrayList<TeaWare> teaWares = new ArrayList<>();
        for (Product p : productList) {
            if (p instanceof TeaWare) {
                teaWares.add((TeaWare) p); // Ép kiểu từ Product về TeaWare
            }
        }
        return teaWares;
    }

    // 6c. Chỉ lấy các loại Phụ kiện / Trà sủng (Accessory)
    public ArrayList<Accessory> getOnlyAccessories() {
        ArrayList<Accessory> accessories = new ArrayList<>();
        for (Product p : productList) {
            if (p instanceof Accessory) {
                accessories.add((Accessory) p); // Ép kiểu từ Product về Accessory
            }
        }
        return accessories;
    }
}