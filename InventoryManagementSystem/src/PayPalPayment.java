public interface PayPalPayment {
    void processPayPalPayment(double total, String email, String password);
    boolean validatePayPalPayment(String email, String password);
}
