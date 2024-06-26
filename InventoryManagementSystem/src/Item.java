/**
 * Interface representing an item in the inventory.
 * Defines methods for getting item name, details, calculating value, and displaying the item's description.
 */
public interface Item {
    String getName();
    String getItemDetails();
    double calculateValue(int quantity);
    void displayDescription();
}
