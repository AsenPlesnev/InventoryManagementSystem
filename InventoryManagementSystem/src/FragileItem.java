/**
 * Class representing a fragile item, extends InventoryItem.
 * Specific to items categorized as fragile with a weight attribute.
 */

public class FragileItem  extends InventoryItem {
    private double weight;

    // Default constructor for serialization
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
    public double calculateValue(int quantity) {
        return getPrice() * quantity + 5 * this.weight; // Adding extra cost based on weight
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
}
