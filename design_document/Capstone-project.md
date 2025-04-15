## Business Logic

### 1. Summary of the Event Management Service

The Event Management Service provides a comprehensive platform for users to register, manage events, and interact with vendors. Users can create and view events, manage bookings, and track their booking history. Vendors can be assigned to events, ensuring that service providers are effectively integrated into the event planning process. The system supports user registration and authentication, maintaining secure password storage and unique user identification. Venue management is also a key feature, allowing venues to be added and managed with defined capacities. The service includes various business rules, such as event status management and vendor assignment constraints, ensuring a structured workflow. Overall, the service aims to streamline event planning and management for users while facilitating effective vendor collaboration.

### 2. API Overview Table

| Base Path                 | Source Repository               | Description                                                                                     |
|---------------------------|----------------------------------|-------------------------------------------------------------------------------------------------|
| `/register`               | [GitHub Repo Link](#)           | API for user registration, allowing new users to create an account with a unique username.    |
| `/login`                  | [GitHub Repo Link](#)           | API for user authentication, verifying user credentials during login.                          |
| `/events`                 | [GitHub Repo Link](#)           | API for retrieving all events associated with the logged-in user.                             |
| `/eventForm`              | [GitHub Repo Link](#)           | API for displaying the event creation form to users.                                          |
| `/submit`                 | [GitHub Repo Link](#)           | API for submitting new event details, saving the event with a "pending" status.               |
| `/bookings`               | [GitHub Repo Link](#)           | API for retrieving all booking records for the logged-in user.                                |
| `/vendors`                | [GitHub Repo Link](#)           | API for retrieving a list of all available vendors.                                           |
| `/assignVendor/{bookingId}`| [GitHub Repo Link](#)          | API for assigning a vendor to a specific booking.                                             |
| `/newbooking`             | [GitHub Repo Link](#)           | API for viewing newly created bookings.                                                        |
| `/history`                | [GitHub Repo Link](#)           | API for retrieving the user's booking history.                                                |
| `/VenueList`              | [GitHub Repo Link](#)           | API for retrieving a list of all venues available in the system.                              |
| `/addvenue`               | [GitHub Repo Link](#)           | API for adding a new venue to the system.                                                    |
| `/updateProfile`          | [GitHub Repo Link](#)           | API for updating user profile information.                                                    |
| `/confirmation`           | [GitHub Repo Link](#)           | API for displaying the confirmation page after event registration.                            |

### 3. Overall API Specification

| Endpoint                          | Method  | Authentication | Description                                         | Expected Behavior                               |
|-----------------------------------|---------|----------------|-----------------------------------------------------|------------------------------------------------|
| `/register`                       | POST    | No             | Register a new user                                | User is created and redirected to login page.  |
| `/login`                          | POST    | No             | Authenticate user                                   | User is redirected to home on success.         |
| `/events`                         | GET     | Yes            | Get all events                                     | Returns list of events for the logged-in user. |
| `/eventForm`                      | GET     | Yes            | Show event creation form                           | Returns event creation form.                    |
| `/submit`                         | POST    | Yes            | Submit new event                                   | Event is saved with status "pending".          |
| `/bookings`                       | GET     | Yes            | Get all bookings                                   | Returns list of bookings for the user.         |
| `/vendors`                        | GET     | Yes            | Get all vendors                                    | Returns list of all vendors.                    |
| `/assignVendor/{bookingId}`      | POST    | Yes            | Assign vendor to a booking                         | Vendor is assigned to the booking.              |
| `/newbooking`                     | GET     | Yes            | View new bookings                                   | Returns list of new bookings.                   |
| `/history`                        | GET     | Yes            | View booking history                                | Returns list of booking history.                |
| `/VenueList`                     | GET     | Yes            | Get list of venues                                  | Returns list of all venues.                     |
| `/addvenue`                      | POST    | Yes            | Add a new venue                                    | Venue is saved and user is redirected.         |
| `/updateProfile`                 | POST    | Yes            | Update user profile                                 | User profile is updated in the system.         |
| `/confirmation`                  | GET     | Yes            | Show confirmation page for event registration      | Returns confirmation page.                      |

### 4. Endpoints Summary

- **User Registration**: `/register`
- **User Login**: `/login`
- **Retrieve Events**: `/events`
- **Event Creation Form**: `/eventForm`
- **Submit Event**: `/submit`
- **Retrieve Bookings**: `/bookings`
- **Retrieve Vendors**: `/vendors`
- **Assign Vendor**: `/assignVendor/{bookingId}`
- **View New Bookings**: `/newbooking`
- **View Booking History**: `/history`
- **Retrieve Venues**: `/VenueList`
- **Add Venue**: `/addvenue`
- **Update User Profile**: `/updateProfile`
- **Event Confirmation**: `/confirmation`

This structured format provides a comprehensive overview of the Event Management Service, detailing its functionalities, API endpoints, and overall business logic.

## Plant UML script

Below is a PlantUML sequence diagram script based on the provided business logic for the Event Management System. This diagram includes all key actors, interactions, decision points, and endpoints as specified.

```plantuml
@startuml
actor User
actor Vendor
actor Admin
participant System
participant "Message Broker\n(RabbitMQ)" as Broker

User -> System : POST /register(username, email, password)
System -> System : Validate unique username and email
System -> System : Hash password
System -> System : Store user data
System -> User : Redirect to login page

User -> System : POST /login(username, password)
System -> System : Validate credentials
alt Successful Authentication
    System -> User : Redirect to home
else Authentication Failed
    System -> User : Show error message
end

User -> System : GET /eventForm
System -> User : Return event creation form

User -> System : POST /submit(event details)
System -> System : Save event data
System -> System : Set status = "pending"
System -> User : Confirmation of event creation

User -> System : GET /vendors
System -> User : Return list of vendors

User -> System : POST /assignVendor(bookingId, vendorId)
alt Vendor and Event Exist
    System -> System : Update event with vendor information
    System -> User : Confirmation of vendor assignment
else Vendor or Event Not Found
    System -> User : Show error message
end

User -> System : GET /bookings
System -> User : Return list of bookings

User -> System : GET /history
System -> User : Return list of booking history

User -> System : GET /VenueList
System -> User : Return list of venues

User -> System : POST /addvenue(venue details)
System -> System : Save venue data
System -> User : Redirect to venue list

User -> System : POST /updateProfile(user details)
System -> System : Update user profile
System -> User : Confirmation of profile update

User -> System : GET /confirmation
System -> User : Return confirmation page

== Publish Events to Message Broker ==
System -> Broker : Publish event creation message
System -> Broker : Publish vendor assignment message

@enduml
```

### Explanation of the Diagram:
1. **Actors**: The diagram includes the `User`, `Vendor`, `Admin`, and `System` participants, as well as the `Message Broker` (RabbitMQ) for message publishing.
2. **Interactions**: The sequence of interactions is clearly outlined, showing how the user registers, logs in, creates events, assigns vendors, and manages bookings.
3. **Decision Points**: The diagram highlights critical decision points, such as successful authentication and vendor assignment checks.
4. **Messages**: Each interaction is described with clear and descriptive messages, making it easy to understand the flow of data and actions.
5. **Endpoints**: All relevant endpoints are included in the interactions to show how the system responds to user requests.
6. **Message Publishing**: The diagram concludes with the system publishing messages to the RabbitMQ broker, indicating the integration of messaging within the system.

This script can be rendered using PlantUML to visualize the sequence diagram.

## UML Sequence Diagram

![Diagram](data:image/png;base64,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)

## UML Sequence Diagram Path

https://github.com/chandrakiran428/Capstone-project/blob/main/images/Capstone-project_cbf9cac9-8a9e-4988-9c06-540f704bd589.png

## API Documentation

# API Documentation

## /register POST
Endpoint URL: `/register`  
HTTP Method: POST  
Authentication: None  
Authorization: None  
Description: Register a new user  

### Request Parameters
| Parameter Name | Type   | Required | Description                      |
|----------------|--------|----------|----------------------------------|
| username       | string | Yes      | Unique username for the user     |
| email          | string | Yes      | Valid email address              |
| password       | string | Yes      | Password for the account         |

### Sample Request
```json
{
  "username": "newuser",
  "email": "newuser@example.com",
  "password": "securepassword"
}
```

### Response Parameters
| Field Name | Type   | Description                        |
|------------|--------|------------------------------------|
| message     | string | Confirmation message of registration |
| userId      | int    | Unique identifier for the user     |

### Sample Response
```json
{
  "message": "User registered successfully.",
  "userId": 123
}
```

### Components Involved
- User Service
- Database

### Data Mapping
| Source Field     | Target Field   | Transformation         |
|------------------|----------------|-------------------------|
| request.username  | User.username  | Direct mapping          |
| request.email     | User.email     | Direct mapping          |
| request.password  | User.password  | Hash before storage     |

### Sample Errors
```json
{
  "error": "Username already exists.",
  "code": 400,
  "message": "The username provided is already in use."
}
```

### Root Exception Details
| Name         | Description                      | Header/Payload | Type   | Mandatory/Optional | Notes                          |
|--------------|----------------------------------|----------------|--------|--------------------|--------------------------------|
| UserExists   | Thrown when username is taken   | Payload        | string | Mandatory           | Indicates a conflict error     |

### Error Codes
| HTTP Code | Message                      | Description                               |
|-----------|------------------------------|-------------------------------------------|
| 400       | Username already exists      | The username provided is already in use. |
| 400       | Invalid email format         | The email address provided is invalid.    |

---

## /login POST
Endpoint URL: `/login`  
HTTP Method: POST  
Authentication: None  
Authorization: None  
Description: Authenticate user  

### Request Parameters
| Parameter Name | Type   | Required | Description                      |
|----------------|--------|----------|----------------------------------|
| username       | string | Yes      | Username of the user             |
| password       | string | Yes      | Password for the account         |

### Sample Request
```json
{
  "username": "existinguser",
  "password": "securepassword"
}
```

### Response Parameters
| Field Name | Type   | Description                        |
|------------|--------|------------------------------------|
| message     | string | Success or failure message         |
| token       | string | Authentication token for session   |

### Sample Response
```json
{
  "message": "Login successful.",
  "token": "abc123xyz"
}
```

### Components Involved
- Authentication Service
- User Service
- Database

### Data Mapping
| Source Field     | Target Field   | Transformation         |
|------------------|----------------|-------------------------|
| request.username  | User.username  | Direct mapping          |
| request.password  | User.password  | Verify against stored hash |

### Sample Errors
```json
{
  "error": "Invalid credentials.",
  "code": 401,
  "message": "The username or password is incorrect."
}
```

### Root Exception Details
| Name         | Description                      | Header/Payload | Type   | Mandatory/Optional | Notes                          |
|--------------|----------------------------------|----------------|--------|--------------------|--------------------------------|
| InvalidLogin | Thrown when credentials are wrong| Payload        | string | Mandatory           | Indicates authentication failure|

### Error Codes
| HTTP Code | Message                      | Description                               |
|-----------|------------------------------|-------------------------------------------|
| 401       | Invalid credentials          | The username or password is incorrect.    |

---

## /events GET
Endpoint URL: `/events`  
HTTP Method: GET  
Authentication: Bearer Token Required  
Authorization: User Role Required  
Description: Get all events  

### Request Parameters
| Parameter Name | Type   | Required | Description                      |
|----------------|--------|----------|----------------------------------|
| None           | -      | -        | No parameters required           |

### Sample Request
```http
GET /events HTTP/1.1
Authorization: Bearer abc123xyz
```

### Response Parameters
| Field Name | Type   | Description                        |
|------------|--------|------------------------------------|
| events     | array  | List of events associated with user |

### Sample Response
```json
{
  "events": [
    {
      "id": 1,
      "name": "Birthday Party",
      "status": "pending"
    },
    {
      "id": 2,
      "name": "Wedding",
      "status": "accepted"
    }
  ]
}
```

### Components Involved
- Event Service
- Database

### Data Mapping
| Source Field     | Target Field   | Transformation         |
|------------------|----------------|-------------------------|
| User ID          | Event.userId   | Fetch events for user   |

### Sample Errors
```json
{
  "error": "Unauthorized access.",
  "code": 403,
  "message": "You do not have permission to view events."
}
```

### Root Exception Details
| Name         | Description                      | Header/Payload | Type   | Mandatory/Optional | Notes                          |
|--------------|----------------------------------|----------------|--------|--------------------|--------------------------------|
| Unauthorized | Thrown when user is not logged in| Header         | string | Mandatory           | Indicates authentication failure|

### Error Codes
| HTTP Code | Message                      | Description                               |
|-----------|------------------------------|-------------------------------------------|
| 403       | Unauthorized access          | You do not have permission to view events. |

---

## /eventForm GET
Endpoint URL: `/eventForm`  
HTTP Method: GET  
Authentication: Bearer Token Required  
Authorization: User Role Required  
Description: Show event creation form  

### Request Parameters
| Parameter Name | Type   | Required | Description                      |
|----------------|--------|----------|----------------------------------|
| None           | -      | -        | No parameters required           |

### Sample Request
```http
GET /eventForm HTTP/1.1
Authorization: Bearer abc123xyz
```

### Response Parameters
| Field Name | Type   | Description                        |
|------------|--------|------------------------------------|
| form       | object | Event creation form details        |

### Sample Response
```json
{
  "form": {
    "eventTypes": ["Birthday", "Wedding", "Corporate"],
    "venues": ["Venue A", "Venue B"]
  }
}
```

### Components Involved
- Event Service
- Venue Service

### Data Mapping
| Source Field     | Target Field   | Transformation         |
|------------------|----------------|-------------------------|
| None             | Form           | Direct mapping          |

### Sample Errors
```json
{
  "error": "Unauthorized access.",
  "code": 403,
  "message": "You do not have permission to access the event creation form."
}
```

### Root Exception Details
| Name         | Description                      | Header/Payload | Type   | Mandatory/Optional | Notes                          |
|--------------|----------------------------------|----------------|--------|--------------------|--------------------------------|
| Unauthorized | Thrown when user is not logged in| Header         | string | Mandatory           | Indicates authentication failure|

### Error Codes
| HTTP Code | Message                      | Description                               |
|-----------|------------------------------|-------------------------------------------|
| 403       | Unauthorized access          | You do not have permission to access the event creation form. |

---

## /submit POST
Endpoint URL: `/submit`  
HTTP Method: POST  
Authentication: Bearer Token Required  
Authorization: User Role Required  
Description: Submit new event  

### Request Parameters
| Parameter Name    | Type   | Required | Description                      |
|-------------------|--------|----------|----------------------------------|
| name              | string | Yes      | Name of the event                |
| eventType         | string | Yes      | Type of the event                |
| venue             | string | Yes      | Selected venue for the event     |
| numberOfAttendees | int    | Yes      | Expected number of attendees     |
| date              | string | Yes      | Date of the event (YYYY-MM-DD)   |

### Sample Request
```json
{
  "name": "Corporate Meeting",
  "eventType": "Corporate",
  "venue": "Venue A",
  "numberOfAttendees": 50,
  "date": "2023-12-01"
}
```

### Response Parameters
| Field Name | Type   | Description                        |
|------------|--------|------------------------------------|
| message     | string | Confirmation message of event submission |
| eventId     | int    | Unique identifier for the event    |

### Sample Response
```json
{
  "message": "Event submitted successfully.",
  "eventId": 456
}
```

### Components Involved
- Event Service
- Database

### Data Mapping
| Source Field     | Target Field   | Transformation         |
|------------------|----------------|-------------------------|
| request.name      | Event.name     | Direct mapping          |
| request.eventType | Event.type     | Direct mapping          |
| request.venue     | Event.venue    | Direct mapping          |

### Sample Errors
```json
{
  "error": "Invalid event data.",
  "code": 400,
  "message": "The event details provided are invalid."
}
```

### Root Exception Details
| Name         | Description                      | Header/Payload | Type   | Mandatory/Optional | Notes                          |
|--------------|----------------------------------|----------------|--------|--------------------|--------------------------------|
| InvalidEvent | Thrown when event data is invalid| Payload        | string | Mandatory           | Indicates validation failure    |

### Error Codes
| HTTP Code | Message                      | Description                               |
|-----------|------------------------------|-------------------------------------------|
| 400       | Invalid event data           | The event details provided are invalid.   |

---

## /bookings GET
Endpoint URL: `/bookings`  
HTTP Method: GET  
Authentication: Bearer Token Required  
Authorization: User Role Required  
Description: Get all bookings  

### Request Parameters
| Parameter Name | Type   | Required | Description                      |
|----------------|--------|----------|----------------------------------|
| None           | -      | -        | No parameters required           |

### Sample Request
```http
GET /bookings HTTP/1.1
Authorization: Bearer abc123xyz
```

### Response Parameters
| Field Name | Type   | Description                        |
|------------|--------|------------------------------------|
| bookings   | array  | List of bookings associated with user |

### Sample Response
```json
{
  "bookings": [
    {
      "id": 1,
      "eventName": "Birthday Party",
      "status": "confirmed"
    },
    {
      "id": 2,
      "eventName": "Corporate Meeting",
      "status": "pending"
    }
  ]
}
```

### Components Involved
- Booking Service
- Database

### Data Mapping
| Source Field     | Target Field   | Transformation         |
|------------------|----------------|-------------------------|
| User ID          | Booking.userId | Fetch bookings for user  |

### Sample Errors
```json
{
  "error": "Unauthorized access.",
  "code": 403,
  "message": "You do not have permission to view bookings."
}
```

### Root Exception Details
| Name         | Description                      | Header/Payload | Type   | Mandatory/Optional | Notes                          |
|--------------|----------------------------------|----------------|--------|--------------------|--------------------------------|
| Unauthorized | Thrown when user is not logged in| Header         | string | Mandatory           | Indicates authentication failure|

### Error Codes
| HTTP Code | Message                      | Description                               |
|-----------|------------------------------|-------------------------------------------|
| 403       | Unauthorized access          | You do not have permission to view bookings. |

---

## /vendors GET
Endpoint URL: `/vendors`  
HTTP Method: GET  
Authentication: Bearer Token Required  
Authorization: User Role Required  
Description: Get all vendors  

### Request Parameters
| Parameter Name | Type   | Required | Description                      |
|----------------|--------|----------|----------------------------------|
| None           | -      | -        | No parameters required           |

### Sample Request
```http
GET /vendors HTTP/1.1
Authorization: Bearer abc123xyz
```

### Response Parameters
| Field Name | Type   | Description                        |
|------------|--------|------------------------------------|
| vendors    | array  | List of all vendors                |

### Sample Response
```json
{
  "vendors": [
    {
      "id": 1,
      "vendor_name": "Catering Co.",
      "vendor_status": "active"
    },
    {
      "id": 2,
      "vendor_name": "DJ Services",
      "vendor_status": "active"
    }
  ]
}
```

### Components Involved
- Vendor Service
- Database

### Data Mapping
| Source Field     | Target Field   | Transformation         |
|------------------|----------------|-------------------------|
| None             | Vendors        | Direct mapping          |

### Sample Errors
```json
{
  "error": "Unauthorized access.",
  "code": 403,
  "message": "You do not have permission to view vendors."
}
```

### Root Exception Details
| Name         | Description                      | Header/Payload | Type   | Mandatory/Optional | Notes                          |
|--------------|----------------------------------|----------------|--------|--------------------|--------------------------------|
| Unauthorized | Thrown when user is not logged in| Header         | string | Mandatory           | Indicates authentication failure|

### Error Codes
| HTTP Code | Message                      | Description                               |
|-----------|------------------------------|-------------------------------------------|
| 403       | Unauthorized access          | You do not have permission to view vendors. |

---

## /assignVendor/{bookingId} POST
Endpoint URL: `/assignVendor/{bookingId}`  
HTTP Method: POST  
Authentication: Bearer Token Required  
Authorization: User Role Required  
Description: Assign vendor to a booking  

### Request Parameters
| Parameter Name | Type   | Required | Description                      |
|----------------|--------|----------|----------------------------------|
| bookingId      | int    | Yes      | Unique identifier for the booking |
| vendorId       | int    | Yes      | Unique identifier for the vendor  |

### Sample Request
```json
{
  "vendorId": 1
}
```

### Response Parameters
| Field Name | Type   | Description                        |
|------------|--------|------------------------------------|
| message     | string | Confirmation message of vendor assignment |

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
| Source Field     | Target Field   | Transformation         |
|------------------|----------------|-------------------------|
| request.vendorId  | Booking.vendorId| Update booking with vendor |

### Sample Errors
```json
{
  "error": "Invalid vendor assignment.",
  "code": 400,
  "message": "The vendor is not valid for this booking."
}
```

### Root Exception Details
| Name         | Description                      | Header/Payload | Type   | Mandatory/Optional | Notes                          |
|--------------|----------------------------------|----------------|--------|--------------------|--------------------------------|
| InvalidVendor| Thrown when vendor assignment is invalid| Payload        | string | Mandatory           | Indicates validation failure    |

### Error Codes
| HTTP Code | Message                      | Description                               |
|-----------|------------------------------|-------------------------------------------|
| 400       | Invalid vendor assignment     | The vendor is not valid for this booking. |

---

## /newbooking GET
Endpoint URL: `/newbooking`  
HTTP Method: GET  
Authentication: Bearer Token Required  
Authorization: User Role Required  
Description: View new bookings  

### Request Parameters
| Parameter Name | Type   | Required | Description                      |
|----------------|--------|----------|----------------------------------|
| None           | -      | -        | No parameters required           |

### Sample Request
```http
GET /newbooking HTTP/1.1
Authorization: Bearer abc123xyz
```

### Response Parameters
| Field Name | Type   | Description                        |
|------------|--------|------------------------------------|
| newBookings | array  | List of new bookings               |

### Sample Response
```json
{
  "newBookings": [
    {
      "id": 3,
      "eventName": "Company Retreat",
      "status": "pending"
    }
  ]
}
```

### Components Involved
- Booking Service
- Database

### Data Mapping
| Source Field     | Target Field   | Transformation         |
|------------------|----------------|-------------------------|
| User ID          | NewBooking.userId | Fetch new bookings for user |

### Sample Errors
```json
{
  "error": "Unauthorized access.",
  "code": 403,
  "message": "You do not have permission to view new bookings."
}
```

### Root Exception Details
| Name         | Description                      | Header/Payload | Type   | Mandatory/Optional | Notes                          |
|--------------|----------------------------------|----------------|--------|--------------------|--------------------------------|
| Unauthorized | Thrown when user is not logged in| Header         | string | Mandatory           | Indicates authentication failure|

### Error Codes
| HTTP Code | Message                      | Description                               |
|-----------|------------------------------|-------------------------------------------|
| 403       | Unauthorized access          | You do not have permission to view new bookings. |

---

## /history GET
Endpoint URL: `/history`  
HTTP Method: GET  
Authentication: Bearer Token Required  
Authorization: User Role Required  
Description: View booking history  

### Request Parameters
| Parameter Name | Type   | Required | Description                      |
|----------------|--------|----------|----------------------------------|
| None           | -      | -        | No parameters required           |

### Sample Request
```http
GET /history HTTP/1.1
Authorization: Bearer abc123xyz
```

### Response Parameters
| Field Name | Type   | Description                        |
|------------|--------|------------------------------------|
| history    | array  | List of booking history            |

### Sample Response
```json
{
  "history": [
    {
      "id": 1,
      "eventName": "Birthday Party",
      "status": "completed"
    }
  ]
}
```

### Components Involved
- Booking Service
- Database

### Data Mapping
| Source Field     | Target Field   | Transformation         |
|------------------|----------------|-------------------------|
| User ID          | BookingHistory.userId | Fetch booking history for user |

### Sample Errors
```json
{
  "error": "Unauthorized access.",
  "code": 403,
  "message": "You do not have permission to view booking history."
}
```

### Root Exception Details
| Name         | Description                      | Header/Payload | Type   | Mandatory/Optional | Notes                          |
|--------------|----------------------------------|----------------|--------|--------------------|--------------------------------|
| Unauthorized | Thrown when user is not logged in| Header         | string | Mandatory           | Indicates authentication failure|

### Error Codes
| HTTP Code | Message                      | Description                               |
|-----------|------------------------------|-------------------------------------------|
| 403       | Unauthorized access          | You do not have permission to view booking history. |

---

## /VenueList GET
Endpoint URL: `/VenueList`  
HTTP Method: GET  
Authentication: Bearer Token Required  
Authorization: User Role Required  
Description: Get list of venues  

### Request Parameters
| Parameter Name | Type   | Required | Description                      |
|----------------|--------|----------|----------------------------------|
| None           | -      | -        | No parameters required           |

### Sample Request
```http
GET /VenueList HTTP/1.1
Authorization: Bearer abc123xyz
```

### Response Parameters
| Field Name | Type   | Description                        |
|------------|--------|------------------------------------|
| venues     | array  | List of all venues                 |

### Sample Response
```json
{
  "venues": [
    {
      "id": 1,
      "venueName": "Venue A",
      "capacity": 200
    },
    {
      "id": 2,
      "venueName": "Venue B",
      "capacity": 150
    }
  ]
}
```

### Components Involved
- Venue Service
- Database

### Data Mapping
| Source Field     | Target Field   | Transformation         |
|------------------|----------------|-------------------------|
| None             | Venues         | Direct mapping          |

### Sample Errors
```json
{
  "error": "Unauthorized access.",
  "code": 403,
  "message": "You do not have permission to view venues."
}
```

### Root Exception Details
| Name         | Description                      | Header/Payload | Type   | Mandatory/Optional | Notes                          |
|--------------|----------------------------------|----------------|--------|--------------------|--------------------------------|
| Unauthorized | Thrown when user is not logged in| Header         | string | Mandatory           | Indicates authentication failure|

### Error Codes
| HTTP Code | Message                      | Description                               |
|-----------|------------------------------|-------------------------------------------|
| 403       | Unauthorized access          | You do not have permission to view venues. |

---

## /addvenue POST
Endpoint URL: `/addvenue`  
HTTP Method: POST  
Authentication: Bearer Token Required  
Authorization: Admin Role Required  
Description: Add a new venue  

### Request Parameters
| Parameter Name | Type   | Required | Description                      |
|----------------|--------|----------|----------------------------------|
| venueName      | string | Yes      | Name of the venue                |
| capacity       | int    | Yes      | Maximum capacity of the venue     |
| address        | string | Yes      | Address of the venue              |
| phoneNumber    | string | Yes      | Contact number for the venue      |
| cost           | float  | Yes      | Cost of using the venue           |

### Sample Request
```json
{
  "venueName": "New Venue",
  "capacity": 300,
  "address": "123 New Venue St.",
  "phoneNumber": "123-456-7890",
  "cost": 1500.00
}
```

### Response Parameters
| Field Name | Type   | Description                        |
|------------|--------|------------------------------------|
| message     | string | Confirmation message of venue addition |
| venueId     | int    | Unique identifier for the venue    |

### Sample Response
```json
{
  "message": "Venue added successfully.",
  "venueId": 789
}
```

### Components Involved
- Venue Service
- Database

### Data Mapping
| Source Field     | Target Field   | Transformation         |
|------------------|----------------|-------------------------|
| request.venueName | Venue.name     | Direct mapping          |
| request.capacity   | Venue.capacity | Direct mapping          |

### Sample Errors
```json
{
  "error": "Invalid venue data.",
  "code": 400,
  "message": "The venue details provided are invalid."
}
```

### Root Exception Details
| Name         | Description                      | Header/Payload | Type   | Mandatory/Optional | Notes                          |
|--------------|----------------------------------|----------------|--------|--------------------|--------------------------------|
| InvalidVenue | Thrown when venue data is invalid| Payload        | string | Mandatory           | Indicates validation failure    |

### Error Codes
| HTTP Code | Message                      | Description                               |
|-----------|------------------------------|-------------------------------------------|
| 400       | Invalid venue data           | The venue details provided are invalid.   |

---

## /updateProfile POST
Endpoint URL: `/updateProfile`  
HTTP Method: POST  
Authentication: Bearer Token Required  
Authorization: User Role Required  
Description: Update user profile  

### Request Parameters
| Parameter Name | Type   | Required | Description                      |
|----------------|--------|----------|----------------------------------|
| username       | string | Yes      | New username for the user        |
| email          | string | Yes      | New email address                |
| address        | string | No       | New address                      |
| mobile         | string | No       | New mobile number                |

### Sample Request
```json
{
  "username": "updateduser",
  "email": "updateduser@example.com",
  "address": "456 Updated St.",
  "mobile": "987-654-3210"
}
```

### Response Parameters
| Field Name | Type   | Description                        |
|------------|--------|------------------------------------|
| message     | string | Confirmation message of profile update |

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
| Source Field     | Target Field   | Transformation         |
|------------------|----------------|-------------------------|
| request.username  | User.username  | Direct mapping          |
| request.email     | User.email     | Direct mapping          |

### Sample Errors
```json
{
  "error": "Invalid profile data.",
  "code": 400,
  "message": "The profile details provided are invalid."
}
```

### Root Exception Details
| Name         | Description                      | Header/Payload | Type   | Mandatory/Optional | Notes                          |
|--------------|----------------------------------|----------------|--------|--------------------|--------------------------------|
| InvalidProfile | Thrown when profile data is invalid| Payload        | string | Mandatory           | Indicates validation failure    |

### Error Codes
| HTTP Code | Message                      | Description                               |
|-----------|------------------------------|-------------------------------------------|
| 400       | Invalid profile data         | The profile details provided are invalid. |

---

## /confirmation GET
Endpoint URL: `/confirmation`  
HTTP Method: GET  
Authentication: Bearer Token Required  
Authorization: User Role Required  
Description: Show confirmation page for event registration  

### Request Parameters
| Parameter Name | Type   | Required | Description                      |
|----------------|--------|----------|----------------------------------|
| None           | -      | -        | No parameters required           |

### Sample Request
```http
GET /confirmation HTTP/1.1
Authorization: Bearer abc123xyz
```

### Response Parameters
| Field Name | Type   | Description                        |
|------------|--------|------------------------------------|
| confirmation | object | Confirmation details of the event |

### Sample Response
```json
{
  "confirmation": {
    "eventName": "Corporate Meeting",
    "status": "pending",
    "date": "2023-12-01"
  }
}
```

### Components Involved
- Event Service

### Data Mapping
| Source Field     | Target Field   | Transformation         |
|------------------|----------------|-------------------------|
| User ID          | Confirmation.userId | Fetch confirmation details for user |

### Sample Errors
```json
{
  "error": "Unauthorized access.",
  "code": 403,
  "message": "You do not have permission to view confirmation."
}
```

### Root Exception Details
| Name         | Description                      | Header/Payload | Type   | Mandatory/Optional | Notes                          |
|--------------|----------------------------------|----------------|--------|--------------------|--------------------------------|
| Unauthorized | Thrown when user is not logged in| Header         | string | Mandatory           | Indicates authentication failure|

### Error Codes
| HTTP Code | Message                      | Description                               |
|-----------|------------------------------|-------------------------------------------|
| 403       | Unauthorized access          | You do not have permission to view confirmation. |

---

