public class Booking {
    private int roomNumber;
    private int numberOfNights;
    private int totalAmount;
    private String paymentMethod;
    private boolean isPaid;

    public Booking(int roomNumber, int numberOfNights, int totalAmount, String paymentMethod) {
        this.roomNumber = roomNumber;
        this.numberOfNights = numberOfNights;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.isPaid = false;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public void setNumberOfNights(int numberOfNights) {
        this.numberOfNights = numberOfNights;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    @Override
    public String toString() {
        return "Booking [Room No: " + roomNumber + ", Nights: " + numberOfNights + ", Total Amount: " + totalAmount
                + ", Payment Method: " + paymentMethod + "]";
    }
}
