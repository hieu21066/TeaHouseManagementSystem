package product;

public class Tea extends Product {
    private String teaType; // Lục trà, Hồng trà, Bạch trà, Phổ nhĩ,...

    public Tea(String id, String name, double price, String teaType) {
        super(id, name, price);
        this.teaType = teaType;
    }

    public String getTeaType() { return teaType; }

    @Override
    public String toString() {
        return super.toString() + " | Loại trà: " + teaType;
    }
}