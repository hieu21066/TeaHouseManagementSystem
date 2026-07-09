package employee;

public class TeaServant extends Employee {

    public TeaServant() {
        super();
    }

    public TeaServant(String employeeId, String fullName, String gender, int age, 
                      String phone, double salary, String shift, String hireDate) {
        super(employeeId, fullName, gender, age, phone, salary, shift, hireDate);
    }

    @Override
    public String getRole() {
        return "TeaServant"; 
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