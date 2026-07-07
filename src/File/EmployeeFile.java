package file;

import employee.*;

import java.io.*;
import java.util.ArrayList;

public class EmployeeFile {

    private static final String FILE_NAME = "Employee.txt";

    //================ LOAD =================

    public static ArrayList<Employee> load() {

        ArrayList<Employee> list = new ArrayList<>();

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return list;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split("\\|");

                String id = data[0];
                String role = data[1];
                String name = data[2];
                String gender = data[3];
                int age = Integer.parseInt(data[4]);
                String phone = data[5];
                String address = data[6];
                double salary = Double.parseDouble(data[7]);
                String shift = data[8];
                String status = data[9];
                String hireDate = data[10];

                Employee employee = null;

                switch (role.toLowerCase()) {

                    case "admin":
                        employee = new Admin(id, name, gender, age,
                                phone, address,
                                salary, shift,
                                status, hireDate);
                        break;

                    case "cashier":
                        employee = new Cashier(id, name, gender, age,
                                phone, address,
                                salary, shift,
                                status, hireDate);
                        break;

                    case "tea master":
                        employee = new TeaMaster(id, name, gender, age,
                                phone, address,
                                salary, shift,
                                status, hireDate);
                        break;

                    case "tea lady":
                        employee = new TeaLady(id, name, gender, age,
                                phone, address,
                                salary, shift,
                                status, hireDate);
                        break;

                    case "tea servant":
                        employee = new TeaServant(id, name, gender, age,
                                phone, address,
                                salary, shift,
                                status, hireDate);
                        break;

                    case "warehouse staff":
                        employee = new WarehouseStaff(id, name, gender, age,
                                phone, address,
                                salary, shift,
                                status, hireDate);
                        break;
                }

                if (employee != null) {
                    list.add(employee);
                }

            }

        } catch (Exception e) {
            System.out.println("Load Employee.txt failed!");
        }

        return list;

    }

    //================ SAVE =================

    public static void save(ArrayList<Employee> list) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (Employee employee : list) {

                bw.write(employee.toString());

                bw.newLine();

            }

        } catch (Exception e) {

            System.out.println("Save Employee.txt failed!");

        }

    }

}