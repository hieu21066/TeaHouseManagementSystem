package service; // Chuẩn package service của bạn

import file.ProductFile;
import product.*; // Import tất cả các lớp Tea, Teapot, TeaCup... từ package product
import java.util.ArrayList;
import java.util.Scanner;

public class ProductService {
    private ArrayList<Product> catalogList;      
    private ArrayList<Product> activeProductList; 

    public ProductService() {
        this.catalogList = ProductFile.loadCatalog();
        this.activeProductList = ProductFile.loadCatalog();
        ProductFile.loadStorage(this.activeProductList);
    }

    // 1. Hiển thị toàn bộ kho hàng thực tế
    public void displayAllProducts() {
        if (activeProductList.isEmpty()) {
            System.out.println("No products available in the active stock.");
            return;
        }
        Product.displayHeader();
        for (Product p : activeProductList) {
            p.display();
        }
    }

    // 2. NHẬP SẢN PHẨM (Cộng dồn số lượng vào kho thực tế theo ID có sẵn từ Catalog)
    public void importProduct() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Product ID to import: ");
        String inputId = sc.nextLine().trim().toUpperCase();

        Product catalogItem = findInList(catalogList, inputId);
        if (catalogItem == null) {
            System.out.println("❌ Error: Product ID [" + inputId + "] does not exist in the master catalog!");
            return;
        }

        Product activeItem = findInList(activeProductList, inputId);
        System.out.println("\n--- Product Identified ---");
        System.out.println("» Name: " + catalogItem.getName());
        System.out.println("» Current Stock: " + (activeItem != null ? activeItem.getQuantity() : 0));
        
        String unit = (catalogItem instanceof Tea) ? "grams" : "pieces";
        System.out.print("Enter amount to add (" + unit + "): ");
        
        try {
            int amount = Integer.parseInt(sc.nextLine());
            if (amount <= 0) {
                System.out.println("❌ Invalid amount.");
                return;
            }

            if (activeItem != null) {
                activeItem.setQuantity(activeItem.getQuantity() + amount);
                ProductFile.saveStorage(activeProductList); // Lưu file Storage.txt ngay
                System.out.println("✅ Successfully updated stock for [" + catalogItem.getName() + "].");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid number format.");
        }
    }

    // 3. XÓA SẢN PHẨM KHỎI KHO (Đưa số lượng tồn kho thực tế về 0)
    public void deleteProductFromStock() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Product ID to clear from active stock: ");
        String inputId = sc.nextLine().trim().toUpperCase();

        Product activeItem = findInList(activeProductList, inputId);
        if (activeItem == null) {
            System.out.println("❌ Error: Product ID [" + inputId + "] is not found in active stock.");
            return;
        }

        System.out.print("Are you sure you want to clear stock for " + activeItem.getName() + "? (Y/N): ");
        String confirm = sc.nextLine().trim();
        if (confirm.equalsIgnoreCase("Y")) {
            activeItem.setQuantity(0); // Đưa số lượng về 0
            ProductFile.saveStorage(activeProductList); // Lưu file Storage.txt
            System.out.println("✅ Successfully cleared stock for product [" + inputId + "].");
        } else {
            System.out.println("Operation canceled.");
        }
    }

    // Hàm tiện ích tìm kiếm sản phẩm theo ID dùng chung
    public Product findProductById(String id) {
        return findInList(activeProductList, id);
    }

    private Product findInList(ArrayList<Product> list, String id) {
        for (Product p : list) {
            if (p.getId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }

    public void saveStorageFile() {
        ProductFile.saveStorage(activeProductList);
    }
}