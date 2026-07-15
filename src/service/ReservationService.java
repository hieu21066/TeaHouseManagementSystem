package service;

import reservation.Reservation;
import file.ReservationFile;
import java.util.*;
import java.util.stream.Collectors;
import main.SystemClock;
public class ReservationService {
    private ArrayList<Reservation> reservationList;
    private final int MAX_TABLES = 10;

    public ReservationService() {
        this.reservationList = ReservationFile.load();
    }

    // --- LOGIC TỰ ĐỘNG ---
    public String generateNextId() {
        int max = 0;
        for (Reservation r : reservationList) {
            try { max = Math.max(max, Integer.parseInt(r.getReservationId().substring(3))); } catch (Exception e) {}
        }
        return String.format("RES%03d", max + 1);
    }

    public List<Integer> getAvailableTables(String timeSlot) {

    String[] input = timeSlot.split("-");
    int newStart = Integer.parseInt(input[0]);
    int newEnd = Integer.parseInt(input[1]);

    List<Integer> occupied = new ArrayList<>();

    for (Reservation r : reservationList) {

        String[] old = r.getTimeSlot().split("-");
        int oldStart = Integer.parseInt(old[0]);
        int oldEnd = Integer.parseInt(old[1]);

        // Nếu hai khoảng thời gian giao nhau
        if (newStart < oldEnd && oldStart < newEnd) {
            occupied.add(r.getTableNumber());
        }
    }

    List<Integer> available = new ArrayList<>();

    for (int i = 1; i <= MAX_TABLES; i++) {
        if (!occupied.contains(i)) {
            available.add(i);
        }
    }

    return available;
}

    // --- CÁC CHỨC NĂNG QUẢN LÝ ---
    public boolean addReservation(Reservation r) {
        reservationList.add(r);
        ReservationFile.save(reservationList);
        return true;
    }

    public boolean deleteReservation(String id) {
        boolean removed = reservationList.removeIf(r -> r.getReservationId().equalsIgnoreCase(id));
        if(removed) ReservationFile.save(reservationList);
        return removed;
    }

    public void updateFile() { ReservationFile.save(reservationList); }

    public Reservation searchById(String id) {
        return reservationList.stream().filter(r -> r.getReservationId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

    public ArrayList<Reservation> searchByName(String name) {
        return reservationList.stream().filter(r -> r.getCustomerName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Reservation> searchByPhone(String phone) {
        return reservationList.stream().filter(r -> r.getPhoneNumber().contains(phone))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void sortByCustomerName() { reservationList.sort(Comparator.comparing(Reservation::getCustomerName)); updateFile(); }
    public void sortByTableNumber() { reservationList.sort(Comparator.comparingInt(Reservation::getTableNumber)); updateFile(); }
    public ArrayList<Reservation> getReservationList() { return reservationList; }
    public ArrayList<Reservation> getReservationsByCurrentStatus(boolean onlyOccupied) {
    int currentHour = SystemClock.getCurrentHour();
    ArrayList<Reservation> result = new ArrayList<>();
    
    for (Reservation r : reservationList) {
        String[] parts = r.getTimeSlot().split("-");
        int start = Integer.parseInt(parts[0]);
        int end = Integer.parseInt(parts[1]);
        boolean isOccupied = (currentHour >= start && currentHour < end);
        
        if (onlyOccupied == isOccupied) {
            result.add(r);
        }
    }
    return result;
}
}