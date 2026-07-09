package file;

import combo.Combo;
import java.io.*;
import java.util.ArrayList;

public class ComboFile {

    private static final String FILE_NAME = "Combo.txt";

    //==================== CREATE FILE ====================
    private static void createFileIfNotExists() {

        try {

            File file = new File(FILE_NAME);

            if (!file.exists()) {

                file.createNewFile();
                System.out.println("Create Combo.txt successfully!");

            }

        } catch (IOException e) {

            System.out.println("Cannot create Combo.txt!");
            e.printStackTrace();

        }

    }

    //==================== LOAD ====================
    public static ArrayList<Combo> load() {

        createFileIfNotExists();

        ArrayList<Combo> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {

            String line;

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split("\\|");

                if (data.length != 5) {
                    continue;
                }

                try {

                    String comboId = data[0];
                    String teaType = data[1];
                    String comboName = data[2];
                    double price = Double.parseDouble(data[3]);
                    String description = data[4];

                    Combo combo = new Combo(
                            comboId,
                            teaType,
                            comboName,
                            price,
                            description
                    );

                    list.add(combo);

                } catch (NumberFormatException e) {

                    System.out.println("Invalid data: " + line);

                }

            }

        } catch (IOException e) {

            System.out.println("Load Combo.txt failed!");
            e.printStackTrace();

        }

        return list;

    }

    //==================== SAVE ====================
    public static void save(ArrayList<Combo> list) {

        createFileIfNotExists();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (Combo combo : list) {

                bw.write(combo.toString());
                bw.newLine();

            }

        } catch (IOException e) {

            System.out.println("Save Combo.txt failed!");
            e.printStackTrace();

        }

    }

}