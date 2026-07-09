package employee;

public class Cashier extends Employee {

    public Cashier() {
        super();
    }

    public Cashier(String employeeId, String fullName, String gender, int age, 
                   String phone, double salary, String shift, String hireDate) {
        super(employeeId, fullName, gender, age, phone, salary, shift, hireDate);
    }

    @Override
    public String getRole() {
        return "Cashier"; // 1 từ -> Lớp cha tự cắt ra chữ "CA"
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