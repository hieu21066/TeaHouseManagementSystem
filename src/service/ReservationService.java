package service;

import reservation.Reservation;
import java.util.ArrayList;
import java.util.Comparator;

public class ReservationService {

    //==================== Attribute ====================

    private ArrayList<Reservation> reservationList;

    //==================== Constructor ====================

    public ReservationService() {
        reservationList = new ArrayList<>();
    }

    //==================== Getter & Setter ====================

    public ArrayList<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(ArrayList<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    //==================== ADD ====================

    public boolean addReservation(Reservation reservation) {

        if (isExist(reservation.getReservationId())) {
            return false;
        }

        reservationList.add(reservation);
        return true;
    }

    //==================== DELETE ====================

    public boolean deleteReservation(String reservationId) {

        Reservation reservation = searchById(reservationId);

        if (reservation == null) {
            return false;
        }

        reservationList.remove(reservation);
        return true;
    }

    //==================== UPDATE ====================

    public boolean updateReservation(Reservation newReservation) {

        Reservation oldReservation = searchById(newReservation.getReservationId());

        if (oldReservation == null) {
            return false;
        }

        oldReservation.setCustomerName(newReservation.getCustomerName());
        oldReservation.setPhoneNumber(newReservation.getPhoneNumber());
        oldReservation.setTimeSlot(newReservation.getTimeSlot());
        oldReservation.setTableNumber(newReservation.getTableNumber());
        oldReservation.setStatus(newReservation.getStatus());

        return true;
    }

    //==================== SEARCH ====================

    public Reservation searchById(String reservationId) {

        for (Reservation reservation : reservationList) {

            if (reservation.getReservationId().equalsIgnoreCase(reservationId)) {
                return reservation;
            }

        }

        return null;
    }

    public ArrayList<Reservation> searchByCustomerName(String name) {

        ArrayList<Reservation> result = new ArrayList<>();

        for (Reservation reservation : reservationList) {

            if (reservation.getCustomerName()
                    .toLowerCase()
                    .contains(name.toLowerCase())) {

                result.add(reservation);
            }

        }

        return result;
    }

    public ArrayList<Reservation> searchByPhone(String phone) {

        ArrayList<Reservation> result = new ArrayList<>();

        for (Reservation reservation : reservationList) {

            if (reservation.getPhoneNumber().contains(phone)) {
                result.add(reservation);
            }

        }

        return result;
    }

    //==================== DISPLAY ====================

    public void displayAll() {

        if (reservationList.isEmpty()) {
            System.out.println("Reservation list is empty!");
            return;
        }

        Reservation.displayHeader();

        for (Reservation reservation : reservationList) {
            reservation.display();
        }
    }

    public void displayById(String reservationId) {

        Reservation reservation = searchById(reservationId);

        if (reservation == null) {
            System.out.println("Reservation not found!");
            return;
        }

        Reservation.displayHeader();
        reservation.display();
    }

    //==================== SORT ====================

    public void sortByCustomerName() {

        reservationList.sort(
                Comparator.comparing(Reservation::getCustomerName)
        );
    }

    public void sortByTableNumber() {

        reservationList.sort(
                Comparator.comparingInt(Reservation::getTableNumber)
        );
    }

    //==================== STATUS ====================

    public boolean confirmReservation(String reservationId) {

        Reservation reservation = searchById(reservationId);

        if (reservation == null) {
            return false;
        }

        reservation.setStatus("Confirmed");
        return true;
    }

    public boolean cancelReservation(String reservationId) {

        Reservation reservation = searchById(reservationId);

        if (reservation == null) {
            return false;
        }

        reservation.setStatus("Cancelled");
        return true;
    }

    //==================== CHECK ====================

    public boolean isExist(String reservationId) {

        return searchById(reservationId) != null;
    }

    public int countReservation() {

        return reservationList.size();
    }

    public void clearAll() {

        reservationList.clear();
    }
}