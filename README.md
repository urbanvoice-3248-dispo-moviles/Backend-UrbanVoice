# PeaceApp Backend

This repository contains the backend implementation for the PeaceApp application. The backend is built using 
Java and Spring Boot, providing RESTful services for managing alerts, user profiles, reports, and location data.

## Features
- **User Management**: Create and manage user profiles securely.
- **Alert System**: Allow users to create and respond to alerts.
- **Location Tracking**: Record and manage user locations.
- **Incident Reporting**: Manage and view reported incidents.

## Project Structure
The project follows a standard Maven-based Spring Boot structure with a clear separation of concerns:

- **Application Entry Point**:
  - `PeaceappApplication.java`: Main class that bootstraps the Spring Boot application.

- **Controllers** (`src/main/java/com/upc/pre/peaceapp/controllers`):
  - `AlertController.java`: Handles HTTP requests related to alerts.
  - `LocationController.java`: Manages location-related requests.
  - `ReportController.java`: Handles all operations related to reports.
  - `UserController.java`: Manages user-related requests.

- **Models** (`src/main/java/com/upc/pre/peaceapp/models`):
  - Classes representing the entities of the application, such as `UserProfile`, `Alert`, `Location`, and `Report`.

- **Repositories** (`src/main/java/com/upc/pre/peaceapp/repositories`):
  - Interfaces extending `JpaRepository` for CRUD operations on the entities.

- **Resources** (`src/main/resources`):
  - `application.properties`: Defines configuration settings such as database connection, server ports, etc.

## Getting Started

### Prerequisites
- **JDK 11 or 17** (preferably)
- **Maven 3.6+**
- **IntelliJ IDEA** or any Java IDE

### Installation
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/username/PeaceAppBackend.git
   cd PeaceAppBackend
   ```
2. **Import the Project in IntelliJ**:
   - Open IntelliJ IDEA, click on **Open** and select the project's `pom.xml` file to load it as a Maven project.
3. **Build and Run**:
   - Let IntelliJ sync Maven dependencies.
   - Run the `PeaceappApplication` class to start the backend server.

### Usage
- The backend provides RESTful endpoints that can be accessed using tools like Postman or via the frontend application.
- The base URL for the API is `http://localhost:8080`.
