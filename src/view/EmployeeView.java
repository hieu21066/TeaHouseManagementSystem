package view;

import employee.*;
import service.EmployeeService;
import java.util.Scanner;

public class EmployeeView {

    private EmployeeService employeeService;
    private Scanner sc;

    public EmployeeView(EmployeeService employeeService) {
        this.employeeService = employeeService;
        sc = new Scanner(System.in);
    }

    public void run() {

        int choice;

        do {

            menu();

            System.out.print("Choose: ");
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1:
                    addEmployee();
                    break;

                case 2:
                    deleteEmployee();
                    break;

                case 3:
                    updateEmployee();
                    break;

                case 4:
                    searchEmployee();
                    break;

                case 5:
                    employeeService.displayAll();
                    break;

                case 6:
                    employeeService.displayByType(Admin.class);
                    break;

                case 7:
                    employeeService.displayByType(Cashier.class);
                    break;

                case 8:
                    employeeService.displayByType(TeaMaster.class);
                    break;

                case 9:
                    employeeService.displayByType(TeaLady.class);
                    break;

                case 10:
                    employeeService.displayByType(TeaServant.class);
                    break;

                case 11:
                    employeeService.displayByType(WarehouseStaff.class);
                    break;

                case 12:
                    employeeService.sortBySalary();
                    System.out.println("Sorted Successfully!");
                    employeeService.displayAll();
                    break;

                case 0:
                    System.out.println("Back...");
                    break;

                default:
                    System.out.println("Invalid choice!");

            }

        } while (choice != 0);

    }

    //=================================================

    private void menu() {

        System.out.println("\n========== EMPLOYEE MANAGEMENT ==========");
        System.out.println("1. Add Employee");
        System.out.println("2. Delete Employee");
        System.out.println("3. Update Employee");
        System.out.println("4. Search Employee");
        System.out.println("5. Display All");
        System.out.println("-----------------------------------------");
        System.out.println("6. Display Admin");
        System.out.println("7. Display Cashier");
        System.out.println("8. Display Tea Master");
        System.out.println("9. Display Tea Lady");
        System.out.println("10. Display Tea Servant");
        System.out.println("11. Display Warehouse Staff");
        System.out.println("-----------------------------------------");
        System.out.println("12. Sort By Salary");
        System.out.println("0. Back");
        System.out.println("=========================================");

    }

    //=================================================

    private void addEmployee() {

        System.out.println("----- Select Employee Type -----");
        System.out.println("1. Admin");
        System.out.println("2. Cashier");
        System.out.println("3. Tea Master");
        System.out.println("4. Tea Lady");
        System.out.println("5. Tea Servant");
        System.out.println("6. Warehouse Staff");

        System.out.print("Choose: ");
        int type = Integer.parseInt(sc.nextLine());

        // Sau này nhập đầy đủ thông tin
        System.out.println("Add Employee Function...");

    }

    //=================================================

    private void deleteEmployee() {

        System.out.print("Enter Employee ID: ");

        String id = sc.nextLine();

        if (employeeService.deleteEmployee(id))
            System.out.println("Delete Successfully!");
        else
            System.out.println("Employee Not Found!");

    }

    //=================================================

    private void updateEmployee() {

        System.out.println("Update Employee Function...");

    }

    //=================================================

    private void searchEmployee() {

        System.out.print("Enter Employee ID: ");

        String id = sc.nextLine();

        Employee employee = employeeService.searchById(id);

        if (employee == null) {

            System.out.println("Employee Not Found!");

        } else {

            Employee.displayHeader();
            employee.display();

        }

    }

}