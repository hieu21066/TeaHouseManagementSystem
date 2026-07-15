package reservation;
import main.SystemClock;
public class Reservation {
    private String reservationId;
    private String customerName;
    private String phoneNumber;
    private String timeSlot;
    private int tableNumber;

    public Reservation(String id, String name, String phone, String time, int table) {
        this.reservationId = id;
        this.customerName = name;
        this.phoneNumber = phone;
        this.timeSlot = time;
        this.tableNumber = table;
    }

    // Getters & Setters
    public String getReservationId() { return reservationId; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String name) { this.customerName = name; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phone) { this.phoneNumber = phone; }
    public String getTimeSlot() { return timeSlot; }
    public void setTimeSlot(String time) { this.timeSlot = time; }
    public int getTableNumber() { return tableNumber; }
    public void setTableNumber(int table) { this.tableNumber = table; }

    @Override
    public String toString() {
        return reservationId + "|" + customerName + "|" + phoneNumber + "|" + timeSlot + "|" + tableNumber;
    }

    public static void displayHeader() {
        System.out.println("==========================================================================");
        System.out.printf("| %-8s | %-15s | %-12s | %-10s | %-8s | %-10s |%n", 
                          "ID", "Customer", "Phone", "Time", "Table", "Status");
        System.out.println("==========================================================================");
    }

    public void display() {
    int currentHour = SystemClock.getCurrentHour();
    String status = "Available"; // Mặc định là sẵn sàng

    // Giả sử TimeSlot định dạng là "start-end" (ví dụ: 18-20)
    try {
        String[] parts = this.getTimeSlot().split("-");
        int start = Integer.parseInt(parts[0]);
        int end = Integer.parseInt(parts[1]);

        // Nếu giờ hiện tại nằm trong khoảng đặt bàn
        if (currentHour >= start && currentHour < end) {
            status = "Occupied";
        }
    } catch (Exception e) {
        status = "Unknown";
    }

    System.out.printf("| %-8s | %-15s | %-12s | %-10s | %-8d | %-10s |%n", 
                      getReservationId(), getCustomerName(), getPhoneNumber(), 
                      getTimeSlot(), getTableNumber(), status);
}
}