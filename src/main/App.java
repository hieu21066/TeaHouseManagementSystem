package main;

import java.io.File; 
import java.util.Scanner;
import service.*;
import view.*;

public class App {

    private static final String FILE_NAME = "admin.txt"; 

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        EmployeeService employeeService = new EmployeeService();
        ProductService productService = new ProductService();
        ComboService comboService = new ComboService();
        OrderService orderService = new OrderService();
        ReservationService reservationService = new ReservationService();
        FinanceService financeService = new FinanceService();
         
        int choose = -1;

        do {
            System.out.println("\n=================================");
            System.out.println(" TEA HOUSE MANAGEMENT SYSTEM");
            System.out.println("=================================");
            System.out.println("1. Admin");
            System.out.println("2. Order");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            
            try {
                choose = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid Choice!");
                continue;
            }

            switch (choose) {
                //ADMIN
                case 1:
                    System.out.println("\n========== LOGIN ==========");
                    System.out.print("Username: ");
                    String inputUsername = sc.nextLine();
                    System.out.print("Password: ");
                    String inputPassword = sc.nextLine();
                    
                    boolean isLoginSuccess = false;
                    try {
                        File file = new File(FILE_NAME); 
                        if (file.exists()) {
                            Scanner fileReader = new Scanner(file);
                            if (fileReader.hasNextLine()) {
                                String correctUsername = fileReader.nextLine().trim();
                                if (fileReader.hasNextLine()) {
                                    String correctPassword = fileReader.nextLine().trim();
                                    if (inputUsername.equals(correctUsername) && inputPassword.equals(correctPassword)) {
                                        isLoginSuccess = true;
                                    }
                                }
                            }
                            fileReader.close();
                        } else {
                            System.out.println("Not found " + FILE_NAME);
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("[Lỗi] Đã xảy ra lỗi khi đọc file tài khoản!");
                        break;
                    }
                    // --------------------------------------------------------

                    // Kiểm tra kết quả flag login
                    if (!isLoginSuccess) {
                        System.out.println("Login Failed! Wrong Username or Password.");
                        break;
                    }

                    System.out.println("Login Successfully!");

                    adminMenu(sc,
                            employeeService,
                            productService,
                            comboService,
                            reservationService,
                            orderService,
                            financeService);

                    break;

                //================ ORDER =================
                case 2:
                    System.out.println("\n====== ORDER MODE ======");
                    OrderView.orderMenu(sc, orderService);
                    break;

                case 0:
                    System.out.println("Good Bye!");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choose != 0);

        sc.close();
    }

    //=========================================================
    public static void adminMenu(
            Scanner sc,
            EmployeeService employeeService,
            ProductService productService,
            ComboService comboService,
            ReservationService reservationService,
            OrderService orderService,
            FinanceService financeService) {

        int menu = -1;

        do {
            System.out.println("\n============= ADMIN MENU =============");
            System.out.println("1. Employee Management");
            System.out.println("2. Product Management");
            System.out.println("3. Combo Management");
            System.out.println("4. Reservation Management");
            System.out.println("5. Order Management");
            System.out.println("6. Finance Management");
            System.out.println("0. Logout");
            System.out.print("Choose: ");

            try {
                menu = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid Choice!");
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
                    new ReservationView(reservationService).menu();
                    break;
                case 5:
                    OrderView.orderMenu(sc, orderService);
                    break;
                case 6:
                    FinanceView.displayFinancialReport(sc, financeService, orderService, productService, comboService);
                    break;
                case 0:
                    System.out.println("Logout Successfully!");
                    break;
                default:
                    System.out.println("Invalid Choice!");
            }

        } while (menu != 0);
    }
}