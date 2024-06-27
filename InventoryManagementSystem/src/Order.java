import java.util.Date;
import java.util.HashMap;

/**
 * Class representing an order.
 * Contains details about the order ID, date, items to order and the associated payment.
 */

public class Order {
    private int orderID;
    private Date orderDate;
    //We will store the itemId -> quantity for each ordered item
    private HashMap<Integer, Integer> itemsOrdered;

    public Order(int orderID, Date orderDate, HashMap<Integer, Integer> itemsToOrder) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.itemsOrdered = itemsToOrder;
    }

    public int getOrderID() {
        return this.orderID;
    }

    public Date getOrderDate() {
        return this.orderDate;
    }

    public void addItem(int itemID, int quantity) {
        this.itemsOrdered.put(itemID, quantity);
    }

    public HashMap<Integer, Integer> getItemsOrdered() {
        return this.itemsOrdered;
    }

    public double calculateOrderTotal(HashMap<Integer, InventoryItem> inventory) {
        double total = 0.0;
        for (var entry : this.itemsOrdered.entrySet()) {
            int itemID = entry.getKey();
            int quantity = entry.getValue();

            InventoryItem item = inventory.get(itemID);
            total += item.calculateValue(quantity);
        }
        return total;
    }

    public void processOrder(HashMap<Integer, InventoryItem> inventory) {

        // Update inventory quantities
        for (var entry : this.itemsOrdered.entrySet()) {
            int itemID = entry.getKey();
            int quantityOrdered = entry.getValue();
            if (inventory.containsKey(itemID)) {
                InventoryItem item = inventory.get(itemID);
                int currentQuantity = item.getQuantity();
                item.setQuantity(currentQuantity - quantityOrdered);
                inventory.put(itemID, item);
            }
        }
        System.out.println("Order processed successfully.");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(this.orderID).append("\n");
        sb.append("Order Date: ").append(this.orderDate).append("\n");
        sb.append("Items ordered:\n");
        for (var entry : this.itemsOrdered.entrySet()) {
            sb.append("  Item ID: ").append(entry.getKey()).append(", Quantity: ").append(entry.getValue());
        }

        return sb.toString();
    }
}
