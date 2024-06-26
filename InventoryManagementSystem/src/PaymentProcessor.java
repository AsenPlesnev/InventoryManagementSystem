import java.util.HashMap;

public class PaymentProcessor implements CreditCardPayment {
    //Collection to keep all payment methods like <CardNumber/PayPal Email, Credential>>
    HashMap<String, String> paymentMethods;

    public PaymentProcessor() {
        this.paymentMethods = new HashMap<>();
    }

    private boolean checkIfPaymentMethodExists(String key) {
        return this.paymentMethods.containsKey(key);
    }

    public void addCreditCard(double amount, String cardNumber, String holder, String expiryDate, String cvv) {
        if (!validateCreditCardPayment(amount, cardNumber, holder, expiryDate, cvv)) {
            throw new IllegalArgumentException("Invalid card details. Card Number must be 16 symbols, CCV is 3 symbols. All fields are required.");
        }

        if (this.checkIfPaymentMethodExists(cardNumber)) {
            throw new IllegalArgumentException("Card is already added to the system!");
        }

        this.paymentMethods.put(cardNumber, cvv);

        System.out.println("Card with number " + cardNumber + " is successfully added to the system.");
        System.out.println();
    }

    @Override
    public void processCreditCardPayment(double total, String cardNumber, String ccv) {
        if (!this.checkIfPaymentMethodExists(cardNumber)) {
            throw new IllegalArgumentException("Card with number " + cardNumber + " doesn't exist.");
        }

        if (!this.paymentMethods.get(cardNumber).equals(ccv)) {
            throw new IllegalArgumentException("Invalid CCV!");
        }

        System.out.println("Payment is with card " + cardNumber + " for " + total + " is completed!");
        System.out.println();
    }

    @Override
    public boolean validateCreditCardPayment(double amount, String cardNumber, String holder, String expiryDate, String cvv) {
        return cardNumber.length() == 16 && cvv.length() == 3 && !holder.isEmpty() && !expiryDate.isEmpty();
    }
}
