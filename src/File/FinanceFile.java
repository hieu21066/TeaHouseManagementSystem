package file;

import finance.Finance;

import java.io.*;
import java.util.ArrayList;

public class FinanceFile {

    private static final String FILE_NAME = "Finance.txt";

    //================ LOAD =================
    public static ArrayList<Finance> load() {

        ArrayList<Finance> list = new ArrayList<>();

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return list;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split("\\|");

                String financeId = data[0];
                double totalRevenue = Double.parseDouble(data[1]);
                double totalExpense = Double.parseDouble(data[2]);

                Finance finance = new Finance(financeId, totalRevenue, totalExpense);

                if (finance != null) {
                    list.add(finance);
                }

            }

        } catch (Exception e) {
            System.out.println("Load Finance.txt failed!");
        }

        return list;

    }

    //================ SAVE =================
    public static void save(ArrayList<Finance> list) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (Finance finance : list) {

                bw.write(finance.toString());

                bw.newLine();

            }

        } catch (Exception e) {

            System.out.println("Save Finance.txt failed!");

        }

    }

}
