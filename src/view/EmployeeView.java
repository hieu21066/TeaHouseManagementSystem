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

    // ================== CẬP NHẬT: ADD EMPLOYEE ==================
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

        // Khởi tạo đối tượng trống tương ứng với vai trò đã chọn
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
            // 1. Gọi hàm nhập dữ liệu từ bàn phím
            newEmp.input(); 

            // 2. TÌM SỐ THỨ TỰ LỚN NHẤT CỦA RIÊNG ROLE NÀY
            int maxOrder = 0;
            String currentRole = newEmp.getRole(); // Ví dụ: "Admin" hoặc "Cashier"
            
            for (Employee e : employeeService.getEmployeeList()) {
                // Chỉ xét những nhân viên có cùng Role với nhân viên đang chuẩn bị add
                if (e.getRole().equalsIgnoreCase(currentRole)) {
                    String oldId = e.getEmployeeId(); // Ví dụ: "AD001" hoặc "TL001"
                    
                    try {
                        // Lấy 3 ký tự cuối cùng của ID (luôn là phần số, ví dụ: "001" -> 1)
                        String numberPart = oldId.substring(oldId.length() - 3);
                        int order = Integer.parseInt(numberPart);
                        
                        if (order > maxOrder) {
                            maxOrder = order;
                        }
                    } catch (Exception ex) {
                        // Phòng trường hợp ID cũ bị lỗi định dạng không cắt được
                    }
                }
            }
            
            // Số thứ tự của Role này sẽ bằng số lớn nhất hiện tại + 1
            int nextOrder = maxOrder + 1;

            // 3. Truyền số thứ tự riêng biệt vào để sinh mã ID tương ứng
            newEmp.generateId(nextOrder);

            // 4. Lưu vào hệ thống
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

    // ================== CẬP NHẬT: UPDATE EMPLOYEE ==================
    private void updateEmployee() {
        System.out.print("Enter Employee ID to update: ");
        String id = sc.nextLine().trim();

        Employee oldEmp = employeeService.searchById(id);
        if (oldEmp == null) {
            System.out.println("Employee Not Found!");
            return;
        }

        System.out.println("\n--- Enter New Information for Employee: " + oldEmp.getFullName() + " (" + oldEmp.getRole() + ") ---");
        
        // Tạo một đối tượng sao chép tạm thời cùng loại với oldEmp để hứng thông tin mới nhập vào
        Employee tempEmp = null;
        if (oldEmp instanceof Admin) tempEmp = new Admin();
        else if (oldEmp instanceof Cashier) tempEmp = new Cashier();
        else if (oldEmp instanceof TeaMaster) tempEmp = new TeaMaster();
        else if (oldEmp instanceof TeaLady) tempEmp = new TeaLady();
        else if (oldEmp instanceof TeaServant) tempEmp = new TeaServant();
        else if (oldEmp instanceof WarehouseStaff) tempEmp = new WarehouseStaff();

        if (tempEmp != null) {
            // Giữ nguyên mã ID cũ cho đối tượng tạm
            tempEmp.setEmployeeId(oldEmp.getEmployeeId());
            
            // Gọi hàm input() để nhập đè thông tin mới
            tempEmp.input();

            // Truyền đối tượng chứa dữ liệu mới sang Service xử lý cập nhật gốc
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
            Employee.displayHeader();
            employee.display();
        }
    }
}