import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Class representing the payment processor which handles all payments made.
 * Manages a HashSet<String, String> which collects all payment methods added to the system.
 * Provides methods to add, remove, display, and process payments using different payment methods.
 */

public class PaymentProcessor implements CreditCardPayment, PayPalPayment {
    //Collection to keep all payment methods like <CardNumber/PayPal Email, Credential>>
    HashMap<String, String> paymentMethods;

    public PaymentProcessor() {
        this.paymentMethods = new HashMap<>();
    }

    /**
     * Checks if a payment method is already in the system
     * @param key The key with which the payment method is associated.
     */
    public boolean checkIfPaymentMethodExists(String key) {
        return this.paymentMethods.containsKey(key);
    }

    /**
     * Checks if the collection of payment methods is empty.
     * @return True if the collection is empty and false otherwise.
     */
    public boolean checkForPaymentMethods() {
        return this.paymentMethods.isEmpty();
    }

    /**
     * Adds a payment method in the system
     * @param key The key with which the payment method is associated.
     * @param value The credential with which the method is associated.
     * @throws IllegalArgumentException If the payment method is already in the system.
     */
    public void addPaymentMethod (String key, String value) {
        if (this.checkIfPaymentMethodExists(key)) {
            throw new IllegalArgumentException("Payment method is already in the system!");
        }

        this.paymentMethods.put(key, value);
    }

    /**
     * Removes a payment method from the system
     * @param key The key with which the payment method is associated.
     * @throws IllegalArgumentException If the payment method is not in the system.
     */
    public void removePaymentMethod(String key) {
        if (!this.checkIfPaymentMethodExists(key)) {
            throw new NoSuchElementException("Payment not found in the system!");
        }

        this.paymentMethods.remove(key);
    }

    /**
     * Displays all payment methods that are in the system.
     */
    public void displayPaymentMethods() {
        System.out.println("List of payment methods:");

        for (var method : this.paymentMethods.entrySet()) {
            System.out.println("  " + method.getKey());
        }
    }

    /**
     * Processes the payment with a credit card.
     * @param total The amount which has to be paid.
     * @param cardNumber The number of the card (Key) in our collection.
     * @param ccv The credential (value) in our collection.
     * @throws IllegalArgumentException If the payment method is not in the system or if the CCV is not the correct one.
     */
    @Override
    public void processCreditCardPayment(double total, String cardNumber, String ccv) {

        if (!this.checkIfPaymentMethodExists(cardNumber)) {
            throw new IllegalArgumentException("Card with number " + cardNumber + " doesn't exist.");
        }

        if (!this.paymentMethods.get(cardNumber).equals(ccv)) {
            throw new IllegalArgumentException("Invalid CCV!");
        }

        System.out.println("Payment with card " + cardNumber + " for " + total + " is completed!");
        System.out.println();
    }

    /**
     * Validates the data associated with a credit card.
     * @param cardNumber The number of the card (Key) in our collection.
     * @param holder The name of the person.
     * @param expiryDate Expiry date of the card.
     * @param ccv The credential (value) in our collection.
     * @return True if everything is valid or false if at least one field is not valid.
     */
    @Override
    public boolean validateCreditCardPayment(String cardNumber, String holder, String expiryDate, String ccv) {
        return cardNumber.length() == 16 && ccv.length() == 3 && !holder.isEmpty() && !expiryDate.isEmpty();
    }

    /**
     * Processes the payment with a PayPal account.
     * @param total The amount which has to be paid.
     * @param email The email of the PayPal account (Key) in our collection.
     * @param password The credential (value) in our collection.
     * @throws IllegalArgumentException If the payment method is not in the system or if the password is not the correct one.
     */
    @Override
    public void processPayPalPayment(double total, String email, String password) {
        if (!this.checkIfPaymentMethodExists(email)) {
            throw new IllegalArgumentException("PayPal account " + email + " doesn't exist.");
        }

        if (!this.paymentMethods.get(email).equals(password)) {
            throw new IllegalArgumentException("Invalid Password!");
        }

        System.out.println("Payment with PayPal account " + email + " for " + total + " is completed!");
        System.out.println();
    }

    /**
     * Validates the data associated with a PayPal account.
     * @param email The email of the account (Key) in our collection.
     * @param password The password of the account.
     * @return True if everything is valid or false if at least one field is not valid.
     */
    @Override
    public boolean validatePayPalPayment(String email, String password) {
        return !email.isEmpty() && !password.isEmpty();
    }
}
