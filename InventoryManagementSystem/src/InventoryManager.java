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
        this.inventoryItems.put(item.getId(), item);
    }

    public boolean checkForItems() {
        if (this.inventoryItems.isEmpty()) {
            System.out.println("No items in inventory.");
            System.out.println();
            return true;
        }

        return false;
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
            System.out.println();
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
            System.out.println(item.getItemDetails());
            item.displayDescription();
            System.out.println();
        }
    }

    public Order getOrderById(int id) {
        for (Order order : this.orders) {
            if (order.getOrderID() == id) {
                return order;
            }
        }

        return null;
    }

    public void displayOrders() {
        for (Order order : this.orders) {
            System.out.println(order);
            System.out.println();
        }
    }

    public boolean checkForOrders() {
        if (this.orders.isEmpty()) {
            System.out.println("There are no orders placed!");
            System.out.println();
            return true;
        }

        return false;
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

        this.orders.add(order);

        System.out.println("Order created successfully with total: " + order.calculateOrderTotal(inventoryItems));
        System.out.println();
    }

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

    public void processOrder(int orderId, Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("Order can't be processed without a payment!");
        }
        Order order = getOrderById(orderId);

        if (order == null) {
            throw new NoSuchElementException("Order with ID " + orderId + " doesn't exist!");
        }

        double total = order.calculateOrderTotal(inventoryItems);

        if (payment.getAmount() < total) {
            throw new IllegalArgumentException("Insufficient payment. The total of the order is " + total + " and payment amount is " + payment.getAmount());
        }

        System.out.println("Order with ID " + orderId + " and total: " + total + " was successfully processed and paid by " + payment.getPaymentMethod());
        System.out.println();

        orders.remove(order);
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
