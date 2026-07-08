package employee;

public class TeaLady extends Employee {

    public TeaLady() {
        super();
    }

    public TeaLady(String employeeId, String fullName, String gender, int age, 
                   String phone, double salary, String shift, String status, String hireDate) {
        super(employeeId, fullName, gender, age, phone, salary, shift, status, hireDate);
    }

    @Override
    public String getRole() {
        return "TeaLady"; // 2 từ -> Lớp cha tự cắt chữ cái đầu thành "TL"
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