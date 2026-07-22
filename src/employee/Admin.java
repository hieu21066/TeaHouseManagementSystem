package employee;

public class Admin extends Employee {

    //========================== Constructor ==========================
    public Admin() {
        super();
    }

    public Admin(String employeeId,
            String fullName,
            String gender,
            int age,
            String phone,
            double salary,
            String shift,
            String hireDate) {
        super(employeeId, fullName, gender, age, phone,
                salary, shift, hireDate);
    }

    @Override
    public String getRole() {
        return "Admin";
    }

    @Override
    public void input() {
        super.input();
    }
}
