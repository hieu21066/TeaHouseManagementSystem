package view;

import finance.Finance;
import service.*;
import java.util.Scanner;

public class FinanceView {

    public static void displayFinancialReport(Scanner sc, FinanceService financeService, OrderService orderService, ProductService productService, ComboService comboService, EmployeeService employeeService) {
        financeService.setFinanceList(file.FinanceFile.load());
        
        int choose;
        do {
            System.out.println("\n====== FINANCE MANAGEMENT ======");
            System.out.println("1. Display Financial List");
            System.out.println("2. Create New Financial Period");
            System.out.println("3. Add Revenue (Manual)");
            System.out.println("4. Add Expense (Manual)");
            System.out.println("5. Auto Calculate Profit");
            System.out.println("0. Back");
            System.out.println("================================");
            System.out.print("Choose: ");
            
            try {
                choose = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number!");
                choose = -1; 
                continue;
            }

            switch (choose) {
                case 1: 
                    System.out.println("\n--- FINANCIAL REPORT LIST ---");
                    financeService.displayAll(); 
                    break;
                case 2: handleCreateFinance(sc, financeService); break;
                case 3: handleAddRevenue(sc, financeService); break;
                case 4: handleAddExpense(sc, financeService); break;
                case 5: 
                    handleAutoCalculate(sc, financeService, orderService, productService, comboService, employeeService); 
                    break;
                case 0:
                    file.FinanceFile.save(financeService.getFinanceList());
                    System.out.println("Returning to main menu..."); 
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choose != 0);
    }

    private static void handleCreateFinance(Scanner sc, FinanceService financeService) {
        System.out.print("Enter Finance ID (e.g., F2026_01): ");
        String id = sc.nextLine().trim();
        if (financeService.addFinance(new Finance(id, 0, 0))) {
            System.out.println("Success: Financial period created.");
            file.FinanceFile.save(financeService.getFinanceList());
        } else {
            System.out.println("Error: Finance ID already exists!");
        }
    }

    private static void handleAddRevenue(Scanner sc, FinanceService financeService) {
        System.out.print("Enter Finance ID: ");
        String id = sc.nextLine().trim();
        System.out.print("Enter Revenue Amount: ");
        try {
            double amount = Double.parseDouble(sc.nextLine());
            if (financeService.addRevenue(id, amount)) {
                System.out.println("Success: Revenue updated.");
                file.FinanceFile.save(financeService.getFinanceList());
            } else {
                System.out.println("Error: Finance ID not found!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid amount input!");
        }
    }

    private static void handleAddExpense(Scanner sc, FinanceService financeService) {
        System.out.print("Enter Finance ID: ");
        String id = sc.nextLine().trim();
        System.out.print("Enter Expense Amount: ");
        try {
            double amount = Double.parseDouble(sc.nextLine());
            if (financeService.addExpense(id, amount)) {
                System.out.println("Success: Expense updated.");
                file.FinanceFile.save(financeService.getFinanceList());
            } else {
                System.out.println("Error: Finance ID not found!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid amount input!");
        }
    }

    private static void handleAutoCalculate(Scanner sc, FinanceService financeService, OrderService orderService, ProductService productService, ComboService comboService, EmployeeService employeeService) {
        System.out.print("Enter Finance ID to sync: ");
        String id = sc.nextLine().trim();

        System.out.println("Processing data from system files...");
        
        if (financeService.autoCalculateFinance(id, orderService, productService, comboService, employeeService)) {
            System.out.println("Synchronization completed successfully!");
            
            System.out.println("\n--- AUTO REPORT FOR PERIOD [" + id + "] ---");
            financeService.displayById(id);
            
            file.FinanceFile.save(financeService.getFinanceList());
        } else {
            System.out.println("Error: Finance ID not found!");
        }
    }
}