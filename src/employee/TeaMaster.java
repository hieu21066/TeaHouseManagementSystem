package employee;

import java.util.Scanner;

public class TeaMaster extends Employee {

    //========================== Attributes ==========================
    private int yearsOfExperience;
    private String title; // Chức danh: Beginner, Experienced, Professional

    //========================== Constructor ==========================
    public TeaMaster() {
        super();
    }

    public TeaMaster(String employeeId, String fullName, String gender, int age, 
                     String phone, double salary, String shift, 
                     String hireDate, int yearsOfExperience) {
        super(employeeId, fullName, gender, age, phone, salary, shift,hireDate);
        this.yearsOfExperience = yearsOfExperience;
        this.title = determineTitle(yearsOfExperience);
    }

    //========================== Getter & Setter ==========================
    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
        this.title = determineTitle(yearsOfExperience);
    }

    public String getTitle() {
        return title;
    }

    private String determineTitle(int years) {
        if (years < 2) {
            return "Beginner";
        } else if (years <= 5) {
            return "Experienced";
        } else {
            return "Professional";
        }
    }

    @Override
    public String getRole() {
        return "TeaMaster";
    }

    @Override
    public void input() {
        super.input();
        Scanner sc = new Scanner(System.in);
        System.out.print("Years of Experience: ");
        this.yearsOfExperience = sc.nextInt();
        sc.nextLine();
        this.title = determineTitle(this.yearsOfExperience);
    }

    @Override
    public String toString() {
        return super.toString() + "|" + yearsOfExperience + "|" + title;
    }
}