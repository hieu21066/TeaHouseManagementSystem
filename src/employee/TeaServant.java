package employee;

public class TeaServant extends Employee {

    public TeaServant() {
        super();
    }

    public TeaServant(String employeeId, String fullName, String gender, int age, 
                      String phone, double salary, String shift, String status, String hireDate) {
        super(employeeId, fullName, gender, age, phone, salary, shift, status, hireDate);
    }

    @Override
    public String getRole() {
        return "TeaServant"; // 2 từ -> Lớp cha tự cắt chữ cái đầu thành "TS"
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