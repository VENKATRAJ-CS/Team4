import java.util.Scanner;

public class HotelApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Signup signup = new Signup();
        
        signup.loadCustomerDetails();
        
        Login login = new Login(Signup.customer);

        while (true) {
            System.out.println("Welcome to the Easy Rest Hotel!");
            System.out.println("===============================");
            System.out.println("1) Sign Up");
            System.out.println("2) Login");
            System.out.println("3) Exit");
            System.out.print("Choose an option: ");
            int option = sc.nextInt();
            sc.nextLine(); 

            if (option == 1) {
                signup.signup(sc);  
            } else if (option == 2) {
                login.login(sc);
            } else {
                System.out.println("Exiting...");
                break;
            }
        }
        sc.close();
    }
}
