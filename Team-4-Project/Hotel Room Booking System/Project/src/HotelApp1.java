import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class CustomerDetails1 {
	private String name;
	private String mobileNumber;
	private String aadharNumber;
	private int roomNumber;

	// Constructor
	public CustomerDetails1(String name, String mobileNumber, String aadharNumber, int roomNumber) {
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.aadharNumber = aadharNumber;
		this.roomNumber = roomNumber;
	}

	// Getters
	public String getName() {
		return name;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	@Override
	public String toString() {
		return "Customer Details:\n" + "Name: " + name + "\n" + "Mobile Number: " + mobileNumber + "\n"
				+ "Aadhar Number: " + aadharNumber + "\n" + "Room Number: " + roomNumber;
	}
}

public class HotelApp1 {
	private static HashMap<Integer, CustomerDetails1> roomToCustomerMap = new HashMap<>();
	private static HashMap<String, CustomerDetails1> nameToCustomerMap = new HashMap<>();

	public static void main(String[] args) {
		// Initialize with some customer details
		addCustomer(new CustomerDetails1("Alice", "1234567890", "A123456789", 101));
		addCustomer(new CustomerDetails1("Bob", "2345678901", "B234567890", 102));
		addCustomer(new CustomerDetails1("Charlie", "3456789012", "C345678901", 103));

		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println("Enter search option:");
			System.out.println("1. Search by Room Number");
			System.out.println("2. Search by Customer Name");
			System.out.println("3. Exit");

			int option = sc.nextInt();
			sc.nextLine(); // Clear buffer

			if (option == 1) {
				System.out.print("Enter Room Number: ");
				int roomNumber = sc.nextInt();
				sc.nextLine(); // Clear buffer
				searchByRoomNumber(roomNumber);
			} else if (option == 2) {
				System.out.print("Enter Customer Name: ");
				String name = sc.nextLine();
				searchByName(name);
			} else if (option == 3) {
				System.out.println("Exiting. Thank you!");
				break;
			} else {
				System.out.println("Invalid option. Please try again.");
			}
		}
		sc.close();
	}

	// Method to add a customer to both maps
	private static void addCustomer(CustomerDetails1 customer) {
		roomToCustomerMap.put(customer.getRoomNumber(), customer);
		nameToCustomerMap.put(customer.getName().toLowerCase(), customer); // store name in lowercase for
																			// case-insensitive search
	}

	// Search by room number
	private static void searchByRoomNumber(int roomNumber) {
		CustomerDetails1 customer = roomToCustomerMap.get(roomNumber);
		if (customer != null) {
			System.out.println(customer);
		} else {
			System.out.println("No customer found for Room Number " + roomNumber);
		}
	}

	// Search by customer name
	private static void searchByName(String name) {
		CustomerDetails1 customer = nameToCustomerMap.get(name.toLowerCase()); // Convert name to lowercase for
																				// case-insensitive search
		if (customer != null) {
			System.out.println(customer);
		} else {
			System.out.println("No customer found with the name " + name);
		}
	}
}
