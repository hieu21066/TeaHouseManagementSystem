package product;

public class Tea extends Product {

    private String teaType;
    private int sampleWeightGrams;

    public Tea() {
        super();
    }

    public Tea(String id, String name, double price, int quantity, String description, String teaType, int sampleWeightGrams) {
        super(id, name, price, quantity, description);
        this.teaType = teaType;
        this.sampleWeightGrams = sampleWeightGrams;
    }

    @Override
    public void display() {
        super.display();
        System.out.printf("Tea Type: %s, Packing: %dg\n", teaType, sampleWeightGrams);
    }

    @Override
    public String toString() {
        // Sử dụng các hàm getter public từ lớp cha Product
        return String.format("%s|Tea|%s|%s|%.2f|%s|%d",
                getId(), this.teaType, getName(), getPrice(), getDescription(), this.sampleWeightGrams);
    }

    // Getter & Setter...
    public String getTeaType() {
        return teaType;
    }

    public void setTeaType(String teaType) {
        this.teaType = teaType;
    }

    public int getSampleWeightGrams() {
        return sampleWeightGrams;
    }

    public void setSampleWeightGrams(int sampleWeightGrams) {
        this.sampleWeightGrams = sampleWeightGrams;
    }

}
