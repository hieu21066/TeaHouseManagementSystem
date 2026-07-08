package employee;

public class WarehouseStaff extends Employee {

    public WarehouseStaff() {
        super();
    }

    public WarehouseStaff(String employeeId, String fullName, String gender, int age, 
                          String phone, double salary, String shift, String status, String hireDate) {
        super(employeeId, fullName, gender, age, phone, salary, shift, status, hireDate);
    }

    @Override
    public String getRole() {
        return "WarehouseStaff"; // 2 từ -> Lớp cha tự cắt chữ cái đầu thành "WS"
    }

    @Override
    public void input() {
        super.input();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}