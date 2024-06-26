import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.NoSuchElementException;

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

    public void addItem(InventoryItem item) {
        if (this.inventoryItems.containsKey(item.getId())) {
            throw new IllegalArgumentException("Item with ID " + item.getId() + " already exists.");
        }
        this.inventoryItems.put(item.getId(), item);
    }

    public boolean isEmpty() {
        return this.inventoryItems.isEmpty();
    }

    public void removeItem(int itemID) {
        if (!this.inventoryItems.containsKey(itemID)) {
            throw new NoSuchElementException("Item with ID " + itemID + " not found.");
        }
        this.inventoryItems.remove(itemID);
    }

    public InventoryItem getItem(int itemID) {
        return inventoryItems.get(itemID);
    }

    public void displayItems() {
        if (this.inventoryItems.isEmpty()) {
            System.out.println("No items in inventory.");
            return;
        }
        for (InventoryItem item : this.inventoryItems.values()) {
            System.out.println(item.getItemDetails());
            item.displayDescription();
            System.out.println();
        }
    }

    public ArrayList<InventoryItem> getItemsByCategory(String category) {
        ArrayList<InventoryItem> itemsByCategory = new ArrayList<>();
        for (var item : this.inventoryItems.values()) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                itemsByCategory.add(item);
            }
        }
        return itemsByCategory;
    }

    public void displayItemsByCategory(String category) {
        ArrayList<InventoryItem> itemsByCategory = getItemsByCategory(category);
        if (itemsByCategory.isEmpty()) {
            System.out.println("No items found in category: " + category);
            return;
        }
        for (InventoryItem item : itemsByCategory) {
            item.displayDescription();
        }
    }

    public void displayOrders() {
        for (Order order : this.orders) {
            System.out.println(order);
        }
    }

    public void createOrder(HashMap<Integer, Integer> itemsToOrder) {

        Order order = new Order(nextOrderID++, new Date(), itemsToOrder, null);

        for (HashMap.Entry<Integer, Integer> entry : itemsToOrder.entrySet()) {
            int itemID = entry.getKey();
            int quantity = entry.getValue();

            InventoryItem item = getItem(itemID);

            if (item.getQuantity() < quantity) {
                throw new IllegalArgumentException("Not enough stock for item with ID " + itemID);
            }

            item.setQuantity(item.getQuantity() - quantity);
        }

        System.out.println("Order created successfully with total: " + order.calculateOrderTotal(inventoryItems));
        System.out.println();
    }

    //Saving data both to serialized file and CSV file so the user can read the CSV file, and we can deserialize the other
    //file for easier loading.
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

    public void loadInventory(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            inventoryItems = (HashMap<Integer, InventoryItem>) ois.readObject();
        }
    }
}
