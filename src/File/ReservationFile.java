package file;

import reservation.Reservation;
import java.io.*;
import java.util.*;

public class ReservationFile {

    private static final String FILE = "Reservation.txt";

    public static ArrayList<Reservation> load() {
        ArrayList<Reservation> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split("\\|");
                list.add(new Reservation(d[0], d[1], d[2], d[3], Integer.parseInt(d[4])));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static void save(ArrayList<Reservation> list) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {
            for (Reservation r : list) {
                pw.println(r.toString());
            }
        } catch (Exception e) {
        }
    }
}
