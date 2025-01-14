package Controller;


import Service.AccountService;
import Model.Account;
import Service.MessageService;
import Model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
    * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
    * suite must receive a Javalin object from this method.
    * @return a Javalin app object which defines the behavior of the Javalin controller.
    */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerUserHandler);
        app.post("/login", this::loginUserHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIDHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIDHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIDHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesFromUserHandler);

        return app;
    }

    /**
    * Handles user registration by validating input and interacting with the service layer.
    *
    * @param ctx The Javalin context containing the HTTP request and response.
    * @throws JsonProcessingException if the request body cannot be parsed into an Account object.
    */
    private void registerUserHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account newAccount = accountService.registerUser(account);
    
        if (newAccount != null) {
            ctx.json(mapper.writeValueAsString(newAccount));
        } else {
            ctx.status(400);
        }
    }

    /**
    * Handles user login by verifying credentials with the service layer.
    *
    * @param ctx The Javalin context containing the HTTP request and response.
    * @throws JsonProcessingException if the request body cannot be parsed into an Account object.
    */
    private void loginUserHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loginAccount = accountService.loginUser(account);
    
        if (loginAccount != null) {
            ctx.json(mapper.writeValueAsString(loginAccount));
        } else {
            ctx.status(401);
        }
    }

    /**
    * Handles the creation of a new message by validating input and interacting with the service layer.
    *
    * @param ctx The Javalin context containing the HTTP request and response.
    * @throws JsonProcessingException if the request body cannot be parsed into a Message object.
    */
    private void createMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message newMessage = messageService.createMessage(message);
    
        if (newMessage != null) {
            ctx.json(mapper.writeValueAsString(newMessage));
        } else {
            ctx.status(400);
        }
    }

    /**
    * Retrieves all messages from the database and sends them as a JSON response.
    *
    * @param ctx The Javalin context containing the HTTP request and response.
    */
    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    /**
    * Retrieves a specific message by its ID and sends it as a JSON response.
    *
    * @param ctx The Javalin context containing the HTTP request and response.
    */
    private void getMessageByIDHandler(Context ctx) {
        // Parse the message_id from the path parameter
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
    
        // Call the service to fetch the message
        Message message = messageService.getMessageByID(message_id);
    
        if (message != null) {
            ctx.json(message); // Send the message as JSON if found
        } else {
            ctx.json(""); // Send an empty response if no message is found
        }
    }

    /**
    * Deletes a specific message by its ID and sends the deleted message as a JSON response.
    *
    * @param ctx The Javalin context containing the HTTP request and response.
    */
    private void deleteMessageByIDHandler(Context ctx) {
        // Parse the message_id from the path parameter
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
    
        // Call the service to fetch the message
        Message deletedMessage = messageService.deleteMessageByID(message_id);
    
        if (deletedMessage != null) {
            ctx.json(deletedMessage); // Send the message as JSON if found
        } else {
            ctx.json("");
        }
    }

    /**
    * Updates the text of a specific message by its ID and sends the updated message as a JSON response.
    *
    * @param ctx The Javalin context containing the HTTP request and response.
    * @throws JsonProcessingException if the request body cannot be parsed into a Message object.
    */
    private void updateMessageByIDHandler(Context ctx) throws JsonProcessingException {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        String new_message_text = message.getMessage_text();
        Message updatedMessage = messageService.updateMessageByID(message_id, new_message_text);

        if (updatedMessage != null) {
            ctx.json(updatedMessage); // Send the message as JSON if found
        } else {
            ctx.status(400);
        }
    }

    /**
    * Retrieves all messages written by a specific user and sends them as a JSON response.
    *
    * @param ctx The Javalin context containing the HTTP request and response.
    */
    private void getAllMessagesFromUserHandler(Context ctx) {
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesFromUser(account_id);
        ctx.json(messages);
    }

}