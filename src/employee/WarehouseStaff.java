package employee;

public class WarehouseStaff extends Employee {

    // Constructor mặc định
    public WarehouseStaff() {
        super();
    }

    // Constructor đầy đủ
    public WarehouseStaff(String employeeId,
                          String fullName,
                          String gender,
                          int age,
                          String phone,
                          String address,
                          double salary,
                          String shift,
                          String status,
                          String hireDate) {

        super(employeeId,
              fullName,
              gender,
              age,
              phone,
              address,
              salary,
              shift,
              status,
              hireDate);
    }

    @Override
    public String getRole() {
        return "WarehouseStaff";
    }
}