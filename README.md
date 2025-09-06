# Insurance Management System

A Spring Boot application for managing insurance policies, claims, and bills with role-based authentication.

## Features

- **Authentication & Authorization**: JWT-based security with role-based access control
- **User Roles**: ADMIN, AGENT, USER with different permissions
- **CRUD Operations**: Manage policies, claims, bills, and users
- **Security**: BCrypt password encryption and JWT token validation

## Tech Stack

- Java 21
- Spring Boot 3.5.5
- Spring Security
- Spring Data JPA
- H2 Database (in-memory)
- JWT (JSON Web Tokens)
- Lombok
- Maven

## Quick Start

### 1. Clone and Run
```bash
git clone <repository-url>
cd insurance-management
mvn spring-boot:run
```

Application runs on: `http://localhost:8082`

### 2. Register Users

**Register ADMIN:**
```bash
POST http://localhost:8082/api/auth/register
Content-Type: application/json

{
    "username": "admin",
    "email": "admin@test.com",
    "password": "admin123",
    "role": "ADMIN"
}
```

**Register AGENT:**
```bash
POST http://localhost:8082/api/auth/register
Content-Type: application/json

{
    "username": "agent",
    "email": "agent@test.com",
    "password": "agent123",
    "role": "AGENT"
}
```

**Register USER:**
```bash
POST http://localhost:8082/api/auth/register
Content-Type: application/json

{
    "username": "user",
    "email": "user@test.com",
    "password": "user123",
    "role": "USER"
}
```

### 3. Login to Get JWT Token

```bash
POST http://localhost:8082/api/auth/login
Content-Type: application/json

{
    "username": "admin",
    "password": "admin123"
}
```

Response:
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### 4. Use Token for API Calls

```bash
GET http://localhost:8082/api/policies
Authorization: Bearer YOUR_JWT_TOKEN
```

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user

### Policies
- `GET /api/policies` - Get all policies (All roles)
- `GET /api/policies/{id}` - Get policy by ID (All roles)
- `POST /api/policies` - Create policy (ADMIN, AGENT)
- `PUT /api/policies/{id}` - Update policy (ADMIN, AGENT)
- `DELETE /api/policies/{id}` - Delete policy (ADMIN only)

### Claims
- `GET /api/claims` - Get all claims (All roles)
- `GET /api/claims/{id}` - Get claim by ID (All roles)
- `POST /api/claims` - Create claim (ADMIN, AGENT)
- `PUT /api/claims/{id}` - Update claim (ADMIN, AGENT)
- `DELETE /api/claims/{id}` - Delete claim (ADMIN only)

### Bills
- `GET /api/bills` - Get all bills (All roles)
- `GET /api/bills/{id}` - Get bill by ID (All roles)
- `POST /api/bills` - Create bill (ADMIN, AGENT)
- `PUT /api/bills/{id}` - Update bill (ADMIN, AGENT)
- `DELETE /api/bills/{id}` - Delete bill (ADMIN only)

## Role Permissions

| Role  | Create | Read | Update | Delete |
|-------|--------|------|--------|--------|
| ADMIN | ✅     | ✅   | ✅     | ✅     |
| AGENT | ✅     | ✅   | ✅     | ❌     |
| USER  | ❌     | ✅   | ❌     | ❌     |

## Sample API Calls

### Create Policy (ADMIN/AGENT)
```bash
POST http://localhost:8082/api/policies
Authorization: Bearer YOUR_TOKEN
Content-Type: application/json

{
    "policyNumber": "P001",
    "policyHolderName": "John Doe",
    "type": "Health",
    "premium": 1000.0,
    "startDate": "2024-01-01",
    "endDate": "2024-12-31"
}
```

### Create Claim (ADMIN/AGENT)
```bash
POST http://localhost:8082/api/claims
Authorization: Bearer YOUR_TOKEN
Content-Type: application/json

{
    "claimNumber": "C001",
    "policyNumber": "P001",
    "claimantNmae": "John Doe",
    "claimAmount": 5000.0,
    "status": "Pending",
    "dateFiled": "2024-06-01"
}
```

### Create Bill (ADMIN/AGENT)
```bash
POST http://localhost:8082/api/bills
Authorization: Bearer YOUR_TOKEN
Content-Type: application/json

{
    "billNumber": "B001",
    "policyNumber": "P001",
    "amount": 1000.0,
    "status": "Pending",
    "dueDate": "2024-07-01"
}
```

## Database Access

H2 Console: `http://localhost:8082/h2-console`
- JDBC URL: `jdbc:h2:mem:policydb`
- Username: `sa`
- Password: (empty)

## Configuration

Key configurations in `application.properties`:
- Server port: `8082`
- JWT secret and expiration
- H2 database settings
- JPA/Hibernate settings

## Complete End-to-End API Testing Guide

### Step 1: Start Application
```bash
mvn spring-boot:run
```

### Step 2: Register Admin User
```bash
POST http://localhost:8082/api/auth/register
Content-Type: application/json

{
    "username": "admin",
    "email": "admin@test.com",
    "password": "admin123",
    "role": "ADMIN"
}
```

### Step 3: Login and Get JWT Token
```bash
POST http://localhost:8082/api/auth/login
Content-Type: application/json

{
    "username": "admin",
    "password": "admin123"
}
```
**Copy the JWT token from response for next steps**

### Step 4: Create Policy
```bash
POST http://localhost:8082/api/policies
Authorization: Bearer YOUR_JWT_TOKEN
Content-Type: application/json

{
    "policyNumber": "P001",
    "policyHolderName": "John Doe",
    "type": "HEALTH",
    "premium": 1000.0,
    "startDate": "2024-01-01",
    "endDate": "2024-12-31",
    "userId": 1
}
```

### Step 5: Create Claim
```bash
POST http://localhost:8082/api/claims
Authorization: Bearer YOUR_JWT_TOKEN
Content-Type: application/json

{
    "claimNumber": "C001",
    "policyId": 1,
    "claimantName": "John Doe",
    "claimAmount": 5000.0,
    "status": "PENDING",
    "dateFiled": "2024-09-01"
}
```

### Step 6: Create Bill
```bash
POST http://localhost:8082/api/bills
Authorization: Bearer YOUR_JWT_TOKEN
Content-Type: application/json

{
    "billNumber": "B001",
    "policyId": 1,
    "amount": 1000.0,
    "status": "PENDING",
    "dueDate": "2024-10-01"
}
```

### Step 7: Test Advanced Features

**Policy Renewal:**
```bash
POST http://localhost:8082/api/advanced/policies/1/renew?years=1
Authorization: Bearer YOUR_JWT_TOKEN
```

**Generate Bills:**
```bash
POST http://localhost:8082/api/advanced/generate-bills
Authorization: Bearer YOUR_JWT_TOKEN
```

**Get Notifications:**
```bash
GET http://localhost:8082/api/advanced/notifications
Authorization: Bearer YOUR_JWT_TOKEN
```

**Pay Bill:**
```bash
POST http://localhost:8082/api/advanced/bills/1/pay?amount=1000.0&paymentMethod=CREDIT_CARD
Authorization: Bearer YOUR_JWT_TOKEN
```

**Get Policy Alerts:**
```bash
GET http://localhost:8082/api/advanced/notifications/policy-alerts
Authorization: Bearer YOUR_JWT_TOKEN
```

**Get Claim Notifications:**
```bash
GET http://localhost:8082/api/advanced/notifications/claim-status
Authorization: Bearer YOUR_JWT_TOKEN
```

**Get Expiring Policies:**
```bash
GET http://localhost:8082/api/advanced/policies/expiring
Authorization: Bearer YOUR_JWT_TOKEN
```

**Get Expired Policies (ADMIN only):**
```bash
GET http://localhost:8082/api/advanced/policies/expired
Authorization: Bearer YOUR_JWT_TOKEN
```

### Step 8: Create Policy Expiring Soon (for notifications)
```bash
POST http://localhost:8082/api/policies
Authorization: Bearer YOUR_JWT_TOKEN
Content-Type: application/json

{
    "policyNumber": "P002",
    "policyHolderName": "Jane Doe",
    "type": "AUTO",
    "premium": 800.0,
    "startDate": "2024-01-01",
    "endDate": "2025-09-15",
    "userId": 1
}
```

### Step 9: Test All CRUD Operations

**Get All Policies:**
```bash
GET http://localhost:8082/api/policies
Authorization: Bearer YOUR_JWT_TOKEN
```

**Get Policy by ID:**
```bash
GET http://localhost:8082/api/policies/1
Authorization: Bearer YOUR_JWT_TOKEN
```

**Update Policy:**
```bash
PUT http://localhost:8082/api/policies/1
Authorization: Bearer YOUR_JWT_TOKEN
Content-Type: application/json

{
    "policyNumber": "P001",
    "policyHolderName": "John Doe Updated",
    "type": "HEALTH",
    "premium": 1200.0,
    "startDate": "2024-01-01",
    "endDate": "2025-12-31",
    "userId": 1
}
```

**Delete Policy (ADMIN only):**
```bash
DELETE http://localhost:8082/api/policies/2
Authorization: Bearer YOUR_JWT_TOKEN
```

## Advanced Features

- **Policy Renewal**: Extends policy end date by specified years
- **Premium Calculation**: Automatic premium calculation based on policy type
- **Claim Approval**: Auto-approve claims ≤$10,000
- **Bill Generation**: Scheduled automatic bill generation
- **Payment Processing**: Handle bill payments with late fees
- **Notifications**: Policy expiration and claim status alerts
- **Business Rules**: Comprehensive validation and eligibility checks

### Important Notes

- **H2 Database**: Data is lost on application restart (in-memory)
- **JWT Tokens**: Expire after configured time, get fresh token if needed
- **Role-based Access**: Different endpoints require different roles
- **Validation**: All inputs are validated with proper error messages
- **Foreign Keys**: Policies linked to Users, Claims/Bills linked to Policies

## Testing

Use Postman, curl, or any REST client to test the APIs. Remember to include the JWT token in the Authorization header for protected endpoints.