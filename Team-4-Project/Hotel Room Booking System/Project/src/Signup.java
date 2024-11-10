import java.io.*;
import java.util.*;

public class Signup {
    static Map<String, CustomerDetails> customer = new HashMap<>();
    private static final String CUSTOMER_DETAILS_FILE = "customerDetails.txt";
    private static final String ROOM_BOOKINGS_FILE = "roomBookings.txt"; // To persist room booking info

    // Room availability array
    public static boolean[] roomAvailability = new boolean[31]; // Index 0 is unused, 1-30 represent rooms
    static {
        // Initialize all rooms as available (true)
        for (int i = 1; i <= 30; i++) {
            roomAvailability[i] = true;
        }
    }

    public Signup() {
        loadCustomerDetails();
    }

    public void loadCustomerDetails() {
        try {
            File customerFile = new File(CUSTOMER_DETAILS_FILE);
            if (!customerFile.exists()) {
                customerFile.createNewFile();
            }

            BufferedReader br = new BufferedReader(new FileReader(CUSTOMER_DETAILS_FILE));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] details = line.split(",");
                if (details.length == 5) {
                    int id = Integer.parseInt(details[0].trim());
                    String name = details[1].trim();
                    String mobileNo = details[2].trim();
                    long aadharNo = Long.parseLong(details[3].trim());
                    int bookedRoomNo = Integer.parseInt(details[4].trim());

                    customer.put(String.valueOf(id), new CustomerDetails(name, mobileNo, aadharNo, id, bookedRoomNo));
                    // Mark room availability based on booked rooms
                    if (bookedRoomNo != -1) {
                        roomAvailability[bookedRoomNo] = false;  // Mark room as unavailable if booked
                    }
                }
            }
            br.close();
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading customer details: " + e.getMessage());
        }
    }

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
        System.out.println("===================================================================");
        System.out.printf("%-15s%-25s%-15s%-15s\n", "ID", "Name", "Mobile No", "Aadhar No");
        System.out.println("===================================================================");

        System.out.printf("%-15d%-25s%-15s%-15s\n", cd.getId(), cd.getName(), cd.getMobileNo(), cd.getAadharNo());
        System.out.println("===================================================================");
        System.out.println("Signup Successful !!!");

        System.out.println("Would you like to check availability and book a room?");
        System.out.println("1) Yes");
        System.out.println("2) Exit");
        int option = sc.nextInt();
        sc.nextLine();

        if (option == 1) {
            Login login = new Login(customer);
            login.bookRoom(cd, sc);
        }
    }

    private void saveCustomerDetails() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CUSTOMER_DETAILS_FILE))) {
            for (CustomerDetails cd : customer.values()) {
                bw.write(cd.getId() + "," + cd.getName() + "," + cd.getMobileNo() + "," + cd.getAadharNo() + "," + cd.getBookedRoomNo() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving customer details to file.");
        }
    }

    private int generateUniqueId() {
        int id = 1000;
        try {
            BufferedReader br = new BufferedReader(new FileReader("customerIdCounter.txt"));
            String line = br.readLine();
            if (line != null) {
                id = Integer.parseInt(line) + 1;
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error reading ID counter file.");
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("customerIdCounter.txt"))) {
            bw.write(String.valueOf(id));
        } catch (IOException e) {
            System.out.println("Error saving ID counter.");
        }
        return id;
    }
}
