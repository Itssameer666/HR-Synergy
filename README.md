# HR Synergy – HR Management System

## 🌐 Live Demo

**Live Application:**
https://hr-synergy.onrender.com/

---

## 🛠️ Setup & Installation

### Prerequisites

Make sure the following are installed:

* Java JDK 17 or above
* Maven
* MySQL
* Git
* Eclipse / IntelliJ IDEA / Spring Tool Suite
* Web Browser

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/hr-synergy.git
cd hr-synergy
```

### 2. Configure MySQL

Create the database:

```sql
CREATE DATABASE hr_synergy;
```

Open:

```text
src/main/resources/application.properties
```

Configure your database:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hr_synergy
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

### 3. Install Dependencies

Using Maven:

```bash
mvn clean install
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

Or run:

```text
HrSynergyApplication.java
```

from Eclipse / IntelliJ IDEA.

### 5. Open the Application

```text
http://localhost:8080
```

---

## 🔐 Login Details

### Live Demo Login

Use the following demo account **only if it is configured in the deployed application**:

```text
Username: YOUR_DEMO_USERNAME
Password: YOUR_DEMO_PASSWORD
```

**Live URL:**

```text
https://hr-synergy.onrender.com/
```

> ⚠️ Do not publish real production passwords, database passwords, API keys, or other secrets in this README or GitHub repository.

### Local Login

For local development, create or configure a test/demo user in your application's database according to your authentication implementation.

Example:

```text
Username: admin
Password: your_demo_password
Role: ADMIN
```

Replace these values with the credentials actually configured in your project.

---

## 🔑 Authentication Flow

```text
User
  ↓
Login Page
  ↓
Username + Password
  ↓
Authentication
  ↓
Credential Validation
  ↓
Dashboard
  ↓
HR Management Modules
```

---

## 📋 Main Modules

* Employee Management
* Department Management
* Attendance Management
* Leave Management
* Dashboard
* User Authentication
* Centralized Employee Data
* CRUD Operations
* REST APIs
* MySQL Database

---

## 🔄 Backend Architecture

```text
Frontend
   ↓
Controller
   ↓
Service
   ↓
Repository
   ↓
JPA / Hibernate
   ↓
MySQL
```

---

## 📁 Project Structure

```text
HR-Synergy/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── com/hrsnergy/
│       │       ├── controller/
│       │       ├── service/
│       │       ├── repository/
│       │       ├── entity/
│       │       └── HrSynergyApplication.java
│       │
│       └── resources/
│           ├── templates/
│           ├── static/
│           │   ├── css/
│           │   ├── js/
│           │   └── images/
│           └── application.properties
│
├── pom.xml
└── README.md
```

---

## 🚀 Deployment

The application is deployed on **Render**.

```text
Production URL:
https://hr-synergy.onrender.com/
```

---

## 👨‍💻 Developer

**Er. Sameer**

B.Tech – Computer Science & Engineering

**Java Full Stack Developer**

### Technologies

```text
Java
Spring Boot
Spring MVC
Spring Data JPA
Hibernate
MySQL
HTML
CSS
JavaScript
Bootstrap
Git
GitHub
REST API
```

---

## 📌 Project Type

**Full Stack Java Web Application**

Frontend → HTML, CSS, JavaScript, Bootstrap
Backend → Java, Spring Boot
Database → MySQL
ORM → JPA / Hibernate
Deployment → Render
Version Control → Git & GitHub
