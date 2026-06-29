# 🚀 DBInsight AI

DBInsight AI is a backend-focused database management platform built using Spring Boot that allows users to securely connect to their own PostgreSQL databases, execute SQL queries, analyze queries with AI, and manage multiple database connections through a clean web interface.

---

## 📌 Features

### 🔐 Authentication
- JWT-based Authentication
- User Registration & Login
- Spring Security
- Protected REST APIs
- Role-Based Access Control

### 🗄 Database Connection Management
- Add PostgreSQL Connections
- Validate Connection Before Saving
- Delete Saved Connections
- View User-Specific Connections
- Multi-user Support

### 💻 SQL Workspace
- Open a Database Connection
- Execute SQL Queries
- View Query Results in Table Format
- Display Affected Rows for INSERT/UPDATE/DELETE
- SQL Error Handling

### 🤖 AI Query Analysis
- Analyze SQL Queries
- Query Optimization Suggestions using Gemini API
- AI-powered Query Explanation

### 📊 Dashboard
- Total Connections
- Query Statistics
- Recent Activity

---

# 🏗 Architecture

```
                Frontend (HTML/CSS/JavaScript)
                           │
                           ▼
                   Spring Boot REST API
                           │
        ┌──────────────────┴──────────────────┐
        ▼                                     ▼
 JWT Authentication                  PostgreSQL Database
        │                                     │
        ▼                                     ▼
 Spring Security                  Users / Connections / History
        │
        ▼
 JDBC Connection
        │
        ▼
 User's PostgreSQL Database
```

---

# ⚙ Tech Stack

### Backend
- Java 21+
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- JWT Authentication
- PostgreSQL
- JDBC
- Flyway Migration
- Maven

### Frontend
- HTML5
- CSS3
- JavaScript

### AI
- Google Gemini API

---

# 📂 Project Structure

```
dbinsight-ai
│
├── controller
├── service
├── repository
├── entity
├── dto
├── security
├── config
├── util
├── analysis
├── ai
├── resources
│    ├── db
│    ├── static
│    └── application.yml
│
└── frontend
```

---

# 🔑 Authentication Flow

```
Register
     │
     ▼
Login
     │
     ▼
JWT Token Generated
     │
     ▼
Authorization Header
     │
     ▼
Protected APIs
```

---

# 🔄 Database Connection Flow

```
User
    │
    ▼
Enter Database Credentials
    │
    ▼
Validate JDBC Connection
    │
 ┌──┴────────────┐
 │               │
Success       Failure
 │               │
 ▼               ▼
Save         Show Error
 │
 ▼
Open Connection
 │
 ▼
Execute SQL
```

---

# 📡 API Endpoints

## Authentication

| Method | Endpoint |
|----------|-------------------------|
| POST | /api/auth/register |
| POST | /api/auth/login |

---

## Database Connections

| Method | Endpoint |
|----------|-------------------------|
| POST | /api/connections |
| GET | /api/connections |
| GET | /api/connections/{id} |
| DELETE | /api/connections/{id} |
| POST | /api/connections/execute |

---

## AI Analysis

| Method | Endpoint |
|----------|-------------------------|
| POST | /api/analysis |
| POST | /api/gemini |

---

# 📸 Screenshots

## Login

> Add Screenshot Here

---

## Dashboard

> Add Screenshot Here

---

## Add Connection

> Add Screenshot Here

---

## SQL Workspace

> Add Screenshot Here

---

## Query Results

> Add Screenshot Here

---

# 🚀 Running Locally

## Clone Repository

```bash
git clone https://github.com/vaibhavimadekar/DBInsight-AI.git
```

## Navigate

```bash
cd dbinsight-ai
```

## Configure PostgreSQL

Create a PostgreSQL database:

```
dbinsight_ai
```

Update `application.yml` or environment variables.

---

## Run

```bash
./mvnw spring-boot:run
```

or

```bash
mvn spring-boot:run
```

---

Open:

```
http://localhost:8080
```

---

# 🔒 Security

- JWT Authentication
- BCrypt Password Encryption
- Protected REST Endpoints
- User-Specific Database Connections
- Authorization Checks

---

# 📈 Future Improvements

- Encrypt Saved Database Passwords
- MySQL Support
- PostgreSQL Performance Metrics
- Query Execution History Dashboard
- AI-based Query Optimization
- Database Health Monitoring
- Docker Support
- Kubernetes Deployment

---

# 🎯 Resume Highlights

- Built a full-stack database management platform using Spring Boot and PostgreSQL.
- Implemented JWT Authentication and Spring Security.
- Developed secure multi-user database connection management.
- Built a SQL execution engine using JDBC.
- Integrated Google Gemini API for AI-powered SQL analysis.
- Designed RESTful APIs following layered architecture.

---

# 👨‍💻 Author

**Vaibhavi Madekar**

GitHub:
https://github.com/vaibhavimadekar

---

# ⭐ If you found this project useful, consider giving it a star!
