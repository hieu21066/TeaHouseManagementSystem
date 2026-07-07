package view;

import reservation.Reservation;
import service.ReservationService;

import java.util.ArrayList;
import java.util.Scanner;

public class ReservationViewer {

    private ReservationService reservationService;
    private Scanner sc;

    public ReservationViewer(ReservationService reservationService) {
        this.reservationService = reservationService;
        sc = new Scanner(System.in);
    }

    public void menu() {
        int choice;

        do {
            System.out.println("\n========== RESERVATION MANAGEMENT ==========");
            System.out.println("1. Add Reservation");
            System.out.println("2. Delete Reservation");
            System.out.println("3. Update Reservation");
            System.out.println("4. Search By ID");
            System.out.println("5. Search By Customer Name");
            System.out.println("6. Search By Phone");
            System.out.println("7. Display All");
            System.out.println("8. Sort By Customer Name");
            System.out.println("9. Sort By Table Number");
            System.out.println("10. Confirm Reservation");
            System.out.println("11. Cancel Reservation");
            System.out.println("0. Back");
            System.out.print("Choose: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    addReservation();
                    break;

                case 2:
                    deleteReservation();
                    break;

                case 3:
                    updateReservation();
                    break;

                case 4:
                    searchById();
                    break;

                case 5:
                    searchByName();
                    break;

                case 6:
                    searchByPhone();
                    break;

                case 7:
                    reservationService.displayAll();
                    break;

                case 8:
                    reservationService.sortByCustomerName();
                    System.out.println("Sorted Successfully!");
                    reservationService.displayAll();
                    break;

                case 9:
                    reservationService.sortByTableNumber();
                    System.out.println("Sorted Successfully!");
                    reservationService.displayAll();
                    break;

                case 10:
                    confirmReservation();
                    break;

                case 11:
                    cancelReservation();
                    break;

                case 0:
                    System.out.println("Back...");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 0);
    }

    //====================================================

    private void addReservation() {

        System.out.print("Reservation ID: ");
        String id = sc.nextLine();

        System.out.print("Customer Name: ");
        String name = sc.nextLine();

        System.out.print("Phone: ");
        String phone = sc.nextLine();

        System.out.print("Time Slot: ");
        String time = sc.nextLine();

        System.out.print("Table Number: ");
        int table = Integer.parseInt(sc.nextLine());

        System.out.print("Status: ");
        String status = sc.nextLine();

        Reservation reservation = new Reservation(id, name, phone, time, table, status);

        if (reservationService.addReservation(reservation)) {
            System.out.println("Add Successfully!");
        } else {
            System.out.println("Reservation ID Already Exists!");
        }
    }

    //====================================================

    private void deleteReservation() {

        System.out.print("Reservation ID: ");
        String id = sc.nextLine();

        if (reservationService.deleteReservation(id)) {
            System.out.println("Delete Successfully!");
        } else {
            System.out.println("Reservation Not Found!");
        }
    }

    //====================================================

    private void updateReservation() {

        System.out.print("Reservation ID: ");
        String id = sc.nextLine();

        Reservation old = reservationService.searchById(id);

        if (old == null) {
            System.out.println("Reservation Not Found!");
            return;
        }

        System.out.print("Customer Name: ");
        String name = sc.nextLine();

        System.out.print("Phone: ");
        String phone = sc.nextLine();

        System.out.print("Time Slot: ");
        String time = sc.nextLine();

        System.out.print("Table Number: ");
        int table = Integer.parseInt(sc.nextLine());

        System.out.print("Status: ");
        String status = sc.nextLine();

        Reservation reservation =
                new Reservation(id, name, phone, time, table, status);

        reservationService.updateReservation(reservation);

        System.out.println("Update Successfully!");
    }

    //====================================================

    private void searchById() {

        System.out.print("Reservation ID: ");
        String id = sc.nextLine();

        reservationService.displayById(id);
    }

    //====================================================

    private void searchByName() {

        System.out.print("Customer Name: ");
        String name = sc.nextLine();

        ArrayList<Reservation> list =
                reservationService.searchByCustomerName(name);

        if (list.isEmpty()) {
            System.out.println("No Reservation Found!");
            return;
        }

        Reservation.displayHeader();

        for (Reservation r : list) {
            r.display();
        }
    }

    //====================================================

    private void searchByPhone() {

        System.out.print("Phone: ");
        String phone = sc.nextLine();

        ArrayList<Reservation> list =
                reservationService.searchByPhone(phone);

        if (list.isEmpty()) {
            System.out.println("No Reservation Found!");
            return;
        }

        Reservation.displayHeader();

        for (Reservation r : list) {
            r.display();
        }
    }

    //====================================================

    private void confirmReservation() {

        System.out.print("Reservation ID: ");
        String id = sc.nextLine();

        if (reservationService.confirmReservation(id)) {
            System.out.println("Confirmed Successfully!");
        } else {
            System.out.println("Reservation Not Found!");
        }
    }

    //====================================================

    private void cancelReservation() {

        System.out.print("Reservation ID: ");
        String id = sc.nextLine();

        if (reservationService.cancelReservation(id)) {
            System.out.println("Cancelled Successfully!");
        } else {
            System.out.println("Reservation Not Found!");
        }
    }

}