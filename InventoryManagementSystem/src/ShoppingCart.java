import java.util.HashMap;
import java.util.NoSuchElementException;

public class ShoppingCart {
    private HashMap<Integer, Integer> items;

    public ShoppingCart() {
        this.items = new HashMap<>();
    }

    public void addItem(int itemId, int quantity) {
        if (this.items.containsKey(itemId)) {
            items.put(itemId, items.get(itemId) + quantity);
        } else {
            items.put(itemId, quantity);
        }
        System.out.println("Item added to cart: ID=" + itemId + ", Quantity=" + quantity);
        System.out.println();
    }

    public void removeItem(int itemId) {
        if (!items.containsKey(itemId)) {
            throw new NoSuchElementException("Item not found in cart: ID = " + itemId);
        }
        items.remove(itemId);
        System.out.println("Item removed from cart: ID=" + itemId);
    }

    public void viewCart() {
        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Cart items:");
            for (var entry : items.entrySet()) {
                System.out.println("Item ID: " + entry.getKey() + ", Quantity: " + entry.getValue());
            }
        }
    }

    public HashMap<Integer, Integer> getItems() {
        return new HashMap<>(items);
    }

    public void clearCart() {
        items.clear();
    }
}
