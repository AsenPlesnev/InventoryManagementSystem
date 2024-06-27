import java.util.HashMap;
import java.util.NoSuchElementException;

public class ShoppingCart {
    private HashMap<Integer, Integer> items;

    public ShoppingCart() {
        this.items = new HashMap<>();
    }

    /**
     * Checks if there are any items in the cart.
     * @return True if the cart is empty and false it there is at least one item.
     */
    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    /**
     * Adds and item in the shopping cart.
     * @param itemId The ID of the item we want to add.
     * @param quantity The quantity of the item we want to add.
     */
    public void addItem(int itemId, int quantity) {
        if (this.items.containsKey(itemId)) {
            items.put(itemId, items.get(itemId) + quantity);
        } else {
            items.put(itemId, quantity);
        }
        System.out.println("Item added to cart: ID=" + itemId + ", Quantity=" + quantity);
        System.out.println();
    }

    /**
     * Removes an item from the cart.
     * @param itemId The ID of the item we want to remove.
     * @throws NoSuchElementException If the ID given is invalid.
     */
    public void removeItem(int itemId) {
        if (!items.containsKey(itemId)) {
            throw new NoSuchElementException("Item not found in cart: ID = " + itemId);
        }
        items.remove(itemId);
        System.out.println("Item removed from cart: ID=" + itemId);
        System.out.println();
    }

    /**
     * Prints all items from the shopping cart.
     */
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

    /**
     * Gets a collection of all items in the cart represented like ItemID -> quantity.
     */
    public HashMap<Integer, Integer> getItems() {
        return new HashMap<>(items);
    }

    /**
     * Clears the shopping cart.
     */
    public void clearCart() {
        items.clear();
    }
}
