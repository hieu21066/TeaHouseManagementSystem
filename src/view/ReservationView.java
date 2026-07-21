package view;

import reservation.Reservation;
import service.ReservationService;
import java.util.*;

public class ReservationView {

    private ReservationService service;
    private Scanner sc;

    public ReservationView(Scanner sc, ReservationService service) {
        this.sc = sc;
        this.service = service;
    }

    public void menu() {
        int choice = -1;
        do {
            System.out.println("\n========== RESERVATION MANAGEMENT ==========");
            System.out.println("1. Add Reservation (Auto ID)");
            System.out.println("2. Delete Reservation");
            System.out.println("3. Update Reservation");
            System.out.println("4. Search By ID");
            System.out.println("5. Search By Customer Name");
            System.out.println("6. Search By Phone");
            System.out.println("7. Display All");
            System.out.println("8. Sort By Name");
            System.out.println("9. Sort By Table");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                continue;
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
                    displayById();
                    break;
                case 5:
                    searchByName();
                    break;
                case 6:
                    searchByPhone();
                    break;
                case 7:
                    service.getReservationList().forEach(Reservation::display);
                    break;
                case 8:
                    service.sortByCustomerName();
                    System.out.println("Sorted!");
                    break;
                case 9:
                    service.sortByTableNumber();
                    System.out.println("Sorted!");
                    break;
            }
        } while (choice != 0);
    }

    private void addReservation() {
        System.out.print("Time Slot: ");
        String time = sc.nextLine();
        List<Integer> free = service.getAvailableTables(time);
        if (free.isEmpty()) {
            System.out.println("Full bàn khung giờ này!");
            return;
        }
        System.out.println("Bàn trống: " + free);
        System.out.print("Chọn bàn: ");
        int table = Integer.parseInt(sc.nextLine());
        System.out.print("Customer Name: ");
        String name = sc.nextLine();
        System.out.print("Phone: ");
        String phone = sc.nextLine();
        service.addReservation(new Reservation(service.generateNextId(), name, phone, time, table));
        System.out.println("Add Successfully!");
    }

    private void deleteReservation() {
        System.out.print("ID: ");
        String id = sc.nextLine();
        if (service.deleteReservation(id)) {
            System.out.println("Deleted!");
        } else {
            System.out.println("Not found!");
        }
    }

    private void updateReservation() {
        System.out.print("ID cần sửa: ");
        String id = sc.nextLine();
        Reservation r = service.searchById(id);
        if (r == null) {
            System.out.println("Not found!");
            return;
        }
        System.out.print("Tên mới: ");
        r.setCustomerName(sc.nextLine());
        System.out.print("SĐT mới: ");
        r.setPhoneNumber(sc.nextLine());
        service.updateFile();
        System.out.println("Updated!");
    }

    private void displayById() {
        System.out.print("ID: ");
        Reservation r = service.searchById(sc.nextLine());
        if (r != null) {
            r.display();
        } else {
            System.out.println("Not found!");
        }
    }

    private void searchByName() {
        System.out.print("Tên: ");
        service.searchByName(sc.nextLine()).forEach(Reservation::display);
    }

    private void searchByPhone() {
        System.out.print("SĐT: ");
        service.searchByPhone(sc.nextLine()).forEach(Reservation::display);
    }
}
