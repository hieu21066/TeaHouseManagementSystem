package reservation;

public class Reservation {
    private String customerName;
    private String phoneNumber;
    private String timeSlot;
    private int tableNumber;

    public Reservation(String customerName, String phoneNumber, String timeSlot, int tableNumber) {
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.timeSlot = timeSlot;
        this.tableNumber = tableNumber;
    }

    public void display() {
        System.out.printf("| Table number: %-2d | Client: %-18s | Phone number: %-11s | Hour: %-8s |\n", 
                tableNumber, customerName, phoneNumber, timeSlot);
    }
}