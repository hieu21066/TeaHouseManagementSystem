package product;

public class TeaWare extends Product {
    private String wareType;   
    private String clayType;   
    private String design;     
    private int capacity;      

    public TeaWare(String id, String name, double price, String wareType, String clayType, String design, int capacity) {
        super(id, name, price);
        this.wareType = wareType;
        this.clayType = clayType;
        this.design = design;
        this.capacity = capacity;
    }

    public TeaWare(String id, String name, double price, String wareType, int capacity) {
        super(id, name, price);
        this.wareType = wareType;
        this.clayType = "N/A";
        this.design = "N/A";
        this.capacity = capacity;
    }

    // --- GETTER & SETTER ---
    public String getWareType() { return wareType; }
    public void setWareType(String wareType) { this.wareType = wareType; }

    public String getClayType() { return clayType; }
    public void setClayType(String clayType) { this.clayType = clayType; }

    public String getDesign() { return design; }
    public void setDesign(String design) { this.design = design; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Loại: %s | Đất: %s | Dáng: %s | Dung tích: %dml", 
                wareType, clayType, design, capacity);
    }
}