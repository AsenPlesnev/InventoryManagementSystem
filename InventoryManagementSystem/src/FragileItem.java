public class FragileItem  extends InventoryItem {
    private double weight;

    public FragileItem() {

    }

    public FragileItem(String name, int id, int quantity, double weight, String description, double price) {
        super(name, id, quantity, description, true, false, price);
        this.weight = weight;
        setCategory("Fragile");
    }

    public double getWeight() {
        return this.weight;
    }

    @Override
    public double calculateValue() {
        return getPrice() * getQuantity() + 5 * this.weight; // Adding extra cost based on weight
    }

    @Override
    public String getItemDetails() {
        return super.getItemDetails() + ", Weight: " + this.weight;
    }

    @Override
    public void handleBreakage() {
        if (isBreakable()) {
            System.out.println(getItemDetails() + " is fragile and has broken.");
        }
    }

    @Override
    public String toString() {
        return String.format("FragileItem,%s,%d,%d,%s,%.2f,%.2f", super.getName(), super.getId(), super.getQuantity(),
                super.getCategory(), super.getPrice(), this.weight);
    }
}
