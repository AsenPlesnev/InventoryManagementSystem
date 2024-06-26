public interface CreditCardPayment {
    void processCreditCardPayment(double total, String cardNumber, String ccv);
    boolean validateCreditCardPayment(double amount, String cardNumber, String holder, String expiryDate, String cvv);
}
