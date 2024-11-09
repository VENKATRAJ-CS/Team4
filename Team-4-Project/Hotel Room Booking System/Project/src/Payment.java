import java.util.Scanner;

public class Payment {
    private int roomType;
    private int nights;

    public Payment(int roomType, int nights) {
        this.roomType = roomType;
        this.nights = nights;
    }

    public void processPayment(Scanner sc) {
        double ratePerNight = (roomType == 1) ? 1500 : 1000;  // Assume AC room costs 1500 per night, Non-AC costs 1000
        double totalCost = ratePerNight * nights;

        System.out.println("Total cost for " + nights + " nights: ₹" + totalCost);

        System.out.print("Enter payment amount: ₹");
        double paidAmount = sc.nextDouble();
        sc.nextLine();

        if (paidAmount >= totalCost) {
            double change = paidAmount - totalCost;
            System.out.println("Payment successful! Your change: ₹" + change);
        } else {
            System.out.println("Insufficient payment. Try again.");
        }
    }
}
