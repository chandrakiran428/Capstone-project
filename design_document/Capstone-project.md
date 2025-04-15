## Business Logic

### 1. Summary of the Event Management Service

The Event Management Service provides a platform for users to register, authenticate, and manage their event bookings efficiently. Users can create, view, and manage their bookings while also interacting with vendors and venues. Admins hold elevated permissions to manage vendor and venue details and oversee booking statuses. The service ensures secure user authentication through hashed passwords and maintains unique usernames for each user. It also enforces constraints such as venue capacity and proper vendor assignment to bookings. The backend processes user requests through a structured API, facilitating CRUD operations on user profiles, events, bookings, vendors, and venues. The system is designed to track booking history and manage event statuses effectively, ensuring a seamless experience for users and admins alike.

### 2. Service Information Table

| Base Path                     | Source Repository         | Description                                                                                     |
|-------------------------------|---------------------------|-------------------------------------------------------------------------------------------------|
| `/api/v1/users`              | [GitHub Repo Link](#)    | API for user registration, authentication, and profile management.                             |
| `/api/v1/events`             | [GitHub Repo Link](#)    | API for managing events, including creation, viewing, and updating event details.              |
| `/api/v1/bookings`           | [GitHub Repo Link](#)    | API for managing bookings, including viewing booking history and updating booking statuses.     |
| `/api/v1/vendors`            | [GitHub Repo Link](#)    | API for vendor management, including retrieving vendor details and assigning vendors to bookings.|
| `/api/v1/venues`             | [GitHub Repo Link](#)    | API for venue management, including adding new venues and retrieving venue information.         |

### 3. Overall API Specification

| Endpoint                        | Method | Authentication | Description                                      | Expected Behavior                                       |
|---------------------------------|--------|----------------|--------------------------------------------------|--------------------------------------------------------|
| `/api/v1/users/register`       | POST   | No             | Register a new user                              | Returns success or error message                        |
| `/api/v1/users/login`          | POST   | No             | User login                                       | Returns user session or error message                   |
| `/api/v1/events`               | GET    | Yes            | Get events for booking                           | Returns list of events                                  |
| `/api/v1/bookings`             | GET    | Yes            | Get all bookings                                 | Returns list of bookings                                 |
| `/api/v1/bookings/{id}`        | GET    | Yes            | Get booking details by ID                        | Returns booking details                                  |
| `/api/v1/events/submit`        | POST   | Yes            | Submit a new event                               | Creates a new event and returns confirmation            |
| `/api/v1/vendors`              | GET    | Yes            | Get all vendors                                  | Returns list of vendors                                  |
| `/api/v1/vendors/{id}`         | GET    | Yes            | Get vendor details by ID                         | Returns vendor details                                   |
| `/api/v1/bookings/assignVendor`| POST   | Yes            | Assign a vendor to a booking                     | Returns success or error message                        |
| `/api/v1/venues/add`           | POST   | Yes            | Add a new venue                                  | Creates a new venue and returns confirmation            |
| `/api/v1/venues/list`          | GET    | Yes            | Get list of all venues                           | Returns list of venues                                   |
| `/api/v1/users/updateProfile`  | POST   | Yes            | Update user profile                              | Updates user details and returns confirmation            |
| `/api/v1/bookings/new`         | GET    | Yes            | Get new bookings                                 | Returns list of new bookings                             |
| `/api/v1/bookings/history`     | GET    | Yes            | Get booking history                              | Returns list of booking history                          |
| `/api/v1/confirmation`         | GET    | Yes            | Show confirmation page                           | Displays confirmation message                            |
| `/api/v1/errorPage`            | GET    | Yes            | Show error page                                  | Displays error message                                   |

### 4. Endpoints Summary

1. `/api/v1/users/register`
2. `/api/v1/users/login`
3. `/api/v1/events`
4. `/api/v1/bookings`
5. `/api/v1/bookings/{id}`
6. `/api/v1/events/submit`
7. `/api/v1/vendors`
8. `/api/v1/vendors/{id}`
9. `/api/v1/bookings/assignVendor`
10. `/api/v1/venues/add`
11. `/api/v1/venues/list`
12. `/api/v1/users/updateProfile`
13. `/api/v1/bookings/new`
14. `/api/v1/bookings/history`
15. `/api/v1/confirmation`
16. `/api/v1/errorPage` 

This structured breakdown provides a comprehensive overview of the Event Management Service, detailing its core functions, API specifications, and endpoint summaries.

## Plant UML script

Here's a PlantUML sequence diagram script that captures the interactions and processes defined in your business logic for the event management system. This script includes all key actors, main interactions, critical decision points, and mentions the relevant components involved.

```plantuml
@startuml
actor User
actor Admin
actor Vendor
participant "System" as System
database "Database" as DB
participant "Kafka" as Kafka

User -> System : /register (username, password, email)
System -> DB : Create User
DB --> System : User Created
System --> User : Success/Error Message

User -> System : /login (username, password)
System -> DB : Validate User Credentials
DB --> System : User Validated/Invalid
System --> User : User Session/Access Granted/Error Message

User -> System : /events (GET)
System -> DB : Fetch Events
DB --> System : List of Events
System --> User : Return Event List

User -> System : /newbooking (GET)
System -> DB : Fetch New Bookings
DB --> System : List of New Bookings
System --> User : Return New Bookings

User -> System : /bookings (GET)
System -> DB : Fetch All Bookings
DB --> System : List of Bookings
System --> User : Return Booking List

User -> System : /submit (Event Details)
System -> DB : Create Event
DB --> System : Event Created
System --> User : Confirmation Message

User -> System : /addvenue (Venue Details)
System -> DB : Create Venue
DB --> System : Venue Created
System --> User : Confirmation Message

User -> System : /assignVendor/{bookingId} (Vendor ID)
System -> DB : Validate Booking and Vendor
alt Vendor and Booking Valid
    System -> DB : Assign Vendor to Booking
    DB --> System : Vendor Assigned
    System --> User : Success Message
else Vendor or Booking Invalid
    System --> User : Error Message
end

User -> System : /history (GET)
System -> DB : Fetch Booking History
DB --> System : List of Booking History
System --> User : Return Booking History

Admin -> System : /vendors (GET)
System -> DB : Fetch All Vendors
DB --> System : List of Vendors
System --> Admin : Return Vendor List

Admin -> System : /vendor/{id} (GET)
System -> DB : Fetch Vendor Details
DB --> System : Vendor Details
System --> Admin : Return Vendor Details

Admin -> System : /bookings/{id} (GET)
System -> DB : Fetch Booking Details by ID
DB --> System : Booking Details
System --> Admin : Return Booking Details

Admin -> System : /updateProfile (User Details)
System -> DB : Update User Details
DB --> System : User Details Updated
System --> Admin : Confirmation Message

Admin -> System : /confirmation (GET)
System --> Admin : Show Confirmation Page

User -> System : /errorPage (GET)
System --> User : Show Error Message

System -> Kafka : Publish Event Notification
Kafka --> System : Event Notification Sent

@enduml
```

### Explanation of the Diagram:
1. **Actors**: The diagram includes `User`, `Admin`, and `Vendor` as actors, along with the `System` and `Database`.
2. **Interactions**: It shows the main interactions such as user registration, login, event booking, vendor assignment, and history tracking.
3. **Decision Points**: The diagram highlights critical decision points, such as validating user credentials and checking the validity of vendor and booking assignments.
4. **Messages**: Each interaction is described with clear and descriptive messages, following the sequence of operations.
5. **Components**: It mentions components involved in the service, including the Database and Kafka for event publishing.

This sequence diagram script should help visualize the flow of operations in your event management system effectively.

## UML Sequence Diagram

![Diagram](data:image/png;base64,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)

## UML Sequence Diagram Path

https://github.com/chandrakiran428/Capstone-project/blob/main/images/Capstone-project_6b885d9c-0b0a-4c05-bca6-417072479d88.png

## API Documentation

# API Documentation

## /register POST
Endpoint URL: /register  
HTTP Method: POST  
Authentication: None  
Authorization: None  
Description: Register a new user  

### Request Parameters
| Parameter Name | Type   | Required | Description                      |
|----------------|--------|----------|----------------------------------|
| username       | string | Yes      | Unique username for the user     |
| password       | string | Yes      | Password for the user            |
| email          | string | Yes      | Email address of the user        |
| address        | string | No       | User's address                   |
| mobile         | string | No       | User's mobile number             |

### Sample Request
```json
{
  "username": "john_doe",
  "password": "securePassword123",
  "email": "john@example.com",
  "address": "123 Main St",
  "mobile": "1234567890"
}
```

### Response Parameters
| Field Name | Type   | Description                       |
|------------|--------|-----------------------------------|
| message     | string | Success or error message          |
| userId      | int    | ID of the newly registered user   |

### Sample Response
```json
{
  "message": "User registered successfully.",
  "userId": 1
}
```

### Components Involved
- User Service
- Database

### Data Mapping
| Source Field | Target Field      | Transformation      |
|--------------|------------------|---------------------|
| request.username | db_record.username | Direct mapping       |
| request.password | db_record.password | Hashing applied      |
| request.email    | db_record.email    | Direct mapping       |
| request.address  | db_record.address  | Direct mapping       |
| request.mobile   | db_record.mobile   | Direct mapping       |

### Sample Errors
```json
{
  "error": "Username already exists.",
  "code": 400,
  "message": "The username provided is already taken."
}
```

### Root Exception Details
| Name        | Description                          | Header/Payload | Type   | Mandatory/Optional | Notes                       |
|-------------|--------------------------------------|----------------|--------|---------------------|-----------------------------|
| UserExists  | Indicates the username is taken      | Payload        | string | Mandatory           | Usernames must be unique.   |
| ValidationError | Indicates invalid input data      | Payload        | string | Mandatory           | Check required fields.      |

### Error Codes
| HTTP Code | Message                     | Description                          |
|-----------|-----------------------------|--------------------------------------|
| 400       | Username already exists      | The username is already taken.       |
| 400       | Validation error            | Input data did not pass validation.  |

---

## /login POST
Endpoint URL: /login  
HTTP Method: POST  
Authentication: None  
Authorization: None  
Description: User login  

### Request Parameters
| Parameter Name | Type   | Required | Description                      |
|----------------|--------|----------|----------------------------------|
| username       | string | Yes      | Username of the user             |
| password       | string | Yes      | Password of the user             |

### Sample Request
```json
{
  "username": "john_doe",
  "password": "securePassword123"
}
```

### Response Parameters
| Field Name  | Type   | Description                       |
|-------------|--------|-----------------------------------|
| message     | string | Success or error message          |
| sessionId   | string | Session identifier for the user   |

### Sample Response
```json
{
  "message": "Login successful.",
  "sessionId": "abc123xyz"
}
```

### Components Involved
- Authentication Service
- User Service
- Database

### Data Mapping
| Source Field | Target Field      | Transformation      |
|--------------|------------------|---------------------|
| request.username | db_record.username | Direct mapping       |
| request.password | db_record.password | Hashing applied      |

### Sample Errors
```json
{
  "error": "Invalid credentials.",
  "code": 401,
  "message": "The username or password is incorrect."
}
```

### Root Exception Details
| Name        | Description                          | Header/Payload | Type   | Mandatory/Optional | Notes                       |
|-------------|--------------------------------------|----------------|--------|---------------------|-----------------------------|
| InvalidCredentials | Indicates incorrect username/password | Payload        | string | Mandatory           | Ensure valid credentials.   |

### Error Codes
| HTTP Code | Message                     | Description                          |
|-----------|-----------------------------|--------------------------------------|
| 401       | Invalid credentials         | The username or password is incorrect. |

---

## /events GET
Endpoint URL: /events  
HTTP Method: GET  
Authentication: Bearer Token Required  
Authorization: User Role Required  
Description: Get events for booking  

### Request Parameters
| Parameter Name | Type   | Required | Description                      |
|----------------|--------|----------|----------------------------------|
| None           | -      | -        | No request parameters required    |

### Sample Request
```json
{}
```

### Response Parameters
| Field Name | Type   | Description                       |
|------------|--------|-----------------------------------|
| events     | array  | List of available events          |

### Sample Response
```json
{
  "events": [
    {
      "id": 1,
      "name": "Birthday Party",
      "date": "2023-12-25",
      "venue": "Central Park"
    },
    {
      "id": 2,
      "name": "Corporate Meeting",
      "date": "2023-12-30",
      "venue": "Downtown Conference Center"
    }
  ]
}
```

### Components Involved
- Event Service
- Database

### Data Mapping
| Source Field | Target Field      | Transformation      |
|--------------|------------------|---------------------|
| db_record.id | response.events.id | Direct mapping       |
| db_record.name | response.events.name | Direct mapping       |
| db_record.date | response.events.date | Direct mapping       |
| db_record.venue | response.events.venue | Direct mapping       |

### Sample Errors
```json
{
  "error": "Unauthorized access.",
  "code": 403,
  "message": "You do not have permission to access this resource."
}
```

### Root Exception Details
| Name        | Description                          | Header/Payload | Type   | Mandatory/Optional | Notes                       |
|-------------|--------------------------------------|----------------|--------|---------------------|-----------------------------|
| UnauthorizedAccess | Indicates lack of permission to access | Header        | string | Mandatory           | Check user roles and permissions. |

### Error Codes
| HTTP Code | Message                     | Description                          |
|-----------|-----------------------------|--------------------------------------|
| 403       | Unauthorized access         | User does not have permission to access this resource. |

---

## /bookings GET
Endpoint URL: /bookings  
HTTP Method: GET  
Authentication: Bearer Token Required  
Authorization: User Role Required  
Description: Get all bookings  

### Request Parameters
| Parameter Name | Type   | Required | Description                      |
|----------------|--------|----------|----------------------------------|
| None           | -      | -        | No request parameters required    |

### Sample Request
```json
{}
```

### Response Parameters
| Field Name | Type   | Description                       |
|------------|--------|-----------------------------------|
| bookings   | array  | List of all bookings              |

### Sample Response
```json
{
  "bookings": [
    {
      "id": 1,
      "event": "Birthday Party",
      "status": "accepted"
    },
    {
      "id": 2,
      "event": "Corporate Meeting",
      "status": "pending"
    }
  ]
}
```

### Components Involved
- Booking Service
- Database

### Data Mapping
| Source Field | Target Field      | Transformation      |
|--------------|------------------|---------------------|
| db_record.id | response.bookings.id | Direct mapping       |
| db_record.event | response.bookings.event | Direct mapping       |
| db_record.status | response.bookings.status | Direct mapping       |

### Sample Errors
```json
{
  "error": "Unauthorized access.",
  "code": 403,
  "message": "You do not have permission to access this resource."
}
```

### Root Exception Details
| Name        | Description                          | Header/Payload | Type   | Mandatory/Optional | Notes                       |
|-------------|--------------------------------------|----------------|--------|---------------------|-----------------------------|
| UnauthorizedAccess | Indicates lack of permission to access | Header        | string | Mandatory           | Check user roles and permissions. |

### Error Codes
| HTTP Code | Message                     | Description                          |
|-----------|-----------------------------|--------------------------------------|
| 403       | Unauthorized access         | User does not have permission to access this resource. |

---

## /bookings/{id} GET
Endpoint URL: /bookings/{id}  
HTTP Method: GET  
Authentication: Bearer Token Required  
Authorization: User Role Required  
Description: Get booking details by ID  

### Request Parameters
| Parameter Name | Type   | Required | Description                      |
|----------------|--------|----------|----------------------------------|
| id             | int    | Yes      | ID of the booking                |

### Sample Request
```json
{
  "id": 1
}
```

### Response Parameters
| Field Name | Type   | Description                       |
|------------|--------|-----------------------------------|
| booking    | object | Details of the specified booking   |

### Sample Response
```json
{
  "booking": {
    "id": 1,
    "event": "Birthday Party",
    "status": "accepted",
    "venue": "Central Park"
  }
}
```

### Components Involved
- Booking Service
- Database

### Data Mapping
| Source Field | Target Field      | Transformation      |
|--------------|------------------|---------------------|
| db_record.id | response.booking.id | Direct mapping       |
| db_record.event | response.booking.event | Direct mapping       |
| db_record.status | response.booking.status | Direct mapping       |
| db_record.venue | response.booking.venue | Direct mapping       |

### Sample Errors
```json
{
  "error": "Booking not found.",
  "code": 404,
  "message": "The specified booking ID does not exist."
}
```

### Root Exception Details
| Name        | Description                          | Header/Payload | Type   | Mandatory/Optional | Notes                       |
|-------------|--------------------------------------|----------------|--------|---------------------|-----------------------------|
| BookingNotFound | Indicates the booking ID does not exist | Payload        | int    | Mandatory           | Ensure booking ID is valid. |

### Error Codes
| HTTP Code | Message                     | Description                          |
|-----------|-----------------------------|--------------------------------------|
| 404       | Booking not found           | The specified booking ID does not exist. |

---

## /submit POST
Endpoint URL: /submit  
HTTP Method: POST  
Authentication: Bearer Token Required  
Authorization: User Role Required  
Description: Submit a new event  

### Request Parameters
| Parameter Name | Type   | Required | Description                      |
|----------------|--------|----------|----------------------------------|
| name           | string | Yes      | Name of the event                |
| date           | string | Yes      | Date of the event                |
| venue          | string | Yes      | Venue for the event              |
| numberOfAttendees | int  | Yes      | Expected number of attendees    |
| foodType       | string | No       | Type of food for the event       |

### Sample Request
```json
{
  "name": "Wedding Ceremony",
  "date": "2024-05-15",
  "venue": "City Hall",
  "numberOfAttendees": 100,
  "foodType": "Buffet"
}
```

### Response Parameters
| Field Name | Type   | Description                       |
|------------|--------|-----------------------------------|
| message     | string | Success or error message          |
| eventId     | int    | ID of the newly created event     |

### Sample Response
```json
{
  "message": "Event submitted successfully.",
  "eventId": 1
}
```

### Components Involved
- Event Service
- Database

### Data Mapping
| Source Field | Target Field      | Transformation      |
|--------------|------------------|---------------------|
| request.name | db_record.name    | Direct mapping       |
| request.date | db_record.date    | Direct mapping       |
| request.venue | db_record.venue   | Direct mapping       |
| request.numberOfAttendees | db_record.numberOfAttendees | Direct mapping |
| request.foodType | db_record.foodType | Direct mapping     |

### Sample Errors
```json
{
  "error": "Invalid venue.",
  "code": 400,
  "message": "The specified venue does not exist."
}
```

### Root Exception Details
| Name        | Description                          | Header/Payload | Type   | Mandatory/Optional | Notes                       |
|-------------|--------------------------------------|----------------|--------|---------------------|-----------------------------|
| InvalidVenue | Indicates the venue is not valid    | Payload        | string | Mandatory           | Ensure venue exists.       |

### Error Codes
| HTTP Code | Message                     | Description                          |
|-----------|-----------------------------|--------------------------------------|
| 400       | Invalid venue               | The specified venue does not exist.  |

---

## /vendors GET
Endpoint URL: /vendors  
HTTP Method: GET  
Authentication: Bearer Token Required  
Authorization: Admin Role Required  
Description: Get all vendors  

### Request Parameters
| Parameter Name | Type   | Required | Description                      |
|----------------|--------|----------|----------------------------------|
| None           | -      | -        | No request parameters required    |

### Sample Request
```json
{}
```

### Response Parameters
| Field Name | Type   | Description                       |
|------------|--------|-----------------------------------|
| vendors    | array  | List of all vendors               |

### Sample Response
```json
{
  "vendors": [
    {
      "id": 1,
      "vendor_name": "Catering Co",
      "vendor_email": "info@cateringco.com"
    },
    {
      "id": 2,
      "vendor_name": "DJ Services",
      "vendor_email": "contact@djservices.com"
    }
  ]
}
```

### Components Involved
- Vendor Service
- Database

### Data Mapping
| Source Field | Target Field      | Transformation      |
|--------------|------------------|---------------------|
| db_record.id | response.vendors.id | Direct mapping       |
| db_record.vendor_name | response.vendors.vendor_name | Direct mapping |
| db_record.vendor_email | response.vendors.vendor_email | Direct mapping |

### Sample Errors
```json
{
  "error": "Unauthorized access.",
  "code": 403,
  "message": "You do not have permission to access this resource."
}
```

### Root Exception Details
| Name        | Description                          | Header/Payload | Type   | Mandatory/Optional | Notes                       |
|-------------|--------------------------------------|----------------|--------|---------------------|-----------------------------|
| UnauthorizedAccess | Indicates lack of permission to access | Header        | string | Mandatory           | Check user roles and permissions. |

### Error Codes
| HTTP Code | Message                     | Description                          |
|-----------|-----------------------------|--------------------------------------|
| 403       | Unauthorized access         | User does not have permission to access this resource. |

---

## /vendor/{id} GET
Endpoint URL: /vendor/{id}  
HTTP Method: GET  
Authentication: Bearer Token Required  
Authorization: Admin Role Required  
Description: Get vendor details by ID  

### Request Parameters
| Parameter Name | Type   | Required | Description                      |
|----------------|--------|----------|----------------------------------|
| id             | int    | Yes      | ID of the vendor                 |

### Sample Request
```json
{
  "id": 1
}
```

### Response Parameters
| Field Name | Type   | Description                       |
|------------|--------|-----------------------------------|
| vendor     | object | Details of the specified vendor    |

### Sample Response
```json
{
  "vendor": {
    "id": 1,
    "vendor_name": "Catering Co",
    "vendor_email": "info@cateringco.com",
    "vendor_mobile": "1234567890"
  }
}
```

### Components Involved
- Vendor Service
- Database

### Data Mapping
| Source Field | Target Field      | Transformation      |
|--------------|------------------|---------------------|
| db_record.id | response.vendor.id | Direct mapping       |
| db_record.vendor_name | response.vendor.vendor_name | Direct mapping |
| db_record.vendor_email | response.vendor.vendor_email | Direct mapping |
| db_record.vendor_mobile | response.vendor.vendor_mobile | Direct mapping |

### Sample Errors
```json
{
  "error": "Vendor not found.",
  "code": 404,
  "message": "The specified vendor ID does not exist."
}
```

### Root Exception Details
| Name        | Description                          | Header/Payload | Type   | Mandatory/Optional | Notes                       |
|-------------|--------------------------------------|----------------|--------|---------------------|-----------------------------|
| VendorNotFound | Indicates the vendor ID does not exist | Payload        | int    | Mandatory           | Ensure vendor ID is valid. |

### Error Codes
| HTTP Code | Message                     | Description                          |
|-----------|-----------------------------|--------------------------------------|
| 404       | Vendor not found            | The specified vendor ID does not exist. |

---

## /assignVendor/{bookingId} POST
Endpoint URL: /assignVendor/{bookingId}  
HTTP Method: POST  
Authentication: Bearer Token Required  
Authorization: Admin Role Required  
Description: Assign a vendor to a booking  

### Request Parameters
| Parameter Name | Type   | Required | Description                      |
|----------------|--------|----------|----------------------------------|
| bookingId      | int    | Yes      | ID of the booking                |
| vendorId       | int    | Yes      | ID of the vendor to be assigned  |

### Sample Request
```json
{
  "vendorId": 1
}
```

### Response Parameters
| Field Name | Type   | Description                       |
|------------|--------|-----------------------------------|
| message     | string | Success or error message          |

### Sample Response
```json
{
  "message": "Vendor assigned successfully."
}
```

### Components Involved
- Booking Service
- Vendor Service
- Database

### Data Mapping
| Source Field | Target Field      | Transformation      |
|--------------|------------------|---------------------|
| request.bookingId | db_record.bookingId | Direct mapping       |
| request.vendorId | db_record.vendorId | Direct mapping       |

### Sample Errors
```json
{
  "error": "Vendor assignment failed.",
  "code": 400,
  "message": "The specified vendor or booking does not exist."
}
```

### Root Exception Details
| Name        | Description                          | Header/Payload | Type   | Mandatory/Optional | Notes                       |
|-------------|--------------------------------------|----------------|--------|---------------------|-----------------------------|
| AssignmentFailed | Indicates the vendor assignment failed | Payload        | string | Mandatory           | Ensure both vendor and booking exist. |

### Error Codes
| HTTP Code | Message                     | Description                          |
|-----------|-----------------------------|--------------------------------------|
| 400       | Vendor assignment failed     | The specified vendor or booking does not exist. |

---

## /addvenue POST
Endpoint URL: /addvenue  
HTTP Method: POST  
Authentication: Bearer Token Required  
Authorization: Admin Role Required  
Description: Add a new venue  

### Request Parameters
| Parameter Name | Type   | Required | Description                      |
|----------------|--------|----------|----------------------------------|
| venueName      | string | Yes      | Name of the venue                |
| capacity       | int    | Yes      | Capacity of the venue            |
| address        | string | Yes      | Address of the venue             |
| phoneNumber    | string | No       | Contact number for the venue     |
| cost           | float  | Yes      | Cost associated with the venue    |

### Sample Request
```json
{
  "venueName": "Grand Hall",
  "capacity": 500,
  "address": "456 Event St",
  "phoneNumber": "9876543210",
  "cost": 2000.00
}
```

### Response Parameters
| Field Name | Type   | Description                       |
|------------|--------|-----------------------------------|
| message     | string | Success or error message          |
| venueId     | int    | ID of the newly created venue     |

### Sample Response
```json
{
  "message": "Venue added successfully.",
  "venueId": 1
}
```

### Components Involved
- Venue Service
- Database

### Data Mapping
| Source Field | Target Field      | Transformation      |
|--------------|------------------|---------------------|
| request.venueName | db_record.venueName | Direct mapping       |
| request.capacity | db_record.capacity | Direct mapping       |
| request.address | db_record.address | Direct mapping       |
| request.phoneNumber | db_record.phoneNumber | Direct mapping |
| request.cost | db_record.cost | Direct mapping       |

### Sample Errors
```json
{
  "error": "Venue capacity exceeded.",
  "code": 400,
  "message": "The venue capacity must be defined and cannot be zero."
}
```

### Root Exception Details
| Name        | Description                          | Header/Payload | Type   | Mandatory/Optional | Notes                       |
|-------------|--------------------------------------|----------------|--------|---------------------|-----------------------------|
| CapacityExceeded | Indicates the venue capacity is invalid | Payload        | int    | Mandatory           | Ensure valid capacity.      |

### Error Codes
| HTTP Code | Message                     | Description                          |
|-----------|-----------------------------|--------------------------------------|
| 400       | Venue capacity exceeded      | The venue capacity must be defined and cannot be zero. |

---

## /VenueList GET
Endpoint URL: /VenueList  
HTTP Method: GET  
Authentication: Bearer Token Required  
Authorization: User Role Required  
Description: Get list of all venues  

### Request Parameters
| Parameter Name | Type   | Required | Description                      |
|----------------|--------|----------|----------------------------------|
| None           | -      | -        | No request parameters required    |

### Sample Request
```json
{}
```

### Response Parameters
| Field Name | Type   | Description                       |
|------------|--------|-----------------------------------|
| venues     | array  | List of all venues                |

### Sample Response
```json
{
  "venues": [
    {
      "id": 1,
      "venueName": "Grand Hall",
      "capacity": 500
    },
    {
      "id": 2,
      "venueName": "Central Park",
      "capacity": 1000
    }
  ]
}
```

### Components Involved
- Venue Service
- Database

### Data Mapping
| Source Field | Target Field      | Transformation      |
|--------------|------------------|---------------------|
| db_record.id | response.venues.id | Direct mapping       |
| db_record.venueName | response.venues.venueName | Direct mapping |
| db_record.capacity | response.venues.capacity | Direct mapping |

### Sample Errors
```json
{
  "error": "Unauthorized access.",
  "code": 403,
  "message": "You do not have permission to access this resource."
}
```

### Root Exception Details
| Name        | Description                          | Header/Payload | Type   | Mandatory/Optional | Notes                       |
|-------------|--------------------------------------|----------------|--------|---------------------|-----------------------------|
| UnauthorizedAccess | Indicates lack of permission to access | Header        | string | Mandatory           | Check user roles and permissions. |

### Error Codes
| HTTP Code | Message                     | Description                          |
|-----------|-----------------------------|--------------------------------------|
| 403       | Unauthorized access         | User does not have permission to access this resource. |

---

## /updateProfile POST
Endpoint URL: /updateProfile  
HTTP Method: POST  
Authentication: Bearer Token Required  
Authorization: User Role Required  
Description: Update user profile  

### Request Parameters
| Parameter Name | Type   | Required | Description                      |
|----------------|--------|----------|----------------------------------|
| username       | string | Yes      | Unique username of the user      |
| email          | string | Yes      | Updated email address            |
| address        | string | No       | Updated address                  |
| mobile         | string | No       | Updated mobile number            |

### Sample Request
```json
{
  "username": "john_doe",
  "email": "john_new@example.com",
  "address": "789 New St",
  "mobile": "9876543210"
}
```

### Response Parameters
| Field Name | Type   | Description                       |
|------------|--------|-----------------------------------|
| message     | string | Success or error message          |

### Sample Response
```json
{
  "message": "Profile updated successfully."
}
```

### Components Involved
- User Service
- Database

### Data Mapping
| Source Field | Target Field      | Transformation      |
|--------------|------------------|---------------------|
| request.username | db_record.username | Direct mapping       |
| request.email | db_record.email   | Direct mapping       |
| request.address | db_record.address | Direct mapping       |
| request.mobile | db_record.mobile  | Direct mapping       |

### Sample Errors
```json
{
  "error": "Email already in use.",
  "code": 400,
  "message": "The email address is already associated with another account."
}
```

### Root Exception Details
| Name        | Description                          | Header/Payload | Type   | Mandatory/Optional | Notes                       |
|-------------|--------------------------------------|----------------|--------|---------------------|-----------------------------|
| EmailInUse | Indicates the email is already taken | Payload        | string | Mandatory           | Ensure email is unique.     |

### Error Codes
| HTTP Code | Message                     | Description                          |
|-----------|-----------------------------|--------------------------------------|
| 400       | Email already in use        | The email address is already associated with another account. |

---

## /newbooking GET
Endpoint URL: /newbooking  
HTTP Method: GET  
Authentication: Bearer Token Required  
Authorization: User Role Required  
Description: Get new bookings  

### Request Parameters
| Parameter Name | Type   | Required | Description                      |
|----------------|--------|----------|----------------------------------|
| None           | -      | -        | No request parameters required    |

### Sample Request
```json
{}
```

### Response Parameters
| Field Name | Type   | Description                       |
|------------|--------|-----------------------------------|
| bookings    | array  | List of new bookings              |

### Sample Response
```json
{
  "bookings": [
    {
      "id": 1,
      "event": "Wedding Ceremony",
      "status": "pending"
    },
    {
      "id": 2,
      "event": "Corporate Meeting",
      "status": "pending"
    }
  ]
}
```

### Components Involved
- Booking Service
- Database

### Data Mapping
| Source Field | Target Field      | Transformation      |
|--------------|------------------|---------------------|
| db_record.id | response.bookings.id | Direct mapping       |
| db_record.event | response.bookings.event | Direct mapping       |
| db_record.status | response.bookings.status | Direct mapping       |

### Sample Errors
```json
{
  "error": "Unauthorized access.",
  "code": 403,
  "message": "You do not have permission to access this resource."
}
```

### Root Exception Details
| Name        | Description                          | Header/Payload | Type   | Mandatory/Optional | Notes                       |
|-------------|--------------------------------------|----------------|--------|---------------------|-----------------------------|
| UnauthorizedAccess | Indicates lack of permission to access | Header        | string | Mandatory           | Check user roles and permissions. |

### Error Codes
| HTTP Code | Message                     | Description                          |
|-----------|-----------------------------|--------------------------------------|
| 403       | Unauthorized access         | User does not have permission to access this resource. |

---

## /history GET
Endpoint URL: /history  
HTTP Method: GET  
Authentication: Bearer Token Required  
Authorization: User Role Required  
Description: Get booking history  

### Request Parameters
| Parameter Name | Type   | Required | Description                      |
|----------------|--------|----------|----------------------------------|
| None           | -      | -        | No request parameters required    |

### Sample Request
```json
{}
```

### Response Parameters
| Field Name | Type   | Description                       |
|------------|--------|-----------------------------------|
| history     | array  | List of booking history           |

### Sample Response
```json
{
  "history": [
    {
      "id": 1,
      "event": "Birthday Party",
      "status": "accepted"
    },
    {
      "id": 2,
      "event": "Corporate Meeting",
      "status": "rejected"
    }
  ]
}
```

### Components Involved
- Booking Service
- Database

### Data Mapping
| Source Field | Target Field      | Transformation      |
|--------------|------------------|---------------------|
| db_record.id | response.history.id | Direct mapping       |
| db_record.event | response.history.event | Direct mapping       |
| db_record.status | response.history.status | Direct mapping       |

### Sample Errors
```json
{
  "error": "Unauthorized access.",
  "code": 403,
  "message": "You do not have permission to access this resource."
}
```

### Root Exception Details
| Name        | Description                          | Header/Payload | Type   | Mandatory/Optional | Notes                       |
|-------------|--------------------------------------|----------------|--------|---------------------|-----------------------------|
| UnauthorizedAccess | Indicates lack of permission to access | Header        | string | Mandatory           | Check user roles and permissions. |

### Error Codes
| HTTP Code | Message                     | Description                          |
|-----------|-----------------------------|--------------------------------------|
| 403       | Unauthorized access         | User does not have permission to access this resource. |

---

## /confirmation GET
Endpoint URL: /confirmation  
HTTP Method: GET  
Authentication: Bearer Token Required  
Authorization: User Role Required  
Description: Show confirmation page  

### Request Parameters
| Parameter Name | Type   | Required | Description                      |
|----------------|--------|----------|----------------------------------|
| None           | -      | -        | No request parameters required    |

### Sample Request
```json
{}
```

### Response Parameters
| Field Name | Type   | Description                       |
|------------|--------|-----------------------------------|
| message     | string | Confirmation message               |

### Sample Response
```json
{
  "message": "Your booking has been confirmed."
}
```

### Components Involved
- Booking Service
- Database

### Data Mapping
| Source Field | Target Field      | Transformation      |
|--------------|------------------|---------------------|
| db_record.message | response.message | Direct mapping       |

### Sample Errors
```json
{
  "error": "Unauthorized access.",
  "code": 403,
  "message": "You do not have permission to access this resource."
}
```

### Root Exception Details
| Name        | Description                          | Header/Payload | Type   | Mandatory/Optional | Notes                       |
|-------------|--------------------------------------|----------------|--------|---------------------|-----------------------------|
| UnauthorizedAccess | Indicates lack of permission to access | Header        | string | Mandatory           | Check user roles and permissions. |

### Error Codes
| HTTP Code | Message                     | Description                          |
|-----------|-----------------------------|--------------------------------------|
| 403       | Unauthorized access         | User does not have permission to access this resource. |

---

## /errorPage GET
Endpoint URL: /errorPage  
HTTP Method: GET  
Authentication: Bearer Token Required  
Authorization: User Role Required  
Description: Show error page  

### Request Parameters
| Parameter Name | Type   | Required | Description                      |
|----------------|--------|----------|----------------------------------|
| None           | -      | -        | No request parameters required    |

### Sample Request
```json
{}
```

### Response Parameters
| Field Name | Type   | Description                       |
|------------|--------|-----------------------------------|
| error       | string | Error message to display           |

### Sample Response
```json
{
  "error": "An unexpected error occurred."
}
```

### Components Involved
- Error Handling Service

### Data Mapping
| Source Field | Target Field      | Transformation      |
|--------------|------------------|---------------------|
| db_record.error | response.error | Direct mapping       |

### Sample Errors
```json
{
  "error": "Unauthorized access.",
  "code": 403,
  "message": "You do not have permission to access this resource."
}
```

### Root Exception Details
| Name        | Description                          | Header/Payload | Type   | Mandatory/Optional | Notes                       |
|-------------|--------------------------------------|----------------|--------|---------------------|-----------------------------|
| UnauthorizedAccess | Indicates lack of permission to access | Header        | string | Mandatory           | Check user roles and permissions. |

### Error Codes
| HTTP Code | Message                     | Description                          |
|-----------|-----------------------------|--------------------------------------|
| 403       | Unauthorized access         | User does not have permission to access this resource. |

---

