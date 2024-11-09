import java.util.Scanner;

public class RoomBooking {

    public void room(Scanner sc, CustomerDetails cd) {
        System.out.println("Enter room type:");
        System.out.println("1) AC");
        System.out.println("2) Non-AC");
        int roomType = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter the number: ");
        if (roomType == 1) {
            System.out.println("Bringing available AC rooms...");
        } else {
            System.out.println("Bringing available Non-AC rooms...");
        }

        int[] availableRooms = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        System.out.println("Available rooms:");
        for (int room : availableRooms) {
            System.out.print(room + " ");
        }
        System.out.println();

        System.out.print("Select Your Room No: ");
        int roomNo = sc.nextInt();
        sc.nextLine();

        cd.setBookedRoomNo(roomNo);
        System.out.println("Room no: " + roomNo + " selected successfully.");
        System.out.println("Room booking completed for " + cd.getName() + ". Enjoy your stay!");

        System.out.println("Would you like to vacate any room?");
        System.out.println("1) Yes");
        System.out.println("2) No");
        int vacateOption = sc.nextInt();
        sc.nextLine();
        if (vacateOption == 1) {
            
        } else {
            System.out.println("Thank you for your booking. Exiting the application.");
        }
    }
}
