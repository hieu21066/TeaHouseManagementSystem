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

    // 2. NHẬP SẢN PHẨM (Cộng dồn số lượng vào kho thực tế và ghi lịch sử vào Import.txt)
    // 2. NHẬP SẢN PHẨM (Cộng dồn số lượng vào kho thực tế và ghi lịch sử vào Import.txt)
    public void importProduct() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Product ID to import: ");
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

        String unit = (catalogItem instanceof Tea) ? "packs" : "pieces";
        System.out.print("Enter amount to add (" + unit + "): ");

        try {
            int amount = Integer.parseInt(sc.nextLine());

            int storageAmount = amount;

            if (catalogItem instanceof Tea) {
                Tea tea = (Tea) catalogItem;
                storageAmount = amount * tea.getSampleWeightGrams();
            }
            if (amount <= 0) {
                System.out.println("Invalid amount.");
                return;
            }
            double realPrice = catalogItem.getPrice() * 1000;
            double totalCost = realPrice * amount;

//================== IMPORT ==================
            if (activeItem == null) {

                // Chưa có trong Storage
                catalogItem.setQuantity(storageAmount);
                activeProductList.add(catalogItem);
                activeItem = catalogItem;

            } else {

                // Đã có trong Storage
                activeItem.setQuantity(activeItem.getQuantity() + storageAmount);

            }

// Lưu lại Storage
            ProductFile.saveStorage(activeProductList);

// Ghi lịch sử nhập
            ProductFile.saveImportLog(activeItem.getId(), amount, realPrice);

            System.out.println("\n----------------------------------------");
            System.out.println("✅ Successfully updated stock for [" + catalogItem.getName() + "].");
            System.out.printf("Original price: %,.0f VND / %s\n",
                    realPrice,
                    (catalogItem instanceof Tea) ? "Gram" : "Cái");
            System.out.printf("TOTAL INVESTMENT: %,.0f VND\n", totalCost);
            System.out.println("📝 The import history has been saved Import.txt!");
            System.out.println("----------------------------------------");
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
            activeProductList.remove(activeItem);
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
0