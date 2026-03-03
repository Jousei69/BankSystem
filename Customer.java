import java.util.ArrayList;
import java.util.List;

public class Customer {
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

    public Account findAccount(String accountNumber) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(accountNumber)) {
                return acc;
            }
        }
        return null;
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