package service;

import file.ProductFile;
import product.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductService {

    private ArrayList<Product> catalogList;
    private ArrayList<Product> activeProductList;

    public ProductService() {
        this.catalogList = ProductFile.loadCatalog();
        
        // 🛠 Chỉ nạp vào activeProductList những sản phẩm thực tế đã được lưu trong ProductStorage.txt
        // Các sản phẩm chưa từng import sẽ KHÔNG xuất hiện trong Display All
        this.activeProductList = ProductFile.loadCatalog();
        
        // Lọc lại: chỉ giữ lại những sản phẩm thực sự tồn tại trong file Storage.txt
        java.io.File storageFile = new java.io.File("ProductStorage.txt");
        if (storageFile.exists()) {
            // Nạp dữ liệu số lượng thực tế từ Storage
            ProductFile.loadStorage(this.activeProductList);
            
            // Loại bỏ khỏi activeProductList những món chưa từng có dòng dữ liệu trong Storage.txt
            java.util.Set<String> storedIds = new java.util.HashSet<>();
            try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(storageFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    String[] parts = line.split("\\|");
                    storedIds.add(parts[0].trim().toUpperCase());
                }
            } catch (Exception e) {
                // ignore
            }
            
            this.activeProductList.removeIf(p -> !storedIds.contains(p.getId().toUpperCase()));
        } else {
            // Nếu chưa có file Storage nghĩa là chưa có gì được import
            this.activeProductList.clear();
        }
    }

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

    public void importProduct() {
        Scanner sc = new Scanner(System.in);
        
        // 🔍 In ra danh sách Catalog trước để người dùng nhìn mã nhập cho chuẩn
        displayCatalogList();
        
        System.out.print("\nEnter Product ID to import: ");
        String inputId = sc.nextLine().trim().toUpperCase();

        Product catalogItem = findInList(catalogList, inputId);
        if (catalogItem == null) {
            System.out.println("Error: Product ID [" + inputId + "] does not exist in the master catalog!");
            return;
        }

        Product activeItem = findInList(activeProductList, inputId);
        System.out.println("\n--- Product Identified ---");
        System.out.println("Name: " + catalogItem.getName());
        System.out.println("Current Stock: " + (activeItem != null ? activeItem.getQuantity() : 0));

        // Kiểm tra xem sản phẩm có tính theo gram không (Trà hoặc Đồ ăn kèm mã AC)
        // Kiểm tra xem sản phẩm có tính theo gram không (Trà hoặc Đồ ăn kèm)
        boolean isGramItem = (catalogItem instanceof Tea) || (catalogItem instanceof Accompaniment);
        
        String unit = isGramItem ? "packs/items" : "pieces";
        System.out.print("Enter amount to add (" + unit + "): ");

        try {
            int amount = Integer.parseInt(sc.nextLine());
            int storageAmount = amount;

            if (amount <= 0) {
                System.out.println("Invalid amount.");
                return;
            }

            // Xử lý quy đổi số lượng gram thực tế đưa vào kho
            if (catalogItem instanceof Tea) {
                Tea tea = (Tea) catalogItem;
                storageAmount = amount * tea.getSampleWeightGrams();
            } else if (catalogItem instanceof Accompaniment) {
                Accompaniment acc = (Accompaniment) catalogItem;
                storageAmount = amount * acc.getGramPerServing();
            }

            double realPrice = catalogItem.getPrice() * 1000;
            double totalCost = realPrice * amount;

//================== IMPORT ==================
            if (activeItem == null) {
                // Chưa có trong Storage -> Tạo mới bản sao từ catalog, gán số lượng và đưa vào active
                // Dùng phương thức clone hoặc khởi tạo lại từ catalogItem để không bị dính tham chiếu
                Product newItem = findInList(catalogList, inputId);
                newItem.setQuantity(storageAmount);
                activeProductList.add(newItem);
                activeItem = newItem;
            } else {
                // Đã có trong Storage, cộng dồn số lượng/gram
                activeItem.setQuantity(activeItem.getQuantity() + storageAmount);
            }

// Lưu lại Storage
            ProductFile.saveStorage(activeProductList);

// Ghi lịch sử nhập
            ProductFile.saveImportLog(activeItem.getId(), amount, realPrice);

            System.out.println("\n----------------------------------------");
            System.out.println("✅ Successfully updated stock for [" + catalogItem.getName() + "].");
            System.out.printf("Original price: %,.0f VND\n", realPrice);
            if (isGramItem) {
                System.out.printf("Added to stock: +%d grams\n", storageAmount);
            }
            System.out.printf("TOTAL INVESTMENT: %,.0f VND\n", totalCost);
            System.out.println("📝 The import history has been saved Import.txt!");
            System.out.println("----------------------------------------");
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid number format.");
        }
    }

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
            activeProductList.remove(activeItem);
            ProductFile.saveStorage(activeProductList);
            System.out.println("✅ Successfully cleared stock for product [" + inputId + "].");
        } else {
            System.out.println("Operation canceled.");
        }
    }

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

public void displayCatalogList() {
        if (activeProductList.isEmpty()) {
            System.out.println("No active stock available.");
            return;
        }
        System.out.println("\n--- AVAILABLE PRODUCTS IN STOCK ---");
        Product.displayHeader();
        
        // Hiển thị danh sách dựa trên activeProductList (giống hệt Display All, có sẵn số lượng thực tế)
        for (Product p : activeProductList) {
            p.display();
        }
    }

    public void saveStorageFile() {
        ProductFile.saveStorage(activeProductList);
    }
}