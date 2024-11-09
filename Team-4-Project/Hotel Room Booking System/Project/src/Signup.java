import java.io.*;
import java.util.*;

public class Signup {
    private static HashMap<String, CustomerDetails> customer = new HashMap<>();
    private static boolean[] roomAvailability = new boolean[16]; // 16 rooms (room number 1 to 15)
    private static final String CUSTOMER_DETAILS_FILE = "customerDetails.txt";
    private static final String CUSTOMER_ID_COUNTER_FILE = "customerIdCounter.txt";

    static {
        for (int i = 1; i <= 15; i++) {
            roomAvailability[i] = true; // All rooms are initially available
        }
    }

    public Signup() {
        loadCustomerDetails();  // Load customer details from file when app starts
    }

    // Load customer details from file
    private void loadCustomerDetails() {
        try {
            File customerFile = new File(CUSTOMER_DETAILS_FILE);
            if (!customerFile.exists()) {
                customerFile.createNewFile();  // Create the file if it doesn't exist
            }

            BufferedReader br = new BufferedReader(new FileReader(CUSTOMER_DETAILS_FILE));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                // Split the line by commas
                String[] details = line.split(",");

                // Ensure that we have exactly 4 fields
                if (details.length == 4) {
                    // Parse customer details
                    int id = Integer.parseInt(details[0].trim());  // Parse the ID as an integer
                    String name = details[1].trim();
                    String mobileNo = details[2].trim();
                    long aadharNo = Long.parseLong(details[3].trim());

                    // Add customer to the map with the parsed ID and details
                    customer.put(String.valueOf(id), new CustomerDetails(name, mobileNo, aadharNo, id, -1));
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("No previous customer records found or file error.");
        } catch (NumberFormatException e) {
            System.out.println("Error parsing customer details: " + e.getMessage());
        }
    }

    // Save customer details to file
    private void saveCustomerDetails() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CUSTOMER_DETAILS_FILE))) {
            for (CustomerDetails cd : customer.values()) {
                bw.write(cd.getId() + "," + cd.getName() + "," + cd.getMobileNo() + "," + cd.getAadharNo() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving customer details to file.");
        }
    }

    // Book a room (during signup or login process)
    public void bookRoom(CustomerDetails cd, Scanner sc) {
        System.out.println("Enter room type:");
        System.out.println("1) AC");
        System.out.println("2) Non-AC");
        int roomType = sc.nextInt();
        sc.nextLine();

        // Available room selection logic
        int[] availableRooms = getAvailableRooms(roomType);  // Logic for getting available rooms
        if (availableRooms.length == 0) {
            System.out.println("No rooms available.");
            return;
        }

        System.out.println("Available rooms:");
        for (int room : availableRooms) {
            System.out.print(room + " ");
        }

        int roomNo = -1;
        while (true) {
            System.out.print("\nSelect Your Room No: ");
            roomNo = sc.nextInt();
            sc.nextLine();

            // Check if room is available and belongs to the selected room type
            if (isRoomAvailable(roomNo) && isValidRoomForType(roomNo, roomType)) {
                roomAvailability[roomNo] = false;  // Mark the room as booked
                cd.setBookedRoomNo(roomNo);  // Update the booked room number
                saveCustomerDetails(); // Save the updated customer details
                System.out.println("Room " + roomNo + " booked successfully.");
                break;  // Exit the loop when a valid room is selected
            } else {
                System.out.println("Invalid room number or room already booked. Please select a valid room.");
            }
        }

        // After booking, ask for number of nights and handle payment
        System.out.print("Enter the number of nights you want to stay: ");
        int nights = sc.nextInt();
        sc.nextLine();

        // Create Payment instance
        Payment payment = new Payment(roomType, nights);
        payment.processPayment(sc);
    }

    private int[] getAvailableRooms(int roomType) {
        // Get available rooms based on room type
        int start = (roomType == 1) ? 1 : 8;  // Assume 1-7 are AC rooms and 8-15 are Non-AC rooms
        int end = (roomType == 1) ? 7 : 15;

        List<Integer> availableRooms = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            if (roomAvailability[i]) {
                availableRooms.add(i);
            }
        }
        return availableRooms.stream().mapToInt(i -> i).toArray();
    }

    private boolean isRoomAvailable(int roomNo) {
        return roomNo >= 1 && roomNo <= 15 && roomAvailability[roomNo];
    }

    private boolean isValidRoomForType(int roomNo, int roomType) {
        // Room 1-7 are AC rooms, Room 8-15 are Non-AC rooms
        if (roomType == 1) {
            return roomNo >= 1 && roomNo <= 7;  // AC rooms should be in the range 1-7
        } else if (roomType == 2) {
            return roomNo >= 8 && roomNo <= 15;  // Non-AC rooms should be in the range 8-15
        }
        return false;
    }

    // Handle customer login
    public void login(Scanner sc) {
        System.out.print("Please enter your Customer ID: ");
        String custId = sc.nextLine();
        CustomerDetails cd = customer.get(custId);

        if (cd != null) {
            System.out.println("Customer Details:");
            System.out.println("==========================");
            System.out.println(cd);

            // Ask if they want to vacate a room
            System.out.println("Would you like to vacate any room?");
            System.out.println("1) Yes");
            System.out.println("2) No");
            int vacateOption = sc.nextInt();
            sc.nextLine();

            if (vacateOption == 1) {
                System.out.print("Enter room number to vacate: ");
                int roomNoToVacate = sc.nextInt();
                sc.nextLine();
                vacateRoom(roomNoToVacate);
            }

        } else {
            System.out.println("Customer ID not found. Please try again.");
        }
    }

    // Handle customer signup
    public void signup(Scanner sc) {
        System.out.println("Please Sign Up to Continue...");

        String name;
        while (true) {
            System.out.print("Enter your Name: ");
            name = sc.nextLine();
            if (name.matches("[A-Za-z ]+")) {
                break;
            } else {
                System.out.println("Invalid name!!");
            }
        }

        String mobileNo;
        while (true) {
            System.out.print("Enter Mobile No: ");
            mobileNo = sc.nextLine();
            if (mobileNo.matches("\\d{10}")) {
                break;
            } else {
                System.out.println("Invalid mobile number!!!");
            }
        }

        String aadharNo;
        while (true) {
            System.out.print("Enter your Aadhar No: ");
            aadharNo = sc.nextLine();
            if (aadharNo.matches("\\d{12}")) {
                break;
            } else {
                System.out.println("Invalid Aadhar number!!!.");
            }
        }

        int id = generateUniqueId();
        CustomerDetails cd = new CustomerDetails(name, mobileNo, Long.parseLong(aadharNo), id, -1);
        customer.put(String.valueOf(id), cd);
        saveCustomerDetails();

        System.out.println("Customer Details...");
        System.out.println("==========================");
        System.out.println(cd);
        System.out.println("Signup Successful !!!");

        // Now, proceed with booking
        System.out.println("Would you like to check availability and book a room?");
        System.out.println("1) Yes");
        System.out.println("2) Exit");
        int option = sc.nextInt();
        sc.nextLine();

        if (option == 1) {
            bookRoom(cd, sc);
        }
    }

    private void vacateRoom(int roomNo) {
        if (roomNo >= 1 && roomNo <= 15 && !roomAvailability[roomNo]) {
            roomAvailability[roomNo] = true;
            System.out.println("Room " + roomNo + " vacated successfully.");
        } else {
            System.out.println("Invalid room number or room was not booked.");
        }
    }

    // Generate unique Customer ID
    private int generateUniqueId() {
        int id = 1000;
        try {
            BufferedReader br = new BufferedReader(new FileReader(CUSTOMER_ID_COUNTER_FILE));
            String line = br.readLine();
            if (line != null) {
                id = Integer.parseInt(line) + 1;
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error reading ID counter file.");
        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(CUSTOMER_ID_COUNTER_FILE));
            bw.write(String.valueOf(id));
            bw.close();
        } catch (IOException e) {
            System.out.println("Error saving ID counter.");
        }
        return id;
    }
}
