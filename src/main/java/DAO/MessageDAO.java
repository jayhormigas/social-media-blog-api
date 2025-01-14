package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.h2.command.Prepared;

public class MessageDAO {

    /**
    * Creates a new message in the database.
    * 
    * @param message The message object to be created.
    * @return The newly created message with its message_id if successful, or null if an error occurs.
    */
    public Message createMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int generated_message_id = resultSet.getInt(1); // Get the auto-generated key
                return new Message(
                    generated_message_id, 
                    message.getPosted_by(), 
                    message.getMessage_text(), 
                    message.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
    * Retrieves all messages from the database.
    * 
    * @return A list of all messages in the database, or an empty list if no messages are found.
    */
    public List<Message> getAllMessages(){ 
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Message;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Message message = new Message(
                    resultSet.getInt("message_id"), 
                    resultSet.getInt("posted_by"), 
                    resultSet.getString("message_text"), 
                    resultSet.getLong("time_posted_epoch")
                );
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    /**
    * Retrieves a specific message by its ID.
    * 
    * @param message_id The ID of the message to retrieve.
    * @return The message object if found, or null if no message is found with the given ID.
    */    
    public Message getMessageByID(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM Message WHERE message_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Message message = new Message(
                    resultSet.getInt("message_id"), 
                    resultSet.getInt("posted_by"), 
                    resultSet.getString("message_text"), 
                    resultSet.getLong("time_posted_epoch") 
                );
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
    * Deletes a message from the database by its ID.
    * 
    * @param message_id The ID of the message to delete.
    */    
    public void deleteMessageByID(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE FROM Message WHERE message_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    /**
    * Updates the text of a specific message by its ID.
    * 
    * @param message_id The ID of the message to update.
    * @param new_message_text The new text to update the message with.
    * @return The updated message object if successful, or null if the update fails.
    */
    public Message updateMessageByID(int message_id, String new_message_text){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, new_message_text);
            preparedStatement.setInt(2, message_id);
            preparedStatement.executeUpdate();

            return getMessageByID(message_id);

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    
        return null; // Return null in case of an error
    }

    /**
    * Retrieves all messages written by a specific user from the database.
    * 
    * @param account_id The ID of the user whose messages are to be retrieved.
    * @return A list of all messages posted by the user with the given account_id, or an empty list if no messages are found.
    */    
    public List<Message> getAllMessagesFromUser(int account_id) {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Message WHERE posted_by = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Message message = new Message(
                    resultSet.getInt("message_id"), 
                    resultSet.getInt("posted_by"), 
                    resultSet.getString("message_text"), 
                    resultSet.getLong("time_posted_epoch") 
                );
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

}
