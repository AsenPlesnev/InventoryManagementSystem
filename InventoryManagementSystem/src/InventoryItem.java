import java.util.HashSet;

/**
 * Class representing an inventory item, extends AbstractItem.
 * Adds instance variables for item ID and quantity.
 */

public class InventoryItem extends AbstractItem {
    //Static collection to keep all Ids from all instances that are in use so there won't be any duplicate IDs.
    private static final HashSet<Integer> existingIDs = new HashSet<>();
    private int id;
    private int quantity;

    // Default constructor for serialization
    public InventoryItem() {

    }

    // Constructor to initialize item details, including ID and quantity
    public InventoryItem(String name, int id, int quantity, String description, boolean breakable,
                         boolean perishable, double price) {
        super(name, description, breakable, perishable, price);

        //If the given ID is in the Set already it means it is used by another instance, and we should throw an error.
        if (isIdInUse(id)) {
            throw new IllegalArgumentException("This Id is already in use. Choose another Id.");
        }
        this.id = id;
        existingIDs.add(id);
        this.quantity = quantity;
    }

    private boolean isIdInUse(int itemID) {
        return existingIDs.contains(itemID);
    }

    public int getId() {
        return this.id;
    }

    public void setID(int id) {
        if (id <  0) {
            throw new IllegalArgumentException("Id can't be less than 0");
            //We check if the ID we want to set is in use already.
        } else if (isIdInUse(id)) {
            throw new IllegalArgumentException("Id is already in use. Choose another Id!");
        }


        //We remove the previous ID from the set because it is won't be in use and add the new one
        existingIDs.remove(this.id);
        this.id = id;
        existingIDs.add(id);
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity can't be less than 0.");
        }

        this.quantity = quantity;
    }

    @Override
    public double calculateValue(int quantity) {
        return getPrice() * quantity;
    }

    //Adding the new properties to the details
    @Override
    public String getItemDetails() {
        return "ID: " + this.id + ", " + super.getItemDetails() + ", Quantity: " + this.quantity;
    }

    @Override
    public String toString() {
        return String.format("%s,%d,%d,%s,%.2f", super.getName(), this.id, this.quantity, super.getCategory(), super.getPrice());
    }
}
