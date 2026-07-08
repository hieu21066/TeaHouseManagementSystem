package view;

import java.util.ArrayList;
import java.util.Scanner;
import product.*;
import service.ProductService;

public class ProductView {

    private ProductService productService;
    private Scanner sc;

    // Constructor nhận Service và khởi tạo Scanner giống EmployeeView
    public ProductView(ProductService productService) {
        this.productService = productService;
        this.sc = new Scanner(System.in);
    }

    public void run() {
        int choice;

        do {
            menu();
            System.out.print("Choose: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid choice! Please enter a number.");
                choice = -1;
                continue;
            }

            switch (choice) {
                case 1:
                    addProduct();
                    break;

                case 2:
                    deleteProduct();
                    break;

                case 3:
                    searchProduct();
                    break;

                case 4:
                    printCustomAllProducts(productService.getAllProducts());
                    break;

                case 5:
                    displayTeas();
                    break;

                case 6:
                    displayTeaWares();
                    break;

                case 7:
                    displayAccessories();
                    break;

                case 8:
                    displayTeaPets();
                    break;

                case 0:
                    System.out.println("Back...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 0);
    }

    // ==========================================================
    private void menu() {
        System.out.println("\n========== PRODUCT MANAGEMENT ==========");
        System.out.println("1. Add Product");
        System.out.println("2. Delete Product");
        System.out.println("3. Search Product By ID");
        System.out.println("4. Display All Products");
        System.out.println("----------------------------------------");
        System.out.println("5. Display Only Teas (Trà)");
        System.out.println("6. Display Only TeaWares (Trà cụ)");
        System.out.println("7. Display Only Accessories (Phụ kiện)");
        System.out.println("8. Display Only TeaPets (Trà sủng)");
        System.out.println("0. Back");
        System.out.println("========================================");
    }

    // ==========================================================
    private void addProduct() {
        System.out.println("----- Select Product Type -----");
        System.out.println("1. Tea (Trà)");
        System.out.println("2. TeaWare (Trà cụ)");
        System.out.println("3. Accessory (Phụ kiện)");
        System.out.println("4. TeaPet (Trà sủng)");
        System.out.print("Choose: ");

        int type;
        try {
            type = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid type!");
            return;
        }

        if (type < 1 || type > 4) {
            System.out.println("❌ Invalid type!");
            return;
        }

        System.out.print("Enter ID: ");
        String id = sc.nextLine().trim();
        if (productService.findProductById(id) != null) {
            System.out.println("❌ Error: Product ID \"" + id + "\" already exists!");
            return;
        }

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        double price;
        while (true) {
            try {
                System.out.print("Enter Price: ");
                price = Double.parseDouble(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("❌ Error: Price must be a valid number!");
            }
        }

        switch (type) {
            case 1:
                System.out.print("Enter Tea Type: ");
                String teaType = sc.nextLine();
                productService.addTea(id, name, price, teaType);
                break;

            case 2:
                System.out.print("Enter Ware Type: ");
                String wareType = sc.nextLine();
                System.out.print("Enter Clay Type: ");
                String clayTypeWare = sc.nextLine();
                System.out.print("Enter Design: ");
                String design = sc.nextLine();
                
                int capacity;
                while (true) {
                    try {
                        System.out.print("Enter Capacity (ml): ");
                        capacity = Integer.parseInt(sc.nextLine());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Error: Capacity must be an integer!");
                    }
                }
                productService.addTeaWare(id, name, price, wareType, clayTypeWare, design, capacity);
                break;

            case 3:
                System.out.print("Enter Accessory Type: ");
                String accessoryType = sc.nextLine();
                productService.addAccessory(id, name, price, accessoryType);
                break;

            case 4:
                System.out.print("Enter Pet Type: ");
                String petType = sc.nextLine();
                System.out.print("Enter Clay Type: ");
                String clayTypePet = sc.nextLine();
                System.out.print("Enter Status: ");
                String status = sc.nextLine();
                productService.addTeaPet(id, name, price, petType, clayTypePet, status);
                break;
        }
    }

    // ==========================================================
    private void deleteProduct() {
        System.out.print("Enter Product ID: ");
        String id = sc.nextLine();
        
        if (productService.deleteProductById(id)) {
            System.out.println("Delete Successfully!");
        } else {
            System.out.println("Product Not Found!");
        }
    }

    // ==========================================================
    private void searchProduct() {
        System.out.print("Enter Product ID: ");
        String id = sc.nextLine();

        Teapot found = productService.findProductById(id);
        if (found == null) {
            System.out.println("Product Not Found!");
        } else {
            System.out.println("🔍 Search Result:");
            printSingleProduct(found);
        }
    }

    // ==========================================================
    private void displayTeas() {
        ArrayList<TeaCup> list = productService.getOnlyTeas();
        System.out.println("\n--- TEA LIST ---");
        if (list.isEmpty()) System.out.println("No products found.");
        else list.forEach(System.out::println);
    }

    private void displayTeaWares() {
        ArrayList<TeaAccessories> list = productService.getOnlyTeaWares();
        System.out.println("\n--- TEAWARE LIST ---");
        if (list.isEmpty()) System.out.println("No products found.");
        else list.forEach(System.out::println);
    }

    private void displayAccessories() {
        ArrayList<Tea> list = productService.getOnlyAccessories();
        System.out.println("\n--- ACCESSORY LIST ---");
        if (list.isEmpty()) System.out.println("No products found.");
        else list.forEach(System.out::println);
    }

    private void displayTeaPets() {
        ArrayList<TeaPet> list = productService.getOnlyTeaPets();
        System.out.println("\n--- TEAPET LIST ---");
        if (list.isEmpty()) System.out.println("No products found.");
        else list.forEach(this::printSingleProduct);
    }

    // ========================== HELPERS ==========================
    private void printSingleProduct(Teapot p) {
        if (p instanceof TeaPet) {
            TeaPet pet = (TeaPet) p;
            System.out.printf("Mã: %s | Tên: %s | Giá: %,.0f đ | Tượng: %s | Chất đất: %s | Trạng thái: %s\n",
                    pet.getId(), pet.getName(), pet.getPrice(), pet.getPetType(), pet.getClayType(), pet.getStatus());
        } else {
            System.out.println(p);
        }
    }

    private void printCustomAllProducts(ArrayList<Teapot> list) {
        if (list.isEmpty()) {
            System.out.println("📭 Product list is currently empty.");
            return;
        }
        System.out.println("\n--- ALL PRODUCTS LIST ---");
        for (Teapot p : list) {
            printSingleProduct(p);
        }
    }
}