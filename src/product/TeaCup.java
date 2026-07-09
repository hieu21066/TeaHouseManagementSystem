package product;

public class TeaCup extends Product {
    private String cupRole;   
    private int capacityMl;   

    public TeaCup() {
        super(); 
    }

    public TeaCup(String id, String name, double price, int quantity, String description, String cupRole, int capacityMl) {
        super(id, name, price, quantity, description);
        this.cupRole = cupRole;
        this.capacityMl = capacityMl;
    }

    @Override
    public void display() {
        super.display();
        System.out.printf("Role: %s, Capacity: %dml\n", cupRole, capacityMl);
    }
 
   @Override
    public String toString() {
        return String.format("%s|TeaCup|TeaCup|%s|%.2f|%s|%s|%d", 
                getId(), getName(), getPrice(), getDescription(), this.cupRole, this.capacityMl);
    }

    public String getCupRole() {
        return cupRole;
    }

    public void setCupRole(String cupRole) {
        this.cupRole = cupRole;
    }

    public int getCapacityMl() {
        return capacityMl;
    }

    public void setCapacityMl(int capacityMl) {
        this.capacityMl = capacityMl;
    }
}