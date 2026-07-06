package employee;

public abstract class Employee {

    //========================== Attributes ==========================

    private String employeeId;
    private String fullName;
    private String gender;
    private int age;
    private String phone;
    private String address;
    private double salary;
    private String shift;
    private String status;
    private String hireDate;

    //========================== Constructor ==========================

    public Employee() {
    }

    public Employee(String employeeId, String fullName, String gender,
                    int age, String phone, String address,
                    double salary, String shift,
                    String status, String hireDate) {

        this.employeeId = employeeId;
        this.fullName = fullName;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
        this.address = address;
        this.salary = salary;
        this.shift = shift;
        this.status = status;
        this.hireDate = hireDate;
    }

    //========================== Getter & Setter ==========================

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    //========================== Abstract ==========================

    public abstract String getRole();

    //========================== Save File ==========================

    @Override
    public String toString() {

        return employeeId + "|"
                + getRole() + "|"
                + fullName + "|"
                + gender + "|"
                + age + "|"
                + phone + "|"
                + address + "|"
                + salary + "|"
                + shift + "|"
                + status + "|"
                + hireDate;
    }

    //========================== Display ==========================

    public static void displayHeader() {

        System.out.println("====================================================================================================================================");
        System.out.printf("| %-6s | %-20s | %-16s | %-6s | %-3s | %-12s | %-15s | %-10s | %-10s | %-10s |%n",
                "ID",
                "Name",
                "Role",
                "Gender",
                "Age",
                "Phone",
                "Address",
                "Salary",
                "Shift",
                "Status");

        System.out.println("====================================================================================================================================");
    }

    public void display() {

        System.out.printf("| %-6s | %-20s | %-16s | %-6s | %-3d | %-12s | %-15s | %-10.0f | %-10s | %-10s |%n",
                employeeId,
                fullName,
                getRole(),
                gender,
                age,
                phone,
                address,
                salary,
                shift,
                status);

    }

}