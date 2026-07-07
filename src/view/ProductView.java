package view;

import java.util.ArrayList;
import java.util.Scanner;
import product.*;
import service.ProductService;

public class ProductView {

    public static void displayMenu(Scanner sc, ProductService productService) {
        int choose;
        do {
            System.out.println("\n====== PRODUCT MANAGEMENT ======");
            System.out.println("1. Add Product");
            System.out.println("2. Delete Product");
            System.out.println("3. Search Product By ID");
            System.out.println("4. Display All Products");
            System.out.println("5. Filter Products By Category");
            System.out.println("0. Back");
            System.out.print("Choose: ");

            try {
                choose = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Lỗi: Vui lòng nhập một số nguyên hợp lệ!");
                choose = -1; // Gán giá trị lỗi để vòng lặp tiếp tục chạy
                continue;
            }

            switch (choose) {
                case 1:
                    addProductSubMenu(sc, productService);
                    break;

                case 2:
                    System.out.print("Enter ID to delete: ");
                    String idDelete = sc.nextLine();
                    productService.deleteProductById(idDelete);
                    break;

                case 3:
                    System.out.print("Enter ID to search: ");
                    String idSearch = sc.nextLine();
                    Product found = productService.findProductById(idSearch);
                    if (found != null) {
                        System.out.println("🔍 Kết quả tìm kiếm:");
                        printSingleProduct(found);
                    } else {
                        System.out.println("❌ Không tìm thấy sản phẩm có mã: " + idSearch);
                    }
                    break;

                case 4:
                    printCustomAllProducts(productService.getAllProducts());
                    break;

                case 5:
                    filterProductSubMenu(sc, productService);
                    break;

                case 0:
                    System.out.println("Returning to main menu...");
                    break;

                default:
                    System.out.println("❌ Lựa chọn không hợp lệ! Vui lòng chọn từ 0-5.");
            }
        } while (choose != 0);
    }

    // --- SUB MENU: THÊM SẢN PHẨM MỚI ---
    private static void addProductSubMenu(Scanner sc, ProductService productService) {
        System.out.println("\n--- Select Product Type To Add ---");
        System.out.println("1. Tea (Trà)");
        System.out.println("2. TeaWare (Trà cụ)");
        System.out.println("3. Accessory (Phụ kiện)");
        System.out.println("4. TeaPet (Trà sủng)");
        System.out.print("Choose type (1-4): ");

        int type;
        try {
            type = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Loại sản phẩm không hợp lệ!");
            return;
        }

        if (type < 1 || type > 4) {
            System.out.println("❌ Loại sản phẩm không hợp lệ!");
            return;
        }

        System.out.print("Enter ID: ");
        String id = sc.nextLine().trim();
        if (productService.findProductById(id) != null) {
            System.out.println("❌ Lỗi: Mã sản phẩm \"" + id + "\" đã tồn tại trong hệ thống!");
            return;
        }

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        // Xử lý nhập Price an toàn
        double price;
        while (true) {
            try {
                System.out.print("Enter Price: ");
                price = Double.parseDouble(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("❌ Lỗi: Giá tiền phải là một số thực hợp lệ!");
            }
        }

        switch (type) {
            case 1:
                System.out.print("Enter Tea Type (e.g., Trà Xanh, Ô Long): ");
                String teaType = sc.nextLine();
                productService.addTea(id, name, price, teaType);
                break;

            case 2:
                System.out.print("Enter Ware Type (e.g., Ấm trà, Chén khải): ");
                String wareType = sc.nextLine();
                System.out.print("Enter Clay Type (e.g., Tử nê, Chu nê): ");
                String clayTypeWare = sc.nextLine();
                System.out.print("Enter Design (e.g., Tây Thi, Thạch Biến): ");
                String design = sc.nextLine();
                
                // Xử lý nhập Capacity an toàn
                int capacity;
                while (true) {
                    try {
                        System.out.print("Enter Capacity (ml): ");
                        capacity = Integer.parseInt(sc.nextLine());
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Lỗi: Dung tích phải là một số nguyên hợp lệ!");
                    }
                }
                productService.addTeaWare(id, name, price, wareType, clayTypeWare, design, capacity);
                break;

            case 3:
                System.out.print("Enter Accessory Type (e.g., Khay trà, Kẹp trà): ");
                String accessoryType = sc.nextLine();
                productService.addAccessory(id, name, price, accessoryType);
                break;

            case 4:
                System.out.print("Enter Pet Type (e.g., Cóc thiềm thừ, Tỳ hưu): ");
                String petType = sc.nextLine();
                System.out.print("Enter Clay Type (e.g., Đoạn nê, Tử nê): ");
                String clayTypePet = sc.nextLine();
                System.out.print("Enter Status (e.g., Mới, Đã lên nước): ");
                String status = sc.nextLine();
                productService.addTeaPet(id, name, price, petType, clayTypePet, status);
                break;
        }
    }

    // --- SUB MENU: LỌC SẢN PHẨM ---
    private static void filterProductSubMenu(Scanner sc, ProductService productService) {
        System.out.println("\n--- Filter Category ---");
        System.out.println("1. Show Only Teas (Trà)");
        System.out.println("2. Show Only TeaWares (Trà cụ)");
        System.out.println("3. Show Only Accessories (Phụ kiện)");
        System.out.println("4. Show Only TeaPets (Trà sủng)");
        System.out.print("Choose filter (1-4): ");

        int filterChoose;
        try {
            filterChoose = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Lựa chọn lọc không hợp lệ!");
            return;
        }

        switch (filterChoose) {
            case 1:
                ArrayList<Tea> teas = productService.getOnlyTeas();
                System.out.println("\n--- DANH SÁCH CÁC LOẠI TRÀ ---");
                if (teas.isEmpty()) System.out.println("Không có sản phẩm nào.");
                else teas.forEach(System.out::println);
                break;

            case 2:
                ArrayList<TeaWare> teaWares = productService.getOnlyTeaWares();
                System.out.println("\n--- DANH SÁCH TRÀ CỤ ---");
                if (teaWares.isEmpty()) System.out.println("Không có sản phẩm nào.");
                else teaWares.forEach(System.out::println);
                break;

            case 3:
                ArrayList<Accessory> accessories = productService.getOnlyAccessories();
                System.out.println("\n--- DANH SÁCH PHỤ KIỆN BÀN TRÀ ---");
                if (accessories.isEmpty()) System.out.println("Không có sản phẩm nào.");
                else accessories.forEach(System.out::println);
                break;

            case 4:
                ArrayList<TeaPet> teaPets = productService.getOnlyTeaPets();
                System.out.println("\n--- DANH SÁCH TRÀ SỦNG (TEA PET) ---");
                if (teaPets.isEmpty()) System.out.println("Không có sản phẩm nào.");
                else teaPets.forEach(ProductView::printSingleProduct);
                break;

            default:
                System.out.println("❌ Lựa chọn lọc không hợp lệ!");
        }
    }

    // --- TIỆN ÍCH IN MỘT SẢN PHẨM ĐỒNG BỘ ---
    private static void printSingleProduct(Product p) {
        if (p instanceof TeaPet) {
            TeaPet pet = (TeaPet) p;
            System.out.printf("Mã: %s | Tên: %s | Giá: %,.0f đ | Tượng: %s | Chất đất: %s | Trạng thái: %s\n",
                    pet.getId(), pet.getName(), pet.getPrice(), pet.getPetType(), pet.getClayType(), pet.getStatus());
        } else {
            System.out.println(p);
        }
    }

    // --- TIỆN ÍCH HIỂN THỊ ĐẸP TOÀN BỘ DANH SÁCH ---
    private static void printCustomAllProducts(ArrayList<Product> list) {
        if (list.isEmpty()) {
            System.out.println("📭 Danh sách sản phẩm hiện tại đang trống.");
            return;
        }
        System.out.println("\n--- DANH SÁCH TẤT CẢ SẢN PHẨM ---");
        for (Product p : list) {
            printSingleProduct(p);
        }
    }
}