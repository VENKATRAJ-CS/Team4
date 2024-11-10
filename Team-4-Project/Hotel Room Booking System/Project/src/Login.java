import java.util.*;

public class Login {
    private Map<String, CustomerDetails> customer;

    public Login(Map<String, CustomerDetails> customer) {
        this.customer = customer;
    }

    public void login(Scanner sc) {
        System.out.print("Please enter your Customer ID: ");
        String custId = sc.nextLine();
        CustomerDetails cd = customer.get(custId);

        if (cd != null) {
            System.out.println("Customer Details:");
            System.out.println("===================================================================");
            System.out.printf("%-15s%-25s%-15s%-15s\n", "ID", "Name", "Mobile No", "Aadhar No");
            System.out.println("===================================================================");

            System.out.printf("%-15d%-25s%-15s%-15s\n", cd.getId(), cd.getName(), cd.getMobileNo(), cd.getAadharNo());
            System.out.println("===================================================================");

            if (cd.getBookedRoomNo() != -1) {
                System.out.println("Your booked room number: " + cd.getBookedRoomNo());
                System.out.println("Would you like to vacate your room?");
                System.out.println("1) Yes");
                System.out.println("2) No");
                int vacateOption = sc.nextInt();
                sc.nextLine();

                if (vacateOption == 1) {
                    vacateRoom(cd.getBookedRoomNo());
                    cd.setBookedRoomNo(-1);  // Reset booked room for the customer
                }
            } else {
                System.out.println("You have not booked any room yet.");
            }
        } else {
            System.out.println("Customer ID not found. Please try again.");
        }
    }

    private void vacateRoom(int roomNo) {
        if (roomNo >= 1 && roomNo <= 30 && !Signup.roomAvailability[roomNo]) {
            Signup.roomAvailability[roomNo] = true; // Mark room as available again
            System.out.println("Room No: " + roomNo + " vacated successfully.");
            System.out.println("Thank You For Using Our Easy Rest Hotel ...Visit Again🙏🙏🙏🙏🙏");
        } else {
            System.out.println("Invalid room number or the room was not booked.");
        }
    }

    public void bookRoom(CustomerDetails cd, Scanner sc) {
        System.out.println("Enter room type:");
        System.out.println("1) AC");
        System.out.println("2) Non-AC");
        int roomType = sc.nextInt();
        sc.nextLine();

        int[] availableRooms = getAvailableRooms(roomType);
        if (availableRooms.length == 0) {
            System.out.println("No rooms available.");
            return;
        }

        System.out.println("Available rooms: ");
        for (int room : availableRooms) {
            System.out.print(room + " ");
        }
        System.out.println();

        int roomNo = -1;
        while (true) {
            System.out.print("\nSelect Your Room No: ");
            roomNo = sc.nextInt();
            sc.nextLine();

            if (isRoomAvailable(roomNo)) {
                Signup.roomAvailability[roomNo] = false; // Mark room as unavailable
                cd.setBookedRoomNo(roomNo);
                System.out.println("Room No: " + roomNo + " Selected successfully.");
                break;
            } else {
                System.out.println("Room No: " + roomNo + " is already booked, please select another room.");
            }
        }

        System.out.print("Enter the number of nights you want to stay: ");
        int nights = sc.nextInt();
        sc.nextLine();

        int totalCost = (roomType == 1) ? 1500 * nights : 1200 * nights;
        System.out.println("Total cost: ₹" + totalCost);

        System.out.println("1) Proceed to Payment");
        System.out.println("2) Cancel Booking");
        int bookingOption = sc.nextInt();
        sc.nextLine();

        if (bookingOption == 1) {
            Payment payment = new Payment(roomType, nights, cd, totalCost);
            payment.processPayment(sc);
        } else {
            // If booking is canceled, mark the room as available again
            Signup.roomAvailability[roomNo] = true;
            System.out.println("Booking canceled, room No: " + roomNo + " is available again.");
        }
    }

    private boolean isRoomAvailable(int roomNo) {
        return roomNo >= 1 && roomNo <= 30 && Signup.roomAvailability[roomNo];
    }

    private int[] getAvailableRooms(int roomType) {
        List<Integer> availableRooms = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            if (Signup.roomAvailability[i] && (roomType == 1 && i <= 15 || roomType == 2 && i > 15)) {
                availableRooms.add(i);
            }
        }
        return availableRooms.stream().mapToInt(i -> i).toArray();
    }
}
