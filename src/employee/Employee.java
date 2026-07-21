package employee;

import java.util.Scanner;
import java.text.DecimalFormat;

public abstract class Employee {

    //========================== Attributes ==========================
    private String employeeId;
    private String fullName;
    private String gender;
    private int age;
    private String phone;
    private double salary;
    private String shift;
    private String status;
    private String hireDate;

    //========================== Constructor ==========================
    public Employee() {
    }

    public Employee(String employeeId, String fullName, String gender,
            int age, String phone, double salary, String shift, String hireDate) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
        this.salary = salary;
        this.shift = shift;
        this.hireDate = hireDate;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getFormattedSalary() {
        DecimalFormat formatter = new DecimalFormat("###,###");
        return formatter.format(salary * 1000000);
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    //========================== Abstract Methods ==========================
    public abstract String getRole();

    public void generateId(int nextOrder) {
        String role = getRole();
        String prefix = "";

        java.util.ArrayList<Character> upperCases = new java.util.ArrayList<>();
        for (int i = 0; i < role.length(); i++) {
            if (Character.isUpperCase(role.charAt(i))) {
                upperCases.add(role.charAt(i));
            }
        }

        if (upperCases.size() >= 2) {
            prefix = "" + upperCases.get(0) + upperCases.get(1);
        } else if (role.length() >= 2) {
            prefix = role.substring(0, 2).toUpperCase();
        } else {
            prefix = role.toUpperCase();
        }

        String formattedOrder = String.format("%03d", nextOrder);
        this.setEmployeeId(prefix + formattedOrder);
    }

    //========================== Input Method ==========================
    public void input() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Full Name: ");
        fullName = sc.nextLine();

        System.out.print("Gender: ");
        gender = sc.nextLine();

        System.out.print("Age: ");
        try {
            age = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            age = 0;
        }

        System.out.print("Phone: ");
        phone = sc.nextLine();

        System.out.print("Salary: ");
        try {
            salary = Double.parseDouble(sc.nextLine());
        } catch (Exception e) {
            salary = 0;
        }

        // CHỈNH SỬA: Nhập ca theo dạng hour-hour
        System.out.print("Shift (h-h) ");
        shift = sc.nextLine().trim();

        status = "Unknown";

        System.out.print("Hire Date (dd/mm/yyyy): ");
        hireDate = sc.nextLine();
    }

    public void updateStatusBasedOnHour(int currentHour) {
        try {
            String[] hours = this.shift.split("-");
            if (hours.length == 2) {
                int startHour = Integer.parseInt(hours[0].trim());
                int endHour = Integer.parseInt(hours[1].trim());

                if (startHour < endHour) {
                    if (currentHour >= startHour && currentHour < endHour) {
                        this.status = "Working";
                    } else {
                        this.status = "Off";
                    }
                } // Trường hợp ca làm qua đêm (Ví dụ: ca từ 22h đêm đến 6h sáng hôm sau)
                else {
                    if (currentHour >= startHour || currentHour < endHour) {
                        this.status = "Working";
                    } else {
                        this.status = "Off";
                    }
                }
            } else {
                this.status = "Invalid Shift";
            }
        } catch (Exception e) {
            this.status = "Invalid Shift";
        }
    }

    //========================== Save File ==========================
    @Override
    public String toString() {
        // Lưu lương dưới dạng số double nguyên bản
        return employeeId + "|" + getRole() + "|" + fullName + "|" + gender + "|"
                + age + "|" + phone + "|" + salary + "|" + shift + "|" + hireDate;
    }

    //========================== Display ==========================
    public static void displayHeader() {
        System.out.println("======================================================================================================================");
        // Tăng độ rộng cột Salary từ 10 lên 12 hoặc 14
        System.out.printf("| %-6s | %-20s | %-16s | %-6s | %-3s | %-12s | %-12s | %-10s | %-10s |%n",
                "ID", "Name", "Role", "Gender", "Age", "Phone", "Salary", "Shift", "Status");
        System.out.println("======================================================================================================================");
    }

    public void display() {
        System.out.printf("| %-6s | %-20s | %-16s | %-6s | %-3d | %-12s | %-12s | %-10s | %-10s |%n",
                employeeId, fullName, getRole(), gender, age, phone, getFormattedSalary(), shift, status);
    }
}
