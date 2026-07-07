package file;

import reservation.Reservation;
import java.io.*;
import java.util.ArrayList;

public class ReservationFile {

    private static final String FILE_NAME = "Reservation.txt";

    //==================== LOAD ====================
    public static ArrayList<Reservation> load() {

        ArrayList<Reservation> list = new ArrayList<>();

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return list;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split("\\|");

                if (data.length != 6) {
                    continue; // bỏ qua dòng sai định dạng
                }

                String reservationId = data[0];
                String customerName = data[1];
                String phoneNumber = data[2];
                String timeSlot = data[3];
                int tableNumber = Integer.parseInt(data[4]);
                String status = data[5];

                Reservation reservation = new Reservation(
                        reservationId,
                        customerName,
                        phoneNumber,
                        timeSlot,
                        tableNumber,
                        status
                );

                list.add(reservation);
            }

        } catch (Exception e) {
            System.out.println("Load Reservation.txt failed!");
        }

        return list;
    }

    //==================== SAVE ====================
    public static void save(ArrayList<Reservation> list) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (Reservation reservation : list) {

                bw.write(reservation.toString());
                bw.newLine();

            }

        } catch (Exception e) {
            System.out.println("Save Reservation.txt failed!");
        }
    }

}