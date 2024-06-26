import java.io.Serializable;

/**
 * Abstract class that implements Item, Categorizable, Breakable, Perishable, and Sellable interfaces.
 * Provides common functionality such as getting item details and default implementations for category, breakable, perishable, and sellable attributes.
 */

public abstract class AbstractItem implements Item, Categorizable, Breakable, Perishable, Sellable, Serializable {
    //This helps maintain compatibility across different versions of the program
    private static final long serialVersionUID = 1L;
    private String name;
    private String category;
    private String description;
    private boolean breakable;
    private boolean perishable;
    private double price;

    // Default constructor for serialization
    public AbstractItem() {

    }

    // Constructor to initialize item details
    public AbstractItem(String name, String description, boolean breakable, boolean perishable, double price) {
        this.name = name;
        this.description = description;
        this.breakable = breakable;
        this.perishable = perishable;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String getItemDetails() {
        return "Name of product: " + this.name + ", Category: " + this.category + ", Price: " + this.price;
    }

    @Override
    public double calculateValue(int quantity) {
        return this.price * quantity;
    }

    @Override
    public void displayDescription() {
        System.out.println("Description of product " + this.name);
        System.out.println(this.description);
    }

    //Checks if Category is already set and if it is throws an error
    @Override
    public void setCategory(String category) {
        if (this.category != null) {
            throw new UnsupportedOperationException("Category cannot be changed once set.");
        }
        this.category = category;
    }

    @Override
    public String getCategory() {
        return this.category;
    }

    @Override
    public boolean isBreakable() {
        return this.breakable;
    }

    @Override
    public void handleBreakage() {
        if (this.breakable) {
            System.out.println("Product " + this.name + " has been broken");
        } else {
            System.out.println("Product " + this.name + " is not breakable!");
        }
    }

    @Override
    public boolean isPerishable() {
        return this.perishable;
    }

    @Override
    public void handleExpiration() {
        if (this.perishable) {
            System.out.println("Product " + this.name + " has expired");
        } else {
            System.out.println("Product " + this.name + " is not perishable!");
        }
    }

    //Checks if the new price is < 0 and if so throws an error
    @Override
    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price can't be 0 or less!");
        }

        this.price = price;
    }

    @Override
    public double getPrice() {
        return this.price;
    }
}
