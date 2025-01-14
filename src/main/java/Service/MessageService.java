package Service;

import DAO.MessageDAO;
import DAO.AccountDAO;
import Model.Message;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO();
        this.accountDAO = new AccountDAO();
    }

    /**
    * Handles the creation of a new message by validating input and interacting with the DAO layer.
    * 
    * @param message The message object to be created.
    * @return The newly created message with its message_id if creation is successful, or null if validation fails.
    */
    public Message createMessage(Message message) {
        // Validate the message text: it must not be null or exceed 255 characters
        if (message.getMessage_text() == null || message.getMessage_text().isBlank() || message.getMessage_text().length() > 255) {
            return null; // Return null if message text is invalid
        }

        // Validate that the posted_by field refers to an existing user
        if (accountDAO.getAccountByID(message.getPosted_by()) == null) {
            return null; // Return null if posted_by is invalid or user doesn't exist
        }

        // If all validations pass, call the DAO to persist the message
        return messageDAO.createMessage(message);
    }   

    /**
    * Retrieves all messages from the database.
    * 
    * @return A list of all messages.
    */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    /**
    * Retrieves a specific message by its ID.
    * 
    * @param message_id The ID of the message to retrieve.
    * @return The message object if found, or null if no message exists with the given ID.
    */
    public Message getMessageByID(int message_id) {
        return messageDAO.getMessageByID(message_id); // Delegate to DAO
    }

    /**
    * Deletes a message by its ID.
    * 
    * @param message_id The ID of the message to delete.
    * @return The deleted message object if it existed, or null if no message was found.
    */
    public Message deleteMessageByID(int message_id) {
        Message message = messageDAO.getMessageByID(message_id);

        if (message != null) {
            messageDAO.deleteMessageByID(message_id);
        }

        return message;
    }

    /**
    * Updates the text of a specific message by its ID.
    * 
    * @param message_id The ID of the message to update.
    * @param new_message_text The new text to update the message with.
    * @return The updated message object if successful, or null if the message does not exist or validation fails.
    */
    public Message updateMessageByID(int message_id, String new_message_text) {
        Message message = messageDAO.getMessageByID(message_id);

        if (new_message_text == null || new_message_text.isBlank() || new_message_text.length() > 255) {
            return null; // Return null if message text is invalid
        }
    
        if (message != null) {
            return messageDAO.updateMessageByID(message_id, new_message_text);
        }

        return null;
    }

    /**
    * Retrieves all messages written by a specific user.
    * 
    * @param account_id The ID of the user whose messages are to be retrieved.
    * @return A list of all messages written by the user with the given account_id.
    */
    public List<Message> getAllMessagesFromUser(int account_id) {
        return messageDAO.getAllMessagesFromUser(account_id);
    }
}
