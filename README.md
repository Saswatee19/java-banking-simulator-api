# High-Throughput Banking Simulator REST API

A production-ready, secure, and resilient Spring Boot RESTful API designed to simulate core banking operations, transactional integrity, concurrency management, and dynamic role-based authorization.

## Technical Transformation & Engineering Highlights

### 1. Evolution from CLI to Enterprise REST API

* **Architectural Refactoring:** Refactored a monolithic Command-Line Interface (CLI) application into a decoupled, modern multi-tiered REST API architecture (Controller $\rightarrow$ Service $\rightarrow$ Repository $\rightarrow$ Database).

* **Stateless Communication:** Replaced interactive terminal inputs with standardized JSON requests and clear HTTP responses, enabling scalability and API-first integrations.

### 2. Best Practices in Dependency Injection (DI)

* **Constructor Injection:** Applied clean Spring Dependency Injection strictly via constructors rather than field injection (`@Autowired`). This guarantees immutability (`final` fields), simplifies unit testing, and eliminates hidden circular dependencies.

### 3. Build & Dependency Management

* **Apache Maven:** Structured build pipeline and dependency management via `pom.xml`, streamlining lifecycle management for Spring Boot starters, Spring Security, and JUnit/Mockito test runtimes.

### 4. Database Persistence: In-Memory HashMap to H2 & Hibernate

* **Legacy State:** Upgraded initial volatile in-memory Java `HashMap` storage to a fully database-driven persistence engine.

* **H2 & Hibernate Integration:** Integrated Spring Data JPA backed by an H2 database and Hibernate ORM.

* **Domain Model Inheritance:** Modeled core domain abstractions (`BankAccount` base class extended by `SavingsAccount` and `CheckingAccount`) using `InheritanceType.JOINED` strategy to maintain normalization in dynamic relational schema design.

### 5. Concurrency Control & Race Condition Mitigation

* **Optimistic Locking:** Implemented JPA's `@Version` mechanism on bank account entities. Under high-throughput concurrent deposit/withdrawal conditions, conflicting updates trigger an `ObjectOptimisticLockingFailureException`, preventing lost updates and silent data corruption without locking database rows indefinitely.

### 6. Robust Security & Authentication Architecture

* **Dynamic Security & RBAC:** Replaced hardcoded credentials with dynamic database-driven authentication using a custom `UserDetailsService` and `UserRepository`.

* **Credential Hashing:** Passwords are secure at rest using `BCryptPasswordEncoder`.

* **Granular Route Protection:**

  * Endpoint access is strictly controlled: `/api/auth/register` and `/h2-console/**` are public.

  * Account creation endpoints (`POST /api/accounts/savings`, `/checking`) require **`ADMIN`** authority.

  * Financial transactions (`deposit`, `withdraw`, balance lookups) are accessible to **`USER`** and **`ADMIN`** roles.

### 7. Centralized Resilience & Automated Testing

* **Global Exception Handling:** Constructed a centralized `@RestControllerAdvice` (`GlobalExceptionHandler`) to catch validation errors, domain exceptions, and concurrency conflicts, mapping them cleanly to HTTP status codes (`400 Bad Request`, `404 Not Found`, `409 Conflict`).

* **Unit Testing Suite:** Unit-tested core business service components using **JUnit 5** and **Mockito**, verifying mock interactions, balance adjustments, and exception triggers in isolation.

## Tech Stack

| Category | Technology | 
 | ----- | ----- | 
| **Language** | Java 21 | 
| **Framework** | Spring Boot 4.0.1 (Spring Web, Spring Data JPA, Spring Security) | 
| **ORM / Persistence** | Hibernate, H2 Database | 
| **Build Tool** | Apache Maven | 
| **Security** | BCrypt, HTTP Basic Authentication | 
| **Testing** | JUnit 5, Mockito | 

## API Endpoints Overview

### Authentication & User Management

| Method | Endpoint | Access | Description | 
 | ----- | ----- | ----- | ----- | 
| `POST` | `/api/auth/register` | Public | Register new user with roles (`USER` / `ADMIN`) | 

### Account Operations

| Method | Endpoint | Access | Description | 
 | ----- | ----- | ----- | ----- | 
| `POST` | `/api/accounts/savings` | `ADMIN` | Create a new Savings Account | 
| `POST` | `/api/accounts/checking` | `ADMIN` | Create a new Checking Account | 
| `GET` | `/api/accounts` | `USER` / `ADMIN` | Retrieve all active bank accounts | 
| `GET` | `/api/accounts/{accountNumber}` | `USER` / `ADMIN` | Retrieve account by account number | 

### Transaction Operations

| Method | Endpoint | Access | Description | 
 | ----- | ----- | ----- | ----- | 
| `POST` | `/api/accounts/{accountNumber}/deposit` | `USER` / `ADMIN` | Deposit specified amount into account | 
| `POST` | `/api/accounts/{accountNumber}/withdraw` | `USER` / `ADMIN` | Withdraw specified amount from account | 

## Local Setup & Installation

### Prerequisites

* JDK 17 or higher

* Maven 3.8+

### Steps

1. **Clone the Repository:**

   ```
   git clone https://github.com/Saswatee19/java-banking-simulator-api.git
   cd java-banking-simulator-api
   
   
   ```

2. **Build and Package:**

   ```
   mvn clean package
   
   
   ```

3. **Run the Application:**

   ```
   mvn spring-boot-run
   
   
   ```

4. **Access Endpoints & H2 Console:**

   * Server URL: `http://localhost:8080`

   * H2 Console: `http://localhost:8080/h2-console` *(JDBC URL: `jdbc:h2:mem:testdb`)*
