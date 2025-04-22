# 💸  Remitly Internship Project — SWIFT Code Service
## 📖 Description:

This is a `Spring Boot`  application that provides a `RESTful API` to manage  and query SWIFT codes for banks globally(primarily in the EU and  South America)
This app includes feautres for `adding`, `deleting` and `retrieving` bank information by `SWIFT` code or `country ISO2` codes.
Built with   `Java 21` ,`Spring Boot 3.4.4`  and `Postgres` for database. it includes `unit` and `integration` tests.

## ⚙️ Tech Stack:
- Java 21
- Spring Boot 3.4.4
- Maven
- PostgreSQL( for production)/ H2 in-memory database(test)
- JUnit & Mockito(for unit + integration tests)
- Swagger UI (optional -clear UI, helpful for exploring the endpoints.).

  ---

## 🚀 Setup & Run Instructions
### 1. Clone the repository
```bash
git clone https://github.com/Alcom01/RemitlyInternshipProject.git
cd RemitlyInternShipProject
```
### :elephant: 2. Configure Database (PostgreSQL)
Update  the `application.properties` file with your local PostgreSQL configuration.
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/your_db
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql=true
springdoc.override-with-generic-response=false #Optional: if you are using Swagger UI.
```
### :hammer_and_wrench: 3. Build the Project By Maven
```bash
mvn clean install
```
### :leg: 4. Run the Application
```bash
mvn spring-boot:run
```
The API will be available at: http://localhost:8080

## :microscope:  Running Tests
```bash
mvn test
```
Tests use an in-memory H2 database,  ensuring actual PostgreSQL database remains untouched and secure.

## :incoming_envelope: API Endpoints
You can explore the endpoints via Swagger UI at:
http://localhost:8080/swagger-ui.html

## 🐳 Dockerization
- In progress...


## 📌 Notes
- All exceptions are handled globally with meaningful messages.

- Integration tests cover key API flows and edge cases.

- Application follows  an architecture pattern: `Controller → Service → Repository`.


👨‍💻 Author
Alcom01 — Remitly Internship Application, 2025

