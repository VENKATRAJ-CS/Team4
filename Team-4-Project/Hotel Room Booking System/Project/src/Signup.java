import java.io.*;
import java.util.*;

public class Signup {
    private static HashMap<String, CustomerDetails> customer = new HashMap<>();
    private static boolean[] roomAvailability = new boolean[16]; 
    private static final String CUSTOMER_DETAILS_FILE = "customerDetails.txt";
    private static final String CUSTOMER_ID_COUNTER_FILE = "customerIdCounter.txt";

    static {
        for (int i = 1; i <= 15; i++) {
            roomAvailability[i] = true; 
        }
    }

    public Signup() {
        loadCustomerDetails();  
    }

    private void loadCustomerDetails() {
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

                
                if (details.length == 4) {
                    int id = Integer.parseInt(details[0].trim());  
                    String name = details[1].trim();
                    String mobileNo = details[2].trim();
                    long aadharNo = Long.parseLong(details[3].trim());

                    
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

    private void saveCustomerDetails() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CUSTOMER_DETAILS_FILE))) {
            for (CustomerDetails cd : customer.values()) {
                bw.write(cd.getId() + "," + cd.getName() + "," + cd.getMobileNo() + "," + cd.getAadharNo() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving customer details to file.");
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

        System.out.println("Available rooms:");
        for (int room : availableRooms) {
            System.out.print(room + " ");
        }

        int roomNo = -1;
        while (true) {
            System.out.print("\nSelect Your Room No: ");
            roomNo = sc.nextInt();
            sc.nextLine();

            if (isRoomAvailable(roomNo) && isValidRoomForType(roomNo, roomType)) {
                roomAvailability[roomNo] = false;  
                cd.setBookedRoomNo(roomNo);  
                saveCustomerDetails(); 
                System.out.println("Room " + roomNo + " booked successfully.");
                break;  
            } else {
                System.out.println("Invalid room number or room already booked. Please select a valid room.");
            }
        }

        System.out.print("Enter the number of nights you want to stay: ");
        int nights = sc.nextInt();
        sc.nextLine();

        Payment payment = new Payment(roomType, nights);
        payment.processPayment(sc);
    }

    private int[] getAvailableRooms(int roomType) {
        
        int start = (roomType == 1) ? 1 : 8;  
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
        
        if (roomType == 1) {
            return roomNo >= 1 && roomNo <= 7;  
        } else if (roomType == 2) {
            return roomNo >= 8 && roomNo <= 15;  
        }
        return false;
    }

    public void login(Scanner sc) {
        System.out.print("Please enter your Customer ID: ");
        String custId = sc.nextLine();
        CustomerDetails cd = customer.get(custId);

        if (cd != null) {
            System.out.println("Customer Details:");
            System.out.println("==========================");
            System.out.println(cd);

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
