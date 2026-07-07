package file;

import combo.Combo;
import java.io.*;
import java.util.ArrayList;

public class ComboFile {

    private static final String FILE_NAME = "Combo.txt";

    //==================== LOAD ====================
    public static ArrayList<Combo> load() {

        ArrayList<Combo> list = new ArrayList<>();

        File file = new File(FILE_NAME);

        if (!file.exists())
            return list;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split("\\|");

                String id = data[0];
                String name = data[1];
                double price = Double.parseDouble(data[2]);
                String description = data[3];

                Combo combo = new Combo(id, name, price, description);

                list.add(combo);

            }

        } catch (Exception e) {
            System.out.println("Load Combo.txt failed!");
        }

        return list;
    }

    //==================== SAVE ====================
    public static void save(ArrayList<Combo> list) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (Combo combo : list) {

                bw.write(combo.toString());
                bw.newLine();

            }

        } catch (Exception e) {
            System.out.println("Save Combo.txt failed!");
        }

    }

}