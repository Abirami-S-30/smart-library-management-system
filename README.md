
📚 Smart Library Management System

A Java-based Library Automation Application designed to automate essential library operations efficiently and accurately.

The system uses Java, JDBC, and MySQL to provide centralized data management for books, issue and return operations, inventory tracking, and automated fine calculation.

---

📖 Project Overview

Manual library management can be time-consuming and prone to errors. The Smart Library Management System provides an automated solution for managing library operations.

The application helps manage book records, track book availability, process book issues and returns, and calculate overdue fines automatically.

---

✨ Features

📚 Book Management

- Add new books
- Update book details
- View book records
- Search books by ID, title, or author
- Delete book records

📖 Issue & Return Management

- Check book availability
- Issue books
- Update available quantity automatically
- Process book returns
- Restore book quantity
- Maintain accurate inventory records

💰 Automated Fine Calculation

- Automatically calculate overdue fines
- Calculate fines based on delayed days
- Reduce manual calculation errors

🔍 Book Sorting

Sort books by:

- Book ID
- Title
- Price
- Available quantity

📊 Dashboard & Statistics

The system provides information about:

- Total books
- Available books
- Issued books
- Out-of-stock books

✅ Data Validation

- Prevent invalid entries
- Prevent incomplete records
- Block duplicate book IDs
- Validate transactions

---

🛠️ Technology Stack

- Java – Core programming language and business logic
- JDBC – Database connectivity
- MySQL – Database management
- Eclipse IDE – Development environment
- MySQL Workbench – Database management and SQL execution
- Maven – Dependency and build management

---

🏗️ System Architecture

The project follows a Layered Architecture:

User Interface
      ↓
Service Layer
      ↓
DAO / Repository Layer
      ↓
JDBC Connection
      ↓
MySQL Database

This architecture provides:

- Maintainability
- Modularity
- Reliability
- Scalability
- Clear separation of concerns

---

🔄 Operational Flow

General Flow

User Input
    ↓
Validation
    ↓
Business Logic
    ↓
Database Operation
    ↓
Updated Result

Book Issue Flow

Check Availability
        ↓
Issue Book
        ↓
Update Quantity
        ↓
Create Issue Record
        ↓
Set Due Date

Book Return Flow

Identify Issue
        ↓
Calculate Fine
        ↓
Update Return Status
        ↓
Restore Quantity
        ↓
Save Transaction

---

📂 Project Structure

smart-library-management-system/
│
├── src/
│   ├── model/
│   ├── dao/
│   ├── service/
│   └── main/
│
├── database/
│   └── library.sql
│
├── pom.xml
└── README.md

---

🚀 How to Run the Project

Prerequisites

Make sure you have installed:

- Java JDK
- Eclipse IDE
- MySQL Server
- MySQL Workbench
- Maven

Steps

1. Clone the repository:

git clone https://github.com/Abirami-S-30/smart-library-management-system.git

2. Open the project in Eclipse IDE.

3. Create the required database using MySQL Workbench.

4. Update the database configuration in your JDBC connection file.

5. Add the required MySQL JDBC dependency.

6. Run the main Java application.

---

🧠 Learning Outcomes

Through this project, I practiced:

- Java programming
- Object-Oriented Programming
- JDBC connectivity
- MySQL database operations
- CRUD operations
- Layered Architecture
- DAO Design Pattern
- Data validation
- Database integration
- Maven dependency management
- Git and GitHub

---

🔮 Future Enhancements

- 🖥️ GUI using Java Swing or JavaFX
- 🔐 Secure Authentication and Role-Based Access Control
- 👨‍🎓 Student and Member Management
- 📧 Email and SMS Notifications
- 📄 PDF Reports and Analytics
- ☁️ Cloud Deployment
- 👥 Multi-user support

---

👩‍💻 Author

Abirami S

🔗 GitHub Profile:
https://github.com/Abirami-S-30

---

⭐ Support

If you like this project, please consider giving it a ⭐ on GitHub!
