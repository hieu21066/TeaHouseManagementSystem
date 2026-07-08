package employee;

import java.util.Scanner;

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
                    int age, String phone, double salary, String shift,
                    String status, String hireDate) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
        this.salary = salary;
        this.shift = shift;
        this.status = status;
        this.hireDate = hireDate;
    }

    //========================== Getter & Setter ==========================
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
        String role = getRole(); // Lấy tên Role từ lớp con (Ví dụ: "Admin" hoặc "TeaLady")
        String prefix = "";

        // Thuật toán tự động lấy 2 chữ cái viết tắt dựa theo tên Role
        // Đếm số lượng chữ cái in hoa trong tên Role
        java.util.ArrayList<Character> upperCases = new java.util.ArrayList<>();
        for (int i = 0; i < role.length(); i++) {
            if (Character.isUpperCase(role.charAt(i))) {
                upperCases.add(role.charAt(i));
            }
        }

        if (upperCases.size() >= 2) {
            // Trường hợp 2 từ trở lên (Ví dụ: TeaLady -> T và L)
            prefix = "" + upperCases.get(0) + upperCases.get(1);
        } else if (role.length() >= 2) {
            // Trường hợp chỉ có 1 từ (Ví dụ: Admin -> Lấy AD)
            prefix = role.substring(0, 2).toUpperCase();
        } else {
            prefix = role.toUpperCase();
        }

        // Định dạng số thứ tự thành 3 chữ số (Ví dụ: 1 -> 001)
        String formattedOrder = String.format("%03d", nextOrder);

        // Gán mã ID hoàn chỉnh vào thuộc tính
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
        age = sc.nextInt();
        sc.nextLine(); // Clear bộ nhớ đệm

        System.out.print("Phone: ");
        phone = sc.nextLine();

        System.out.print("Salary: ");
        salary = sc.nextDouble();
        sc.nextLine(); // Clear bộ nhớ đệm

        System.out.print("Shift (sang/chieu/toi): ");
        shift = sc.nextLine().trim().toLowerCase();

        // Tự động tính toán status dựa trên ca làm việc nhập vào
        if (shift.equals("sang") || shift.equals("chieu") || shift.equals("toi")) {
            status = "Working";
        } else {
            status = "Off";
        }

        System.out.print("Hire Date (dd/mm/yyyy): ");
        hireDate = sc.nextLine();

    }

    //========================== Save File ==========================
    @Override
    public String toString() {
        return employeeId + "|"
                + getRole() + "|"
                + fullName + "|"
                + gender + "|"
                + age + "|"
                + phone + "|"
                + salary + "|"
                + shift + "|"
                + status + "|"
                + hireDate;
    }

    //========================== Display ==========================
    public static void displayHeader() {
        System.out.println("======================================================================================================================");
        System.out.printf("| %-6s | %-20s | %-16s | %-6s | %-3s | %-12s | %-10s | %-10s | %-10s |%n",
                "ID",
                "Name",
                "Role",
                "Gender",
                "Age",
                "Phone",
                "Salary",
                "Shift",
                "Status");
        System.out.println("======================================================================================================================");
    }

    public void display() {
        System.out.printf("| %-6s | %-20s | %-16s | %-6s | %-3d | %-12s | %-10.0f | %-10s | %-10s |%n",
                employeeId,
                fullName,
                getRole(),
                gender,
                age,
                phone,
                salary,
                shift,
                status);
    }
    // Thêm hàm này vào Employee.java thay cho hàm generateId cũ
   
}