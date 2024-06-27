import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Command-line interface for interacting with the inventory management system.
 * Provides options to add items, remove items by ID, display a list of items, categorize items, place orders, remove orders, list orders, process orders and save/load inventory.
 */

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        InventoryManager inventoryManager = new InventoryManager();
        PaymentProcessor paymentProcessor = new PaymentProcessor();

        System.out.println("Welcome to the Inventory Management System");

        boolean isRunning = true;

        while (isRunning) {
            displayMenu();
            int choice = getChoice(sc);

            switch (choice) {
                case 1:
                    addNewItem(sc, inventoryManager);
                    break;
                case 2:
                    removeItem(sc, inventoryManager);
                    break;
                case 3:
                    displayItems(inventoryManager);
                    break;
                case 4:
                    categorizeItems(inventoryManager);
                    break;
                case 5:
                    placeOrder(sc, inventoryManager);
                    break;
                case 6:
                    removeOrder(sc, inventoryManager);
                    break;
                case 7:
                    listOrders(inventoryManager);
                    break;
                case 8:
                    addPaymentMethod(sc, paymentProcessor);
                    break;
                case 9:
                    removePaymentMethod(sc, paymentProcessor);
                    break;
                case 10:
                    listPaymentMethods(paymentProcessor);
                    break;
                case 11:
                    processOrder(sc, inventoryManager, paymentProcessor);
                    break;
                case 12:
                    saveInventory(sc, inventoryManager);
                    break;
                case 13:
                    loadInventory(sc, inventoryManager);
                    break;
                case 14:
                    isRunning = false;
                    System.out.println("Exiting the Inventory Management System.......");
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid command. Please enter a number from 1 to 14.");
                    System.out.println();
                    break;
            }
        }
    }

    /**
     * Displays the main menu options.
     */
    public static void displayMenu() {

        System.out.println("Menu [Enter your choice (1 - 14)]:");
        System.out.println("1. Add New Item");
        System.out.println("2. Remove Item by ID");
        System.out.println("3. Display List of Items");
        System.out.println("4. Categorize Items");
        System.out.println("5. Place Order");
        System.out.println("6. Remove Order");
        System.out.println("7. List Orders");
        System.out.println("8. Add Payment Method");
        System.out.println("9. Remove Payment Method");
        System.out.println("10. List Payment Methods");
        System.out.println("11. Process Payment and Complete Order");
        System.out.println("12. Save Inventory");
        System.out.println("13. Load Inventory");
        System.out.println("14. Exit");
        System.out.println();
    }


    public static int getChoice(Scanner sc) {
        int choice;
        try {
            choice = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            choice = 0;// Invalid choice
        }

        return choice;
    }

    /**
     * Prompts the user to add a new item to the inventory.
     */
    public static void addNewItem(Scanner sc, InventoryManager manager) {
        try {
            System.out.println("Enter item type (Electronics, Grocery, Fragile): ");
            String itemType = sc.nextLine().trim().toLowerCase();

            System.out.println("Enter item details:");
            System.out.print("Name: ");
            String name = sc.nextLine();
            System.out.print("Item ID: ");
            int itemID = Integer.parseInt(sc.nextLine());
            System.out.print("Quantity: ");
            int quantity = Integer.parseInt(sc.nextLine());
            System.out.print("Description: ");
            String description = sc.nextLine();
            System.out.print("Price: ");
            double price = Double.parseDouble(sc.nextLine());

            InventoryItem newItem = null;

            switch (itemType) {
                case "electronics":
                    try {
                        System.out.println("Warranty Date (YYYY-MM-DD): ");
                        String warranty = sc.nextLine();
                        newItem = new ElectronicsItem(name, itemID, quantity, warranty, description, price);
                    } catch (NumberFormatException | InputMismatchException e) {
                        System.out.println("Invalid input. Please enter valid details.");
                        System.out.println();
                    }
                    break;
                case "grocery":
                    try {
                        System.out.print("Expiration Date (YYYY-MM-DD): ");
                        String expirationDate = sc.nextLine();
                        newItem = new GroceryItem(name, itemID, quantity, expirationDate, description, price);
                    } catch (NumberFormatException | InputMismatchException e) {
                        System.out.println("Invalid input. Please enter valid details.");
                        System.out.println();
                    }
                    break;
                case "fragile":
                    try {
                        System.out.print("Weight: ");
                        double weight = Double.parseDouble(sc.nextLine());
                        newItem = new FragileItem(name, itemID, quantity, weight, description, price);
                    } catch (NumberFormatException | InputMismatchException e) {
                        System.out.println("Invalid input. Please enter valid details.");
                        System.out.println();
                    }
                    break;
                default:
                    System.out.println("Invalid item type.");
                    System.out.println();
                    return;
            }

            manager.addItem(newItem);
            System.out.println("Item added successfully.");
            System.out.println();
        } catch (IllegalArgumentException | InputMismatchException e) {
            System.out.println(e.getMessage());
            System.out.println();
        }
    }

    /**
     * Prompts the user to remove an item from the inventory by ID.
     */
    public static void removeItem(Scanner sc, InventoryManager manager) {
        if (manager.checkForItems()) {
            return;
        }

        try {
            System.out.print("Enter Item ID to remove: ");
            int itemID = Integer.parseInt(sc.nextLine());

            manager.removeItem(itemID);
            System.out.println("Item removed successfully.");
            System.out.println();
        } catch (NumberFormatException | NoSuchElementException e) {
            System.out.println("Invalid input. Please enter a valid Item ID.");
            System.out.println();
        }
    }

    /**
     * Displays all items in the inventory.
     */
    public static void displayItems(InventoryManager manager) {
        System.out.println("Inventory Items:");
        manager.displayItems();
        System.out.println();
    }

    /**
     * Displays all items by category in the inventory.
     */
    public static void categorizeItems(InventoryManager manager) {
        if (manager.checkForItems()) {
            return;
        }

        System.out.println("List of Electronics Items:");
        manager.displayItemsByCategory("Electronics");
        System.out.println();

        System.out.println("List of Grocery Items:");
        manager.displayItemsByCategory("Grocery");
        System.out.println();

        System.out.println("List of Fragile Items:");
        manager.displayItemsByCategory("Fragile");
        System.out.println();
    }

    /**
     * Prompts the user to place an order.
     */
    public static void placeOrder(Scanner sc, InventoryManager manager) {
        if (manager.checkForItems()) {
            System.out.println("There are no items in the inventory!");
            System.out.println();
            return;
        }

        try {
            System.out.println("Enter order details:");
            System.out.print("Number of items to order: ");
            int itemCount = Integer.parseInt(sc.nextLine());

            HashMap<Integer, Integer> itemsToOrder = new HashMap<>();
            for (int i = 0; i < itemCount; i++) {
                System.out.print("Enter Item ID for item " + (i + 1) + ": ");
                int itemID = Integer.parseInt(sc.nextLine());

                // Check if item exists
                if (manager.getItem(itemID) == null) {
                    System.out.println("Item with ID " + itemID + " not found.");
                    System.out.println();
                    return;
                }

                System.out.print("Enter quantity for item " + (i + 1) + ": ");
                int quantity = Integer.parseInt(sc.nextLine());

                itemsToOrder.put(itemID, quantity);
            }

            // Create order and add to system
            manager.createOrder(itemsToOrder);
        } catch (IllegalArgumentException | InputMismatchException e) {
            System.out.println("Invalid input. Please enter valid details.");
            System.out.println(e.getMessage());
            System.out.println();
        }
    }

    /**
     * Prompts the user to remove an order.
     */
    public static void removeOrder(Scanner sc, InventoryManager manager) {
        if (manager.checkForOrders()) {
            return;
        }

        try {
            System.out.println("Enter the ID of the order you want to remove:");
            int orderId = Integer.parseInt(sc.nextLine());

            manager.removeOrder(orderId);
            System.out.println("Order with ID " + orderId + " is removed successfully!");
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            System.out.println();
        }

    }

    /**
     * Lists all orders in the inventory.
     */
    public static void listOrders(InventoryManager manager) {
        if (manager.checkForOrders()) {
            return;
        }

        System.out.println("List Of Orders:");
        manager.displayOrders();
        System.out.println();
    }

    /**
     * Prompts the user to add a new payment method to the payment processor.
     */
    public static void addPaymentMethod(Scanner sc, PaymentProcessor processor) {
        System.out.println("Enter payment type (Credit Card or PayPal): ");
        String paymentType = sc.nextLine().trim().toLowerCase();

        switch (paymentType) {
            case "credit card":
                System.out.println("Enter card number: ");
                String cardNumber = sc.nextLine();
                System.out.println("Enter holder's name: ");
                String holder = sc.nextLine();
                System.out.println("Enter expiration date: ");
                String expirationDate = sc.nextLine();
                System.out.println("Enter CCV: ");
                String ccv = sc.nextLine();

                if (!processor.validateCreditCardPayment(cardNumber, holder, expirationDate, ccv)) {
                    System.out.println("Invalid card details. Card Number must be 16 symbols, CCV is 3 symbols. All fields are required.");
                    System.out.println();
                    return;
                }

                try {
                    processor.addPaymentMethod(cardNumber, ccv);
                    System.out.println("Credit card " + cardNumber + " is successfully added to the system!");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    System.out.println();
                }
                break;
            case "paypal":
                System.out.println("Enter PayPal account's email: ");
                String email = sc.nextLine();
                System.out.println("Enter PayPal account's password: ");
                String password = sc.nextLine();

                if (!processor.validatePayPalPayment(email, password)) {
                    System.out.println("Invalid PayPal credentials. Email or password can't be empty!");
                    System.out.println();
                    return;
                }

                try {
                    processor.addPaymentMethod(email, password);
                    System.out.println("PayPal account " + email + " is successfully added to the system!");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    System.out.println();
                }
                break;
            default:
                System.out.println("Invalid payment method!");
                System.out.println();
        }
    }


    /**
     * Prompts the user to remove a payment method from the payment processor.
     */
    public static void removePaymentMethod(Scanner sc, PaymentProcessor processor) {
        if (processor.checkForPaymentMethods()) {
            System.out.println("There are no payment methods added to the system!");
            System.out.println();
            return;
        }

        try {
            System.out.println("Enter the key of the payment method you want to remove (Credit card number or PayPal email):");
            String paymentMethodKey = sc.nextLine();

            processor.removePaymentMethod(paymentMethodKey);
            System.out.println("Payment method with credential " + paymentMethodKey + " is removed successfully!");
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            System.out.println();
        }
    }

    /**
     * Prompts the user to list all payment methods in the payment processor.
     */
    public static void listPaymentMethods(PaymentProcessor processor) {
        if (processor.checkForPaymentMethods()) {
            System.out.println("There are no payment methods added to the system!");
            System.out.println();
            return;
        }

        processor.displayPaymentMethods();
    }

    /**
     * Prompts the user to process an order.
     */
    public static void processOrder(Scanner sc, InventoryManager manager, PaymentProcessor processor) {
        if (manager.checkForOrders()) {
            return;
        }

        if (processor.checkForPaymentMethods()) {
            System.out.println("There are no payment methods added to the system! Add at least one to process an order!");
            System.out.println();
            return;
        }

        try {
            System.out.println("Enter the ID of the order you want to process:");
            int orderId = Integer.parseInt(sc.nextLine());

            if (manager.getOrderById(orderId) == null) {
                System.out.println("Order with ID " + orderId + " doesn't exist!");
                System.out.println();
                return;
            }

            System.out.println("Enter payment amount: ");
            double amount = Double.parseDouble(sc.nextLine());

            if (amount <= 0) {
                System.out.println("Payment amount must be greater than 0!");
                System.out.println();
                return;
            }

            System.out.println("Enter payment method (Credit Card or PayPal): ");
            String method = sc.nextLine().trim().toLowerCase();

            System.out.println("Enter payment key (Card number or PayPal email): ");
            String methodKey = sc.nextLine();

            System.out.println("Enter payment credential (Card CCV or PayPal password): ");
            String methodCredential = sc.nextLine();

            switch (method) {
                case "credit card":
                    processor.processCreditCardPayment(amount, methodKey, methodCredential);
                    break;
                case "paypal":
                    processor.processPayPalPayment(amount, methodKey, methodCredential);
                    break;
                default:
                    System.out.println("Invalid payment method!");
                    System.out.println();
                    return;
            }

            manager.processOrder(orderId, amount);


        } catch (NoSuchElementException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println();
        }
    }

    /**
     * Prompts the user to save the inventory to a file.
     */
    public static void saveInventory(Scanner sc, InventoryManager manager) {
        try {
            System.out.print("Enter file name to save inventory (e.g., inventory.ser): ");
            String filename = sc.nextLine();
            manager.saveInventory(filename);
            System.out.println("Inventory saved successfully to file: " + filename);
            System.out.println();
        } catch (Exception e) {
            System.out.println("Error saving inventory: " + e.getMessage());
            System.out.println();
        }
    }

    /**
     * Prompts the user to load the inventory from a file.
     */
    public static void loadInventory(Scanner sc, InventoryManager manager) {
        try {
            System.out.print("Enter file name to load inventory (e.g., inventory.ser): ");
            String filename = sc.nextLine();
            manager.loadInventory(filename);
            System.out.println("Inventory loaded successfully from file: " + filename);
            System.out.println();
        } catch (Exception e) {
            System.out.println("Error loading inventory: " + e.getMessage());
            System.out.println();
        }
    }
}