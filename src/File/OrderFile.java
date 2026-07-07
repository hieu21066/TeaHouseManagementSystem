package file;

import order.Invoice;

import java.io.*;
import java.util.ArrayList;

public class OrderFile {

    private static final String FILE_NAME = "Order.txt";

    //==================== LOAD ====================
    public static ArrayList<Invoice> load() {

        ArrayList<Invoice> list = new ArrayList<>();

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return list;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split("\\|");

                String invoiceId = data[0];
                String employeeName = data[1];

                Invoice invoice = new Invoice(invoiceId, employeeName);

                // Tổng tiền sẽ tự tính lại từ danh sách món,
                // nên file chỉ lưu ID và nhân viên.

                list.add(invoice);
            }

        } catch (Exception e) {
            System.out.println("Load Order.txt failed!");
        }

        return list;
    }

    //==================== SAVE ====================
    public static void save(ArrayList<Invoice> list) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (Invoice invoice : list) {

                bw.write(invoice.toString());
                bw.newLine();

            }

        } catch (Exception e) {
            System.out.println("Save Order.txt failed!");
        }
    }

}
