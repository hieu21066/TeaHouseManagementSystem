package file;

import account.Account;
import java.io.*;
import java.util.ArrayList;

public class AccountFile {

    private static final String FILE_NAME = "Account.txt";

    //==================== LOAD ====================
    public static ArrayList<Account> load() {

        ArrayList<Account> list = new ArrayList<>();

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return list;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split("\\|");

                String username = data[0];
                String password = data[1];

                list.add(new Account(username, password));
            }

        } catch (Exception e) {
            System.out.println("Load Account.txt failed!");
        }

        return list;
    }

    //==================== SAVE ====================
    public static void save(ArrayList<Account> list) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (Account account : list) {
                bw.write(account.toString());
                bw.newLine();
            }

        } catch (Exception e) {
            System.out.println("Save Account.txt failed!");
        }
    }

}