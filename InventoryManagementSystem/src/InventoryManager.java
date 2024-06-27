import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Class representing the inventory management system.
 * Manages a collection of InventoryItem objects and a collection of Order objects
 * Provides methods to add, remove, display, categorize, save, and load items.
 * Also provides method to add, remove, list and process orders.
 * Has functionality to save data to a file and load data from a file.
 */

public class InventoryManager {
    //Collection to keep all items like itemID -> InventoryItem
    private HashMap<Integer, InventoryItem> inventoryItems;
    private ArrayList<Order> orders;
    private int nextOrderID;

    public InventoryManager() {
        this.inventoryItems = new HashMap<>();
        this.orders = new ArrayList<>();
        nextOrderID = 1;
    }

    /**
     * Adds an item to the inventory.
     * @param item The item to add.
     */
    public void addItem(InventoryItem item) {
        this.inventoryItems.put(item.getId(), item);
    }

    /**
     * Checks if there are any items in the inventory.
     * If there aren't outputs a message to the console.
     * @return Returns true if there are no items and false if there are.
     */

    public boolean checkForItems() {
        if (this.inventoryItems.isEmpty()) {
            System.out.println("No items in inventory.");
            System.out.println();
            return true;
        }

        return false;
    }

    /**
     * Removes an item from the inventory by ID.
     * @param itemID The ID of the item to remove.
     * @throws NoSuchElementException If the item with the given ID doesn't exist.
     */
    public void removeItem(int itemID) {
        if (!this.inventoryItems.containsKey(itemID)) {
            throw new NoSuchElementException("Item with ID " + itemID + " not found.");
        }
        this.inventoryItems.remove(itemID);
    }

    /**
     * Gets an item from the inventory by ID.
     * @param itemID The ID of the item to get.
     * @return The item with the specified ID, or null if not found.
     */
    public InventoryItem getItem(int itemID) {
        return inventoryItems.get(itemID);
    }

    /**
     * Displays all items in the inventory.
     */
    public void displayItems() {
        if (this.inventoryItems.isEmpty()) {
            System.out.println("No items in inventory.");
            System.out.println();
            return;
        }
        for (InventoryItem item : this.inventoryItems.values()) {
            System.out.println(item.getItemDetails());
            item.displayDescription();
            System.out.println();
        }
    }

    /**
     * Get all items from a specific Category.
     * @param category the category we want to check for.
     * @return ArrayList of InventoryItem items which are from the given category.
     */
    public ArrayList<InventoryItem> getItemsByCategory(String category) {
        ArrayList<InventoryItem> itemsByCategory = new ArrayList<>();
        for (var item : this.inventoryItems.values()) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                itemsByCategory.add(item);
            }
        }
        return itemsByCategory;
    }

    /**
     * Displays all items from a category.
     * @param category the category we want to check for.
     */
    public void displayItemsByCategory(String category) {
        ArrayList<InventoryItem> itemsByCategory = getItemsByCategory(category);
        if (itemsByCategory.isEmpty()) {
            System.out.println("No items found in category: " + category);
            return;
        }
        for (InventoryItem item : itemsByCategory) {
            System.out.println(item.getItemDetails());
            item.displayDescription();
            System.out.println();
        }
    }

    /**
     * Search for an order by ID in the collection of Orders.
     * @param id the ID of the order we want to check for.
     * @return The order with the specified ID, or null if not found.
     */
    public Order getOrderById(int id) {
        for (Order order : this.orders) {
            if (order.getOrderID() == id) {
                return order;
            }
        }

        return null;
    }

    /**
     * Displays all orders by category.
     */
    public void displayOrders() {
        for (Order order : this.orders) {
            System.out.println(order);
            System.out.println("Order total: " + order.calculateOrderTotal(this.inventoryItems));
            System.out.println();
        }
    }

    /**
     * Checks if we have any orders in the system.
     * If there aren't outputs a message to the console.
     * @return Returns true if there are no orders and false if there are.
     */

    public boolean checkForOrders() {
        if (this.orders.isEmpty()) {
            System.out.println("There are no orders placed!");
            System.out.println();
            return true;
        }

        return false;
    }

    /**
     * Creates an order with the specified items and quantities.
     * Updates the inventory quantities after the order is placed.
     * Adds the order to the list of orders.
     * @throws IllegalArgumentException if any of the item's quantity is less than the quantity given in the order.
     * @param itemsToOrder The items to order, mapped by item ID to quantity.
     */
    public void createOrder(HashMap<Integer, Integer> itemsToOrder) {

        Order order = new Order(nextOrderID++, new Date(), itemsToOrder);

        for (HashMap.Entry<Integer, Integer> entry : itemsToOrder.entrySet()) {
            int itemID = entry.getKey();
            int quantity = entry.getValue();

            InventoryItem item = getItem(itemID);

            if (item.getQuantity() < quantity) {
                throw new IllegalArgumentException("Not enough stock for item with ID " + itemID);
            }

            item.setQuantity(item.getQuantity() - quantity);
        }

        this.orders.add(order);

        System.out.println("Order created successfully with total: " + order.calculateOrderTotal(inventoryItems));
        System.out.println();
    }

    /**
     * Removes an order with the specified ID.
     * Updates the inventory quantities after the order is removed.
     * Removes the order from the list of orders.
     * @throws NoSuchElementException it there isn't an order with the provided ID.
     * @param orderId The ID of the order we want to remove.
     */
    public void removeOrder(int orderId) {
        Order orderToRemove = getOrderById(orderId);

        if (orderToRemove != null) {
            this.orders.remove(orderToRemove);

            //Updated quantities for each item that was in the order
            for (HashMap.Entry<Integer, Integer> entry : orderToRemove.getItemsOrdered().entrySet()) {
                int itemID = entry.getKey();
                int quantity = entry.getValue();

                InventoryItem item = getItem(itemID);

                item.setQuantity(item.getQuantity() + quantity);
            }
            return;
        }

        throw new NoSuchElementException("Order with ID " + orderId + " doesn't exist!");
    }

    /**
     * Process an order with the specified ID.
     * @throws IllegalArgumentException if the payment is null or insufficient.
     * @throws NoSuchElementException it there isn't an order with the provided ID.
     * Removes the order from the list of orders.
     * @param orderId The ID of the order we want to process.
     */
    public void processOrder(int orderId, double paymentAmount) {
        Order order = getOrderById(orderId);

        double total = order.calculateOrderTotal(inventoryItems);

        if (paymentAmount < total) {
            throw new IllegalArgumentException("Insufficient payment. The total of the order is " + total + " and payment amount is " + paymentAmount);
        }

        System.out.println("Order with ID " + orderId + " and total " + total + " was successfully processed ");
        System.out.println();

        orders.remove(order);
    }

    /**
     * Saves the inventory data to a file using ObjectOutputStream for serialization.
     * Also saves the inventory data in CSV format for readability.
     * @param filename The name of the file to save the inventory data to.
     * @throws IOException If an I/O error occurs while saving the inventory data.
     */
    public void saveInventory(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(inventoryItems);
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename.replace(".ser", ".csv")))) {
            writer.println("ItemID,Name,Quantity,Category,Price");
            for (InventoryItem item : inventoryItems.values()) {
                writer.println(item.getId() + "," + item.getName() + "," + item.getQuantity() + "," + item.getCategory() + "," + item.getPrice());
            }
        }
    }

    /**
     * Loads the inventory data from a file using ObjectInputStream for deserialization.
     * @param filename The name of the file to load the inventory data from.
     * @throws IOException If an I/O error occurs while loading the inventory data.
     * @throws ClassNotFoundException If the class of a serialized object cannot be found.
     */
    public void loadInventory(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            inventoryItems = (HashMap<Integer, InventoryItem>) ois.readObject();
        }
    }
}
