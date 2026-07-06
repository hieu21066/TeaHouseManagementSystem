package reservation;

public class Reservation {

    private String reservationId;
    private String customerName;
    private String phoneNumber;
    private String timeSlot;
    private int tableNumber;
    private String status;

    // Constructor mặc định
    public Reservation() {
    }

    // Constructor đầy đủ
    public Reservation(String reservationId, String customerName,
                       String phoneNumber, String timeSlot,
                       int tableNumber, String status) {

        this.reservationId = reservationId;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.timeSlot = timeSlot;
        this.tableNumber = tableNumber;
        this.status = status;
    }

    // Getter & Setter
    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Lưu file
    @Override
    public String toString() {
        return reservationId + "|"
                + customerName + "|"
                + phoneNumber + "|"
                + timeSlot + "|"
                + tableNumber + "|"
                + status;
    }

    // Hiển thị tiêu đề
    public static void displayHeader() {
        System.out.println("==============================================================================================");
        System.out.printf("| %-6s | %-20s | %-12s | %-10s | %-5s | %-10s |%n",
                "ID", "Customer", "Phone", "Time", "Table", "Status");
        System.out.println("==============================================================================================");
    }

    // Hiển thị một dòng
    public void display() {
        System.out.printf("| %-6s | %-20s | %-12s | %-10s | %-5d | %-10s |%n",
                reservationId,
                customerName,
                phoneNumber,
                timeSlot,
                tableNumber,
                status);
    }
}