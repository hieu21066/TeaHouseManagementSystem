package view;

import employee.*;
import service.EmployeeService;
import java.util.Scanner;
import main.SystemClock;

public class EmployeeView {

    private EmployeeService employeeService;
    private Scanner sc;

    public EmployeeView(EmployeeService employeeService) {
        this.employeeService = employeeService;
        sc = new Scanner(System.in);
    }

    public void run() {
        int choice = -1;
        do {
            menu();
            System.out.print("Choose: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid choice! Please enter a number.");
                continue;
            }

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
                    updateRealtimeStatus();
                    employeeService.displayAllWithRealtimeStatus(sc);
                    break;
                case 6:
                     updateRealtimeStatus();
                    employeeService.displayByType(Admin.class);
                    break;
                case 7:
                     updateRealtimeStatus();
                    employeeService.displayByType(Cashier.class);
                    break;
                case 8:
                     updateRealtimeStatus();
                    employeeService.displayByType(TeaMaster.class);
                    break;
                case 9:
                     updateRealtimeStatus();
                    employeeService.displayByType(TeaLady.class);
                    break;
                case 10:
                     updateRealtimeStatus();
                    employeeService.displayByType(TeaServant.class);
                    break;
                case 11:
                     updateRealtimeStatus();
                    employeeService.displayByType(WarehouseStaff.class);
                    break;
                case 12:
                    employeeService.sortBySalary();
                    System.out.println("Sorted Successfully!");
                    employeeService.displayAllWithRealtimeStatus(sc);
                    break;
                case 0:
                    System.out.println("Back...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    private void menu() {
        System.out.println("\n========== EMPLOYEE MANAGEMENT ==========");
        System.out.println("1. Add Employee");
        System.out.println("2. Delete Employee");
        System.out.println("3. Update Employee");
        System.out.println("4. Search Employee");
        System.out.println("5. Display All (With Realtime Status)");
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

    // ================== BỔ SUNG: Hàm phụ trợ hỗ trợ cập nhật trạng thái trước khi hiển thị nhóm nhân viên ==================
    private void updateRealtimeStatus() {

    int hour = SystemClock.getCurrentHour();

    for (Employee employee : employeeService.getEmployeeList()) {
        employee.updateStatusBasedOnHour(hour);
    }
}

    // ================== ADD EMPLOYEE ==================
    private void addEmployee() {
        System.out.println("\n----- Select Employee Type -----");
        System.out.println("1. Admin");
        System.out.println("2. Cashier");
        System.out.println("3. Tea Master");
        System.out.println("4. Tea Lady");
        System.out.println("5. Tea Servant");
        System.out.println("6. Warehouse Staff");
        System.out.print("Choose: ");
        
        int type;
        try {
            type = Integer.parseInt(sc.nextLine());
            if (type < 1 || type > 6) {
                System.out.println("Invalid type choice!");
                return;
            }
        } catch (Exception e) {
            System.out.println("Invalid input! Return to menu.");
            return;
        }

        Employee newEmp = null;
        switch (type) {
            case 1: newEmp = new Admin(); break;
            case 2: newEmp = new Cashier(); break;
            case 3: newEmp = new TeaMaster(); break;
            case 4: newEmp = new TeaLady(); break;
            case 5: newEmp = new TeaServant(); break;
            case 6: newEmp = new WarehouseStaff(); break;
        }

        if (newEmp != null) {
            newEmp.input(); 

            int maxOrder = 0;
            String currentRole = newEmp.getRole(); 
            
            for (Employee e : employeeService.getEmployeeList()) {
                if (e.getRole().equalsIgnoreCase(currentRole)) {
                    String oldId = e.getEmployeeId(); 
                    try {
                        String numberPart = oldId.substring(oldId.length() - 3);
                        int order = Integer.parseInt(numberPart);
                        if (order > maxOrder) {
                            maxOrder = order;
                        }
                    } catch (Exception ex) {
                    }
                }
            }
            
            int nextOrder = maxOrder + 1;
            newEmp.generateId(nextOrder);
            employeeService.addEmployee(newEmp);
            System.out.println("Add Employee Successfully! ID generated: " + newEmp.getEmployeeId());
        }
    }

    // ================== DELETE EMPLOYEE ==================
    private void deleteEmployee() {
        System.out.print("Enter Employee ID: ");
        String id = sc.nextLine();

        if (employeeService.deleteEmployee(id))
            System.out.println("Delete Successfully!");
        else
            System.out.println("Employee Not Found!");
    }

    // ================== UPDATE EMPLOYEE ==================
    private void updateEmployee() {
        System.out.print("Enter Employee ID to update: ");
        String id = sc.nextLine().trim();

        Employee oldEmp = employeeService.searchById(id);
        if (oldEmp == null) {
            System.out.println("Employee Not Found!");
            return;
        }
 
        System.out.println("\n--- Enter New Information for Employee: " + oldEmp.getFullName() + " (" + oldEmp.getRole() + ") ---");
        
        Employee tempEmp = null;
        if (oldEmp instanceof Admin) tempEmp = new Admin();
        else if (oldEmp instanceof Cashier) tempEmp = new Cashier();
        else if (oldEmp instanceof TeaMaster) tempEmp = new TeaMaster();
        else if (oldEmp instanceof TeaLady) tempEmp = new TeaLady();
        else if (oldEmp instanceof TeaServant) tempEmp = new TeaServant();
        else if (oldEmp instanceof WarehouseStaff) tempEmp = new WarehouseStaff();

        if (tempEmp != null) {
            tempEmp.setEmployeeId(oldEmp.getEmployeeId());
            tempEmp.input();

            if (employeeService.updateEmployee(tempEmp)) {
                System.out.println("Update Employee Successfully!");
            } else {
                System.out.println("Update Failed!");
            }
        }
    }

    // ================== SEARCH EMPLOYEE ==================
    private void searchEmployee() {
        System.out.print("Enter Employee ID: ");
        String id = sc.nextLine();
        Employee employee = employeeService.searchById(id);

        if (employee == null) {
            System.out.println("Employee Not Found!");
        } else {
            System.out.print("Enter current hour to check this employee's status (0-23): ");
            try {
              employee.updateStatusBasedOnHour(SystemClock.getCurrentHour());
            } 
            catch (Exception e) {}
            
            Employee.displayHeader();
            employee.display();
        }
    }
}