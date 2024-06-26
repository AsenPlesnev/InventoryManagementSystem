/**
 * Interface representing items that can perish.
 * Defines a method to check if an item is perishable and handle the expiration.
 */
public interface Perishable {
    boolean isPerishable();
    void handleExpiration();
}
