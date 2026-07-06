package product;

public class Tea extends Product {
    private String teaType;

    public Tea(String id, String name, double price, String teaType) {
        super(id, name, price);
        this.teaType = teaType;
    }

    // --- GETTER & SETTER ---
    public String getTeaType() { return teaType; }
    public void setTeaType(String teaType) { this.teaType = teaType; }

    @Override
    public String toString() {
        return super.toString() + " | Loại trà: " + teaType;
    }
}