package view;

import java.util.ArrayList;
import java.util.Scanner;
import product.*;          // Import các lớp Tea, Teapot, TeaCup...
import service.ProductService; // Import chính xác từ package service của bạn

public class ProductView {

    private ProductService productService; // Hết sạch lỗi đỏ!
    private Scanner sc;

    public ProductView(ProductService productService) {
        this.productService = productService;
        this.sc = new Scanner(System.in);
    }

    public void run() {
        int choice;

        do {
            menu();
            System.out.print("Choose an option: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid choice! Please enter a number.");
                choice = -1;
                continue;
            }

            switch (choice) {
                case 1:
                    productService.importProduct();
                    break;

                case 2:
                    productService.deleteProductFromStock();
                    break;

                case 3:
                    searchProduct();
                    break;

                case 4:
                    displayAllProducts();
                    break;

                case 5:
                    displayFilteredProducts("Tea");
                    break;

                case 6:
                    displayFilteredProducts("Teapot");
                    break;

                case 7:
                    displayFilteredProducts("TeaCup");
                    break;

                case 8:
                    displayFilteredProducts("TeaAccessories");
                    break;

                case 0:
                    System.out.println("Returning to Main Menu...");
                    break;

                default:
                    System.out.println("❌ Invalid choice! Please select from 0 to 8.");
            }

        } while (choice != 0);
    }

    private void menu() {
        System.out.println("\n========== PRODUCT MANAGEMENT ==========");
        System.out.println("1. Import Product Stock (Nhập thêm số lượng)");
        System.out.println("2. Delete Product From Stock (Xóa khỏi kho)");
        System.out.println("3. Search Product By ID");
        System.out.println("4. Display All Products");
        System.out.println("----------------------------------------");
        System.out.println("5. Display Only Teas (Trà)");
        System.out.println("6. Display Only Teapots (Ấm trà)");
        System.out.println("7. Display Only TeaCups (Chén trà)");
        System.out.println("8. Display Only Accessories (Trà cụ / Phụ kiện)");
        System.out.println("0. Back");
        System.out.println("========================================");
    }

    private void searchProduct() {
        System.out.print("Enter Product ID to search: ");
        String id = sc.nextLine().trim();

        Product found = productService.findProductById(id);
        if (found == null) {
            System.out.println("❌ Product Not Found!");
        } else {
            System.out.println("\n🔍 SEARCH RESULT:");
            Product.displayHeader();
            found.display();
        }
    } 

    private void displayAllProducts() {
        System.out.println("\n--- ACTIVE INVENTORY STOCK ---");
        productService.displayAllProducts();
    }

    private void displayFilteredProducts(String className) {
        System.out.println("\n--- " + className.toUpperCase() + " INVENTORY LIST ---");
        
        boolean hasItems = false;
        boolean headerPrinted = false;

        for (Product p : getActiveListFromService()) {
            boolean match = false;
            if (className.equals("Tea") && p instanceof Tea) match = true;
            else if (className.equals("Teapot") && p instanceof Teapot) match = true;
            else if (className.equals("TeaCup") && p instanceof TeaCup) match = true;
            else if (className.equals("TeaAccessories") && p instanceof TeaAccessories) match = true;

            if (match) {
                if (!headerPrinted) {
                    Product.displayHeader();
                    headerPrinted = true;
                }
                p.display();
                hasItems = true;
            }
        }

        if (!hasItems) {
            System.out.println("📭 No products available for category: " + className);
        }
    }

    private ArrayList<Product> getActiveListFromService() {
        ArrayList<Product> list = new ArrayList<>();
        // Quét dải ID mẫu từ 001 đến 020 để lấy dữ liệu từ service hiển thị lên view
        String[] types = {"E", "P", "C", "A"};
        for (int i = 1; i <= 20; i++) { 
            for(String t : types) {
                String id = "T" + t + String.format("%03d", i);
                Product p = productService.findProductById(id);
                if (p != null) list.add(p);
            }
        }
        return list;
    }
}