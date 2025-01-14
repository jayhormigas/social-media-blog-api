package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    /**
    * Handles user registration by validating input and interacting with the DAO layer.
    * @param account The account to register.
    * @return The newly created account with an account_id, or null if registration fails.
    */
    public Account registerUser(Account account) {
        // Validate username: it should not be null or blank
        if (account.getUsername() == null || account.getUsername().isBlank()) {
            return null;
        }

        // Validate password: it should be at least 4 characters long
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            return null;
        }

        // Check if the username already exists in the database
        if (accountDAO.getAccountByUsername(account.getUsername()) != null) {
            return null; // Registration fails if username already exists
        }

        // If all validations pass, create the account in the database
        return accountDAO.createAccount(account);
    }

    /**
    * Handles user login by validating the provided username and password
    * and checking against the database for a matching account.
    * 
    * @param account The account object containing the username and password to validate.
    * @return The existing account with its account_id if login is successful, or null if login fails.
    */
    public Account loginUser(Account account) {
        // Validate input: username and password must not be null
        if (account.getUsername() == null || account.getPassword() == null) {
            return null; // Return null if inputs are invalid
        }
    
        // Check if the username and password match an existing account in the database
        Account existingAccount = accountDAO.validateCredentials(account.getUsername(), account.getPassword());
    
        // If credentials are valid, return the matching account; otherwise, return null
        return existingAccount;
    }

}

