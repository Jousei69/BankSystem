import java.util.*;

class Account {
    private String accountNumber;
    private double balance;

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0.0;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}

class Customer {
    private String firstName;
    private String lastName;
    private List<Account> accounts;

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accounts = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void displayDetails() {
        System.out.println("Name: " + firstName + " " + lastName);
        if (accounts.isEmpty()) {
            System.out.println("No accounts.");
        } else {
            for (Account acc : accounts) {
                System.out.println("Account #: " + acc.getAccountNumber() +
                        " | Balance: " + acc.getBalance());
            }
        }
    }
}

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
        scanner.nextLine();
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

    static Account selectAccount(Customer c) {
        if (c.getAccounts().isEmpty()) {
            System.out.println("No accounts available.");
            return null;
        }

        System.out.print("Enter Account Number: ");
        String accNo = scanner.nextLine();

        for (Account acc : c.getAccounts()) {
            if (acc.getAccountNumber().equals(accNo)) {
                return acc;
            }
        }
        System.out.println("Account not found.");
        return null;
    }

    static void balanceInquiry(Customer c) {
        Account acc = selectAccount(c);
        if (acc != null) {
            System.out.println("Balance: " + acc.getBalance());
        }
    }

    static void deposit(Customer c) {
        Account acc = selectAccount(c);
        if (acc != null) {
            System.out.print("Amount: ");
            double amount = scanner.nextDouble();
            acc.deposit(amount);
            System.out.println("Deposit successful.");
        }
    }

    static void withdraw(Customer c) {
        Account acc = selectAccount(c);
        if (acc != null) {
            System.out.print("Amount: ");
            double amount = scanner.nextDouble();
            if (acc.withdraw(amount)) {
                System.out.println("Withdraw successful.");
            } else {
                System.out.println("Insufficient balance.");
            }
        }
    }

    static void transfer(Customer c) {
        System.out.println("From Account:");
        Account from = selectAccount(c);
        if (from == null) return;

        System.out.print("To Account Number: ");
        String toAccNo = scanner.nextLine();

        Account to = null;
        for (Customer cust : customers) {
            for (Account acc : cust.getAccounts()) {
                if (acc.getAccountNumber().equals(toAccNo)) {
                    to = acc;
                    break;
                }
            }
        }

        if (to == null) {
            System.out.println("Destination account not found.");
            return;
        }

        System.out.print("Amount: ");
        double amount = scanner.nextDouble();

        if (from.withdraw(amount)) {
            to.deposit(amount);
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Insufficient balance.");
        }
    }
}