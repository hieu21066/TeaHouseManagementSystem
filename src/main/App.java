package main;

import java.util.Scanner;

import service.EmployeeService;
import service.ProductService;
import service.ComboService;
import service.ReservationService;
import service.OrderService;
import service.FinanceService;

public class App {

    static Scanner sc = new Scanner(System.in);

    //==================== SERVICE ====================

    static EmployeeService employeeService = new EmployeeService();
    static ProductService productService = new ProductService();
    static ComboService comboService = new ComboService();
    static ReservationService reservationService = new ReservationService();
    static OrderService orderService = new OrderService();
    static FinanceService financeService = new FinanceService();

    public static void main(String[] args) {
        
        login();
        
        int choice;

        do {

            menu();

            System.out.print("Choose: ");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1:
                    employeeMenu();
                    break;

                case 2:
                    productMenu();
                    break;

                case 3:
                    comboMenu();
                    break;

                case 4:
                    reservationMenu();
                    break;

                case 5:
                    orderMenu();
                    break;

                case 6:
                    financeMenu();
                    break;

                case 0:
                    System.out.println("Exit Program...");
                    break;

                default:
                    System.out.println("Invalid Choice!");

            }

        } while (choice != 0);

    }

    //========================================================

    public static void login() {

        String username;
        String password;

        System.out.println("======================================");
        System.out.println("      TEA HOUSE MANAGEMENT SYSTEM");
        System.out.println("======================================");

        while (true) {

            System.out.print("Username: ");
            username = sc.nextLine();

            System.out.print("Password: ");
            password = sc.nextLine();

            // Sau này thay bằng AccountService.login()

            if (username.equals("admin") && password.equals("123")) {

                System.out.println("Login Successfully!");
                break;

            }

            System.out.println("Wrong Username or Password!");
        }

    }

    //========================================================

    public static void menu() {

        System.out.println();
        System.out.println("============== MAIN MENU ==============");

        System.out.println("1. Employee Management");

        System.out.println("2. Product Management");

        System.out.println("3. Combo Management");

        System.out.println("4. Reservation Management");

        System.out.println("5. Order Management");

        System.out.println("6. Finance Management");

        System.out.println("0. Exit");

        System.out.println("=======================================");
    }

    //========================================================

    public static void employeeMenu() {

        int choose;

        do {

            System.out.println("\n====== EMPLOYEE MANAGEMENT ======");

            System.out.println("1. Add Employee");

            System.out.println("2. Delete Employee");

            System.out.println("3. Update Employee");

            System.out.println("4. Search Employee");

            System.out.println("5. Display All");

            System.out.println("6. Sort Salary");

            System.out.println("0. Back");

            System.out.print("Choose: ");

            choose = Integer.parseInt(sc.nextLine());

            switch (choose) {

                case 1:

                    //employeeService.addEmployee(...);

                    break;

                case 2:

                    break;

                case 3:

                    break;

                case 4:

                    break;

                case 5:

                    employeeService.displayAll();

                    break;

                case 6:

                    employeeService.sortBySalary();

                    break;

            }

        } while (choose != 0);

    }
    public static void productMenu() {
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
                System.out.println("❌ Lỗi: Vui lòng nhập một số nguyên!");
                choose = -1;
                continue;
            }

            switch (choose) {
                case 1:
    System.out.println("\n--- Select Product Type To Add ---");
    System.out.println("1. Tea (Trà)");
    System.out.println("2. TeaWare (Trà cụ)");
    System.out.println("3. Accessory (Phụ kiện)");
    System.out.print("Choose type (1-3): ");
    int type = Integer.parseInt(sc.nextLine());

    System.out.print("Enter ID: ");
    String id = sc.nextLine();
    System.out.print("Enter Name: ");
    String name = sc.nextLine();
    System.out.print("Enter Price: ");
    double price = Double.parseDouble(sc.nextLine());

    if (type == 1) {
        System.out.print("Enter Tea Type: ");
        String teaType = sc.nextLine();
        
        // 🌟 GỌI SERVICE Ở ĐÂY ĐỂ LƯU DỮ LIỆU
        productService.addTea(id, name, price, teaType); 

    } else if (type == 2) {
        System.out.print("Enter Ware Type (e.g., Ấm trà): ");
        String wareType = sc.nextLine();
        System.out.print("Enter Clay Type: ");
        String clayType = sc.nextLine();
        System.out.print("Enter Design: ");
        String design = sc.nextLine();
        System.out.print("Enter Capacity (ml): ");
        int capacity = Integer.parseInt(sc.nextLine());
        
        // 🌟 GỌI SERVICE Ở ĐÂY ĐỂ LƯU DỮ LIỆU
        productService.addTeaWare(id, name, price, wareType, clayType, design, capacity);

    } else if (type == 3) {
        System.out.print("Enter Accessory Type: ");
        String accessoryType = sc.nextLine();
        
        // 🌟 GỌI SERVICE Ở ĐÂY ĐỂ LƯU DỮ LIỆU
        productService.addAccessory(id, name, price, accessoryType);
    } else {
        System.out.println("Invalid type!");
    }
    break;

                case 2:
                    System.out.print("Enter ID to delete: ");
                    String idDelete = sc.nextLine();
                    productService.deleteProductById(idDelete);
                    break;

                case 3:
                    System.out.print("Enter ID to search: ");
                    String idSearch = sc.nextLine();
                    // In trực tiếp kết quả trả về, nếu tìm thấy tự gọi toString(), nếu null sẽ hiện chữ null
                    System.out.println(productService.findProductById(idSearch));
                    break;

                case 4:
                    productService.printAllProducts();
                    break;

                case 5:
                    System.out.println("\n--- Filter Category ---");
                    System.out.println("1. Show Only Teas (Trà)");
                    System.out.println("2. Show Only TeaWares (Trà cụ)");
                    System.out.println("3. Show Only Accessories (Phụ kiện)");
                    System.out.print("Choose filter (1-3): ");
                    int filterChoose = Integer.parseInt(sc.nextLine());
                    
                    if (filterChoose == 1) {
                        System.out.println(productService.getOnlyTeas()); 
                    } else if (filterChoose == 2) {
                        System.out.println(productService.getOnlyTeaWares());
                    } else if (filterChoose == 3) {
                        System.out.println(productService.getOnlyAccessories());
                    } else {
                        System.out.println("❌ Lựa chọn lọc không hợp lệ!");
                    }
                    break;
                    
                case 0:
                    System.out.println("Returning to main menu...");
                    break;
            }
        } while (choose != 0);
    }

    //========================================================

  

    public static void comboMenu() {

        System.out.println("COMBO MANAGEMENT");

        // gọi ComboService

    }

    //========================================================

    public static void reservationMenu() {

        System.out.println("RESERVATION MANAGEMENT");

        // gọi ReservationService

    }

    //========================================================

    public static void orderMenu() {

        System.out.println("ORDER MANAGEMENT");

        // gọi OrderService

    }

    //========================================================

    public static void financeMenu() {

        financeService.displayFinancialReport();

    }

}