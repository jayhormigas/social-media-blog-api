# Social Media Blog API

## Project Description  
The **Social Media Blog API** is a backend application designed to manage **user accounts and messages** in a **micro-blogging platform**. It provides **RESTful API endpoints** to handle **user registrations, authentication, message creation, retrieval, updating, and deletion**. This project follows a **three-layer architecture** with **Java, Javalin, and Spring**, ensuring scalability and maintainability.  

## Technologies Used  
- **Java** - Version 17  
- **Javalin** - Lightweight web framework     
- **JUnit & Mockito** - Unit testing framework  
- **Git** - Version control  

## Features  

### User Management  
- Register new users with unique usernames and secure passwords  
- User authentication with password validation  

### Message Handling  
- Users can create, edit, and delete messages  
- Retrieve all messages or filter by a specific user  
- Enforce text length limits (≤ 255 characters)  

### Data Persistence  
- **H2 Database** stores user accounts and messages  
- **DAO layer** ensures efficient SQL operations  

### Security & Error Handling  
- Input validation for usernames, passwords, and message lengths  
- Error handling for duplicate users, invalid login attempts, and missing data  

---

## Getting Started  

### Clone the Repository  
```
sh
git clone https://github.com/jayhormigas/social-media-blog-api.git  
cd social-media-blog-api
```

### Setup & Run the Application
For Windows (CMD/PowerShell)
```
mvn clean install  
java -jar target/social-media-api.jar  
```

For macOS/Linux (Terminal)
```
./mvnw clean install  
java -jar target/social-media-api.jar  
```

### Run the API Locally
Once the application starts, you can use Postman or cURL to test the API:

Register a User
```
curl -X POST http://localhost:8080/register -H "Content-Type: application/json" -d '{"username": "testUser", "password": "password123"}'
```

Login a User
```
curl -X POST http://localhost:8080/login -H "Content-Type: application/json" -d '{"username": "testUser", "password": "password123"}'
```

Create a Message
```
curl -X POST http://localhost:8080/messages -H "Content-Type: application/json" -d '{"posted_by": 1, "message_text": "Hello, world!", "time_posted_epoch": 1710000000}'
```

Retrieve All Messages
```
curl -X GET http://localhost:8080/messages
```

## Usage
This API is designed for full-stack development, where a frontend (e.g., React, Angular) can consume its endpoints. It allows user authentication, message posting, and data retrieval, making it a solid foundation for a social media or messaging application.
