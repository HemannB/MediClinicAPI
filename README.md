# MediClinicAPI

MediClinicAPI is a simple medical clinic REST API built for learning Spring Boot, Spring Security, JWT authentication, JPA, PostgreSQL, DTOs, service layers, and basic domain rules.

The project models a small clinic flow where an administrator can authenticate, create doctors and patients, schedule appointments, cancel appointments, and mark appointments as completed.

## Stack

- Java 25
- Spring Boot 4.1.0
- Spring Web MVC
- Spring Data JPA
- Spring Security
- JWT with `java-jwt`
- PostgreSQL 16
- Docker Compose
- Lombok
- Maven

## Main Features

- JWT authentication
- Default admin user seed
- Doctor registration and listing
- Patient registration and listing
- Appointment scheduling
- Appointment filters by doctor or patient
- Appointment cancellation
- Appointment completion with medical notes
- Basic global error responses
- Role-based access control for admin endpoints

## Project Structure

```text
src/main/java/com/example/mediclinicapi
├── config          # Spring Security configuration
├── controller      # REST controllers
├── domain          # JPA entities and enums
├── dto             # Request and response DTOs
├── exception       # Global API error handling
├── repository      # Spring Data repositories
├── security        # JWT request filter
├── service         # Business logic
└── util            # Database seeding
```

## Requirements

- JDK 25
- Docker and Docker Compose
- Maven Wrapper included in the project

## Running Locally

Start only the PostgreSQL container:

```bash
docker compose up -d medclinic-db
```

Run the application from IntelliJ using the main class:

```text
com.example.mediclinicapi.MediClinicApiApplication
```

By default, local execution uses:

```text
API: http://localhost:8081
Database: localhost:5433
Database name: medclinic_db
Database user: postgres
Database password: root
```

The Docker API service still runs on port `8080` when using the full Compose setup.

## Configuration

Default local values are defined in `src/main/resources/application.properties`:

```properties
server.port=${SERVER_PORT:8081}
spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5433/medclinic_db}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME:postgres}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD:root}
```

The admin seed can also be customized:

```properties
api.seed.admin.email=admin@mediclinic.com
api.seed.admin.password=admin123
```

If these properties are not provided, the default admin credentials are:

```text
Email: admin@mediclinic.com
Password: admin123
```

## Authentication

Login returns a JWT token. Protected endpoints require:

```text
Authorization: Bearer YOUR_TOKEN
```

### Login

```http
POST /auth/login
```

```json
{
  "email": "admin@mediclinic.com",
  "password": "admin123"
}
```

Response:

```json
{
  "token": "jwt-token"
}
```

### Current User

```http
GET /auth/me
```

Response:

```json
{
  "id": 1,
  "email": "admin@mediclinic.com",
  "role": "ADMIN"
}
```

## API Endpoints

### Doctors

Create doctor:

```http
POST /doctors
```

```json
{
  "email": "doctor@mediclinic.com",
  "password": "doctor123"
}
```

List doctors:

```http
GET /doctors
```

Find doctor by id:

```http
GET /doctors/{id}
```

### Patients

Create patient:

```http
POST /patients
```

```json
{
  "firstName": "Joao",
  "lastName": "Silva",
  "cpf": "12345678901",
  "phone": "11999999999",
  "email": "joao@email.com",
  "password": "joao123"
}
```

List patients:

```http
GET /patients
```

Find patient by id:

```http
GET /patients/{id}
```

### Appointments

Create appointment:

```http
POST /appointments
```

```json
{
  "doctorId": 2,
  "patientId": 1,
  "dateTime": "2026-07-21T10:00:00"
}
```

List appointments:

```http
GET /appointments
```

Filter by doctor:

```http
GET /appointments?doctorId=2
```

Filter by patient:

```http
GET /appointments?patientId=1
```

Find appointment by id:

```http
GET /appointments/{id}
```

Cancel appointment:

```http
PATCH /appointments/{id}/cancel
```

Complete appointment:

```http
PATCH /appointments/{id}/complete
```

```json
{
  "medicalNotes": "Patient reported improvement after medication."
}
```

## Business Rules

- Users have one of three roles: `ADMIN`, `DOCTOR`, or `PATIENT`.
- The default seeded user is an `ADMIN`.
- Doctors are represented as users with role `DOCTOR`.
- Patients have personal data and are linked to a user with role `PATIENT`.
- Appointments require one doctor and one patient.
- A doctor cannot have two active appointments at the same date and time.
- A patient cannot have two active appointments at the same date and time.
- Cancelled appointments do not block new appointments at the same date and time.
- Cancelled appointments cannot be completed.
- Completed appointments cannot be completed again.

## Development Notes

This project intentionally keeps some choices simple because it is focused on learning:

- Schema management uses `spring.jpa.hibernate.ddl-auto=update`.
- There are no Flyway or Liquibase migrations yet.
- Most resource endpoints are currently restricted to `ADMIN`.
- Doctors do not have a dedicated profile entity yet.
- List endpoints do not use pagination yet.
- Automated tests are still minimal.

Good next improvements:

- Add role-aware access so doctors and patients can see their own appointments.
- Add pagination to list endpoints.
- Add automated tests for services and controllers.
- Add Flyway migrations.
- Add doctor profile data, such as name, CRM, and specialty.
- Add appointment duration and clinic business hours.

## Useful Commands

Start database:

```bash
docker compose up -d medclinic-db
```

Stop containers:

```bash
docker compose down
```

Run tests:

```bash
./mvnw test
```

Build package:

```bash
./mvnw clean package
```
