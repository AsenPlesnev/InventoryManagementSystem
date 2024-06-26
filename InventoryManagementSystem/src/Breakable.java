/**
 * Interface indicating items that can break.
 * Defines a method to check if an item is breakable and method to handle the breakage.
 */
public interface Breakable {
    boolean isBreakable();
    void handleBreakage();
}
