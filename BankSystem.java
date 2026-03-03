import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankSystem {

    static Scanner scanner = new Scanner(System.in);
    static List<Customer> customers = new ArrayList<>();

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n=== BANK SYSTEM ===");
            System.out.println("1. Admin");
            System.out.println("2. Customer");
            System.out.println("3. Quit");
            System.out.print("Choose: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> adminMenu();
                case 2 -> customerMenu();
                case 3 -> {
                    System.out.println("Program terminated.");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // ================= ADMIN =================

    static void adminMenu() {
        while (true) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. Add Customer");
            System.out.println("2. Search Customer");
            System.out.println("3. Delete Customer");
            System.out.println("4. Display Customers");
            System.out.println("5. Create Account");
            System.out.println("6. Back");
            System.out.print("Choose: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addCustomer();
                case 2 -> searchCustomer();
                case 3 -> deleteCustomer();
                case 4 -> displayCustomers();
                case 5 -> createAccount();
                case 6 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void addCustomer() {
        System.out.print("First Name: ");
        String first = scanner.nextLine();
        System.out.print("Last Name: ");
        String last = scanner.nextLine();

        customers.add(new Customer(first, last));
        System.out.println("Customer added successfully.");
    }

    static Customer findCustomer(String firstName) {
        for (Customer c : customers) {
            if (c.getFirstName().equalsIgnoreCase(firstName)) {
                return c;
            }
        }
        return null;
    }

    static void searchCustomer() {
        System.out.print("Enter First Name: ");
        String name = scanner.nextLine();
        Customer c = findCustomer(name);

        if (c != null) {
            c.displayDetails();
        } else {
            System.out.println("Customer not found.");
        }
    }

    static void deleteCustomer() {
        System.out.print("Enter First Name: ");
        String name = scanner.nextLine();
        Customer c = findCustomer(name);

        if (c != null) {
            customers.remove(c);
            System.out.println("Customer deleted.");
        } else {
            System.out.println("Customer not found.");
        }
    }

    static void displayCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers available.");
            return;
        }

        for (Customer c : customers) {
            c.displayDetails();
            System.out.println("------------------");
        }
    }

    static void createAccount() {
        System.out.print("Enter First Name: ");
        String name = scanner.nextLine();
        Customer c = findCustomer(name);

        if (c != null) {
            System.out.print("Enter Account Number: ");
            String accNo = scanner.nextLine();
            c.addAccount(new Account(accNo));
            System.out.println("Account created.");
        } else {
            System.out.println("Customer not found.");
        }
    }

    // ================= CUSTOMER =================

    static void customerMenu() {
        System.out.print("Enter First Name: ");
        String name = scanner.nextLine();
        Customer c = findCustomer(name);

        if (c == null) {
            System.out.println("Customer not found.");
            return;
        }

        while (true) {
            System.out.println("\n--- CUSTOMER MENU ---");
            System.out.println("1. Balance Inquiry");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Back");
            System.out.print("Choose: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> balanceInquiry(c);
                case 2 -> deposit(c);
                case 3 -> withdraw(c);
                case 4 -> transfer(c);
                case 5 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void balanceInquiry(Customer c) {
        System.out.print("Enter Account Number: ");
        String accNo = scanner.nextLine();
        Account acc = c.findAccount(accNo);

        if (acc != null) {
            System.out.println("Balance: " + acc.getBalance());
        } else {
            System.out.println("Account not found.");
        }
    }

    static void deposit(Customer c) {
        System.out.print("Enter Account Number: ");
        String accNo = scanner.nextLine();
        Account acc = c.findAccount(accNo);

        if (acc != null) {
            System.out.print("Amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            acc.deposit(amount);
            System.out.println("Deposit successful.");
        } else {
            System.out.println("Account not found.");
        }
    }

    static void withdraw(Customer c) {
        System.out.print("Enter Account Number: ");
        String accNo = scanner.nextLine();
        Account acc = c.findAccount(accNo);

        if (acc != null) {
            System.out.print("Amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();

            if (acc.withdraw(amount)) {
                System.out.println("Withdraw successful.");
            } else {
                System.out.println("Insufficient balance.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    static void transfer(Customer c) {
        System.out.print("From Account Number: ");
        String fromAccNo = scanner.nextLine();
        Account from = c.findAccount(fromAccNo);

        if (from == null) {
            System.out.println("Source account not found.");
            return;
        }

        System.out.print("To Account Number: ");
        String toAccNo = scanner.nextLine();

        Account to = null;
        for (Customer cust : customers) {
            Account acc = cust.findAccount(toAccNo);
            if (acc != null) {
                to = acc;
                break;
            }
        }

        if (to == null) {
            System.out.println("Destination account not found.");
            return;
        }

        System.out.print("Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        if (from.withdraw(amount)) {
            to.deposit(amount);
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Insufficient balance.");
        }
    }
}