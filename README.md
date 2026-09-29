# MoodBloom
MoodBloom is a full-stack mental wellness web application designed to give users a simple and supportive space to track their moods, journal their thoughts, and interact with a community.

This application was developed as a team capstone project for the Computer Science program at Franklin University. I served as the Team Lead for the MoodBloom capstone project.

## Features
- User registration, login, and logout
- Secure authentication with Spring Security
- Daily mood tracking with notes
- Mood history
- Create, edit, and delete mood entries
- Personal journal entries
- Create, edit, and delete journal entries
- Community posts and comments
- Anonymous posting and commenting
- User profile management
- Account deletion
- Mental wellness resources
- Daily wellness tips

## Technologies Used

### Backend
- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- Maven

### Frontend
- HTML
- CSS
- JavaScript
- Thymeleaf
- Bootstrap

### Database & Deployment
- MySQL
- Amazon RDS (AWS)
- Jenkins CI/CD
- Git & GitHub

## Security
MoodBloom uses Spring Security for authentication and access control. User passwords are securely processed rather than stored as plain text.

Database credentials and other sensitive configuration information should be supplied through secure configuration and are not intended to be included in the public repository.

## Running MoodBloom

### Requirements
- Java
- Maven
- MySQL
- Git

### Clone the Repository
```bash
git clone https://github.com/Allenl25/MoodBloom.git
cd MoodBloom
```

### Run the Application
On Windows:
```bash
mvnw.cmd spring-boot:run
```

Or with Maven installed:
```bash
mvn spring-boot:run
```

Once running, open:
```text
http://localhost:8080
```

> A properly configured MySQL database connection is required for database-backed features.

## Project Purpose
Mental wellness tools can often be spread across multiple applications. MoodBloom was designed to bring several useful features into one place by combining mood tracking, private journaling, wellness resources, and community interaction.

The project also served as an opportunity to apply concepts from the Computer Science program to a complete software development lifecycle, including planning, design, implementation, database integration, testing, version control, and deployment.

## Academic Project
MoodBloom was created collaboratively as a **Franklin University Computer Science capstone project**.
The repository contains contributions from multiple members of the original development team.
