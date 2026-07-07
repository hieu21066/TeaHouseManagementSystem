package main;

import java.util.Scanner;

import service.*;

import view.ComboView;
import view.EmployeeView;
import view.FinanceView;
import view.OrderView;
import view.ProductView;
import view.ReservationView;

public class App {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        //================ SERVICE =================
        AccountService accountService = new AccountService();
        EmployeeService employeeService = new EmployeeService();
        ProductService productService = new ProductService();
        ComboService comboService = new ComboService();
        OrderService orderService = new OrderService();
        ReservationService reservationService = new ReservationService();
        FinanceService financeService = new FinanceService();

        while (true) {

            System.out.println("\n======================================");
            System.out.println("      TEA HOUSE MANAGEMENT SYSTEM");
            System.out.println("======================================");
            System.out.println("1. Login");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            int choose;

            try {
                choose = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid choice!");
                continue;
            }

            if (choose == 0) {
                System.out.println("Good Bye!");
                break;
            }

            if (choose != 1) {
                System.out.println("Invalid choice!");
                continue;
            }

            //---------------- LOGIN ----------------

            System.out.print("Username: ");
            String username = sc.nextLine();

            System.out.print("Password: ");
            String password = sc.nextLine();

            if (!accountService.login(username, password)) {
                System.out.println("Login Failed!");
                continue;
            }

            System.out.println("Login Successfully!");

            //---------------- MAIN MENU ----------------

            int menu;

            do {

                System.out.println("\n============= MAIN MENU =============");
                System.out.println("1. Employee Management");
                System.out.println("2. Product Management");
                System.out.println("3. Combo Management");
                System.out.println("4. Order Management");
                System.out.println("5. Reservation Management");
                System.out.println("6. Finance Management");
                System.out.println("0. Logout");
                System.out.println("=====================================");
                System.out.print("Choose: ");

                try {
                    menu = Integer.parseInt(sc.nextLine());
                } catch (Exception e) {
                    System.out.println("Invalid choice!");
                    menu = -1;
                    continue;
                }

                switch (menu) {

                    case 1:
                        new EmployeeView(employeeService).run();
                        break;

                    case 2:
                        new ProductView(productService).run();
                        break;

                    case 3:
                        ComboView.comboMenu(sc, comboService);
                        break;

                    case 4:
                        OrderView.orderMenu(sc, orderService);
                        break;

                    case 5:
                        new ReservationView(reservationService).menu();
                        break;

                    case 6:
                        FinanceView.displayFinancialReport(sc, financeService);
                        break;

                    case 0:
                        System.out.println("Logout Successfully!");
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }

            } while (menu != 0);

        }

        sc.close();
    }
}