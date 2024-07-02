# InventoryManagementSystem

# Inventory Management System & E-commerce Console Application

## Project Overview

This project involves developing a comprehensive Inventory Management System for a small store, which will be extended to an E-commerce Console Application that includes payment processing capabilities. The system will manage various types of items, handle payments, and process orders. The project is divided into several parts, with detailed requirements for each.

## Part 1: Inventory Management System

### Interfaces and Abstract Classes

1. **Item Interface **
   - Create an `Item` interface to represent items in the inventory.
   - Define methods for getting item details, calculating value, and displaying the item's description.

2. **Categorizable Interface **
   - Create a `Categorizable` interface for items that can be categorized.
   - Include methods for setting and getting the item category.

3. **Breakable Interface **
   - Create a `Breakable` interface for items that can break.
   - Include methods for checking if an item is breakable and for handling item breakage.

4. **Perishable Interface **
   - Create a `Perishable` interface for items that can perish.
   - Include methods for checking if an item is perishable and for handling item expiration.

5. **Sellable Interface **
   - Create a `Sellable` interface for items that can be sold.
   - Include methods for setting and getting item prices.

6. **Abstract Item Class **
   - Create an abstract class `AbstractItem` that implements the `Item`, `Categorizable`, `Breakable`, `Perishable`, and `Sellable` interfaces.
   - Implement common functionality such as getting item details.
   - Provide default implementations for category, breakable, perishable, and sellable attributes.

### Superclasses and Inheritance

7. **Inventory Superclass **
   - Create an `InventoryItem` superclass that extends `AbstractItem`.
   - Add instance variables for item ID and quantity.
   - Implement getters and setters for ID and quantity.

8. **Item Types **
   - Create subclasses for specific item types like `ElectronicsItem`, `GroceryItem`, and `FragileItem` that inherit from `InventoryItem`.
   - Implement constructors for these subclasses to set specific attributes like weight for fragile items.
   - Override relevant methods to calculate item values differently for each type.

### File I/O, User Interface, Payments, and Orders

9. **File I/O **
   - Implement methods to save and load inventory data to/from text files.
   - Use a well-defined file format for data storage.

10. **User Interface **
    - Create a command-line interface (CLI) to interact with the inventory system.
    - Allow users to add items, remove items by ID, display a list of items, categorize items, and place orders.
    - Display a menu for user choices and handle user input gracefully.

11. **Payments and Orders **
    - Implement classes for `Payment` and `Order`.
    - Allow users to create orders, calculate order totals, and process payments.
    - Update inventory quantities after orders are placed.

### Error Handling 

12. **Error Handling **
    - Implement robust error handling to address potential issues, such as invalid user input, file I/O errors, and handling exceptions properly.

## Part 2: E-commerce Console Application

### Payment Processing

1. **Payment Processor **
   - Create a `PaymentProcessor` class to handle payments.
   - Implement methods for processing payments using various payment methods (e.g., credit card, PayPal).
   - Include validation for payment methods and simulate payment authorization.

2. **Payment Methods **
   - Create interfaces or abstract classes for different payment methods such as `CreditCardPayment`, `PayPalPayment`, etc.
   - Implement payment methods using appropriate attributes (e.g., card number, PayPal account) and validation.
   - Use interfaces or abstract classes to ensure consistent payment processing.

### User Interface Enhancement

3. **E-commerce Console Interface **
   - Enhance the console interface to allow users to select and purchase items.
   - Implement the shopping cart functionality to add items to the cart, view the cart, and place orders.
   - Integrate payment processing into the ordering process.

### Order Processing

4. **Order Class **
   - Create an `Order` class to represent orders.
   - Include details such as order ID, items, quantities, total cost, and payment method.
   - Implement methods for calculating the order total, processing payments, and updating inventory quantities.

