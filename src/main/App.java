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

    //========================================================

    public static void productMenu() {

        System.out.println("PRODUCT MANAGEMENT");

        // gọi ProductService

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