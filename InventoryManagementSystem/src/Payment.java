public class Payment {
    private double amount;
    private String paymentMethod;

    public Payment(double amount, String paymentMethod) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    public double getAmount() {
        return this.amount;
    }

    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    @Override
    public String toString() {
        return String.format("Payment of %.2f made via %s", this.amount, this.paymentMethod);
    }
}
