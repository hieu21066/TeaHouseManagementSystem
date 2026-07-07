package product;

public class TeaPet extends Product {
    private String petType;   // Loại tượng: Cóc thiềm thừ, tỳ hưu, chú tiểu...
    private String clayType;  // Chất đất: Tử nê, Đoàn nê...
    private String status;    // Tình trạng lên nước: Mới, Đã lên nước...

    // Constructor
    public TeaPet(String id, String name, double price, String petType, String clayType, String status) {
        super(id, name, price);
        this.petType = petType;
        this.clayType = clayType;
        this.status = status;
    }

    // Getter và Setter
    public String getPetType() { return petType; }
    public void setPetType(String petType) { this.petType = petType; }

    public String getClayType() { return clayType; }
    public void setClayType(String clayType) { this.clayType = clayType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Hàm toString dùng để ghi file định dạng dấu |
    @Override
    public String toString() {
        return getId() + "|teapet|" + getName() + "|" + getPrice() + "|" + petType + "|" + clayType + "|" + status;
    }
}