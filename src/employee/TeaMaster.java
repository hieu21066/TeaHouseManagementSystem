package employee;

public class TeaMaster extends Employee {

    private String level;

    public TeaMaster() {
    }

    public TeaMaster(String employeeId, String fullName, String gender,
                     int age, String phone, String address,
                     double salary, String shift,
                     String status, String hireDate,
                     String level) {

        super(employeeId, fullName, gender, age,
              phone, address, salary,
              shift, status, hireDate);

        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String getRole() {
        return "TeaMaster";
    }
}