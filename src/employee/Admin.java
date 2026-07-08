package employee;


public class Admin extends Employee {

    //========================== Constructor ==========================
    public Admin() {
        super();
    }

    // Constructor đầy đủ tham số (đã loại bỏ hoàn toàn thuộc tính address)
    public Admin(String employeeId, String fullName, String gender,
                 int age, String phone, double salary, 
                 String shift, String status, String hireDate) {

        super(employeeId, fullName, gender, age, phone, 
              salary, shift, status, hireDate);
    }

    //========================== Override Chức vụ ==========================
    @Override
    public String getRole() {
        return "Admin"; // 1 từ -> Thuật toán lớp cha sẽ tự cắt ra chữ "AD"
    }

    //========================== Override Nhập liệu ==========================
    @Override
    public void input() {
        super.input(); // Gọi lớp cha để nhập tất cả thông tin chung công khai
        
        // Nếu sau này Admin có thêm thuộc tính riêng (Ví dụ: bộ phận quản lý hệ thống), 
        // bạn sẽ dùng Scanner nhập thêm ở đây tương tự như thuộc tính height/material của lớp Vase.
    }
}