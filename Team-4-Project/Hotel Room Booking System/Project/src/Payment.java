import java.text.SimpleDateFormat;
import java.util.*;

public class Payment {
    private int roomType;
    private int nights;
    private CustomerDetails customerDetails;
    private int totalCost;

    public Payment(int roomType, int nights, CustomerDetails customerDetails, int totalCost) {
        this.roomType = roomType;
        this.nights = nights;
        this.customerDetails = customerDetails;
        this.totalCost = totalCost;
    }

    public void processPayment(Scanner sc) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date transactionDate = new Date();
        System.out.println("\nTransaction Date: " + sdf.format(transactionDate));

        System.out.println("Choose payment method:");
        System.out.println("1) Cash");
        System.out.println("2) Credit Card");
        System.out.println("3) Debit Card");
        System.out.println("4) UPI");

        int paymentOption = sc.nextInt();
        sc.nextLine();
        String paymentMethod = "";

        switch (paymentOption) {
            case 1: paymentMethod = "Cash"; break;
            case 2: paymentMethod = "Credit Card"; break;
            case 3: paymentMethod = "Debit Card"; break;
            case 4: paymentMethod = "UPI"; break;
            default:
                System.out.println("Invalid payment option selected.");
                return;
        }

        System.out.println("\nPayment Method: " + paymentMethod);
        System.out.println("Room Type: " + (roomType == 1 ? "AC" : "Non-AC"));
        System.out.println("Total Cost: ₹" + totalCost);
        System.out.println("Transaction Successful!");

        System.out.println("\n========================Receipt============================");
        System.out.printf("%-20s%-25s\n", "Customer ID:", customerDetails.getId());
        System.out.printf("%-20s%-25s\n", "Customer Name:", customerDetails.getName());
        System.out.printf("%-20s%-25s\n", "Room No:", customerDetails.getBookedRoomNo());
        System.out.printf("%-20s%-25s\n", "No. of Nights:", nights);
        System.out.printf("%-20s%-25s\n", "Total Amount:", totalCost);
        System.out.printf("%-20s%-25s\n", "Payment Method:", paymentMethod);
        System.out.printf("%-20s%-25s\n", "Transaction Date:", sdf.format(transactionDate));
        System.out.println("===============================================================");
    }
}
