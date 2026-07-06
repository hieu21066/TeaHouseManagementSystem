package product;

public class TeaWare extends Product {
    private String wareType;   // Ấm, Chén chủ, Chén quân, Khải, Tống...
    private String clayType;   // Đất tử sa, Tử nê, Đoàn nê, Đại hồng bào... (Nếu là ấm)
    private String design;     // Thạch biều, Tây Thi, Lục Phương, Truyền Lô... (Nếu là ấm)
    private int capacity;      // Dung tích: 80ml, 100ml, 200ml...

    // Constructor tổng quát cho Trà Cụ
    public TeaWare(String id, String name, double price, String wareType, String clayType, String design, int capacity) {
        super(id, name, price);
        this.wareType = wareType;
        this.clayType = clayType;
        this.design = design;
        this.capacity = capacity;
    }

    // Constructor rút gọn cho dụng cụ không làm từ đất/không có dáng ấm (ví dụ: Chén, Tống thủy tinh)
    public TeaWare(String id, String name, double price, String wareType, int capacity) {
        super(id, name, price);
        this.wareType = wareType;
        this.clayType = "N/A";
        this.design = "N/A";
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Loại: %s | Đất: %s | Dáng: %s | Dung tích: %dml", 
                wareType, clayType, design, capacity);
    }
}