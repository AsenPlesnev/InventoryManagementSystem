import java.time.LocalDate;

public class GroceryItem extends InventoryItem {
    private LocalDate expirationDate;

    public GroceryItem() {

    }

    public GroceryItem(String name, int id, int quantity, String expirationDate, String description, double price) {
        super(name, id, quantity, description, false, true, price);
        setCategory("Grocery");
        this.expirationDate = LocalDate.parse(expirationDate);
    }

    public String getExpirationDate() {
        return expirationDate.toString();
    }

    @Override
    public double calculateValue(int quantity) {
        return getPrice() * quantity * 0.9; // 10% discount for groceries.
    }

    @Override
    public String getItemDetails() {
        return super.getItemDetails() + ", Expiration Date: " + this.expirationDate;
    }

    //Check if today is the same day as expirationDate or and if it is that means the product is expired.
    @Override
    public void handleExpiration() {
        LocalDate today = LocalDate.now();

        if (today.getMonth() == this.expirationDate.getMonth() && today.getYear() == this.expirationDate.getYear() &&
                today.getDayOfYear() == this.expirationDate.getDayOfYear()) {
            System.out.println("Product is expired");
        } else {
            System.out.println("Product is still good to use!");
        }
    }
}
