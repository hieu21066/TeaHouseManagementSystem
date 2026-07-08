package employee;

import java.util.Scanner;

public class TeaMaster extends Employee {

    //========================== Attributes ==========================
    private int yearsOfExperience;
    private String title; // Chức danh: Beginner, Experienced, Professional

    //========================== Constructor ==========================
    public TeaMaster() {
        super();
    }

    public TeaMaster(String employeeId, String fullName, String gender, int age, 
                     String phone, double salary, String shift, String status, 
                     String hireDate, int yearsOfExperience) {
        super(employeeId, fullName, gender, age, phone, salary, shift, status, hireDate);
        this.yearsOfExperience = yearsOfExperience;
        // Tự động gán chức danh dựa vào số năm kinh nghiệm khi khởi tạo có tham số
        this.title = determineTitle(yearsOfExperience);
    }

    //========================== Getter & Setter ==========================
    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
        this.title = determineTitle(yearsOfExperience); // Cập nhật lại chức danh nếu số năm thay đổi
    }

    public String getTitle() {
        return title;
    }

    // Hàm phụ trợ tự động tính toán chức danh (Helper Method)
    private String determineTitle(int years) {
        if (years < 2) {
            return "Beginner";
        } else if (years <= 5) {
            return "Experienced";
        } else {
            return "Professional";
        }
    }

    //========================== Override Chức vụ ==========================
    @Override
    public String getRole() {
        return "TeaMaster"; // Viết liền PascalCase -> Lớp cha tự cắt ra chữ "TM"
    }

    //========================== Override Nhập liệu ==========================
    @Override
    public void input() {
        super.input(); // Nhập các thông tin chung của Employee trước
        
        Scanner sc = new Scanner(System.in);
        System.out.print("Years of Experience: ");
        this.yearsOfExperience = sc.nextInt();
        sc.nextLine(); // Clear bộ nhớ đệm
        
        // Tự động phân cấp chức danh dựa trên số năm kinh nghiệm vừa nhập
        this.title = determineTitle(this.yearsOfExperience);
    }

    //========================== Override Chi tiết ==========================
    @Override
    public String toString() {
        // Kế thừa chuỗi thông tin của cha và nối thêm phần riêng biệt
        return super.toString() + "|" + yearsOfExperience + "|" + title;
    }
}