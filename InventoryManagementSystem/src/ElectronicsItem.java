import java.time.LocalDate;

public class ElectronicsItem extends InventoryItem{
    //Each electronic item has a warranty presented in Months.
    private LocalDate warranty;

    public ElectronicsItem() {

    }

    //We set the category to Electronics and breakable to true because this type of item can break and perishable to false
    public ElectronicsItem(String name, int id, int quantity, String warranty, String description, double price) {
        super(name, id, quantity, description, true, false, price);
        setCategory("Electronics");
        this.warranty = LocalDate.parse(warranty);
    }

    public String getWarranty() {
        return this.warranty.toString();
    }

    @Override
    public String getItemDetails() {
        return super.getItemDetails() + ", Warranty: " + this.warranty;
    }

    @Override
    public void handleBreakage() {
        if (isBreakable()) {
            LocalDate today = LocalDate.now();

            if (today.getMonth() == this.warranty.getMonth() && today.getYear() == this.warranty.getYear() &&
                    today.getDayOfYear() == this.warranty.getDayOfYear()) {
                System.out.println("Product is broken but is still in warranty so it will be replaced!");
            } else {
                System.out.println("Product is broken and out of warranty! No replacement!");
            }
        }
    }
}
