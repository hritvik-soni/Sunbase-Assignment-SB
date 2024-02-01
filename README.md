# Customer CRUD Application

This is a basic CRUD (Create, Read, Update, Delete) application for managing customer information. The application uses MySQL as the database, Spring Boot for the backend, and HTML/CSS for the frontend.

## Backend

### Technologies Used

- Database: MySQL
- Backend: Spring Boot
- Authentication: JWT

### API Endpoints

- **Create a Customer**

  - Method: POST
  - Path: `/api/customers`
  - Request Body:
    ```json
    {
      "first_name": "Jane",
      "last_name": "Doe",
      "street": "Elvnu Street",
      "address": "H no 2 ",
      "city": "Delhi",
      "state": "Delhi",
      "email": "sam@gmail.com",
      "phone": "12345678"
    }
    ```

- **Update a Customer**

  - Method: PUT
  - Path: `/api/customers/{id}`
  - Request Body (similar to create):

- **Get List of Customers**

  - Method: GET
  - Path: `/api/customers`
  - Query Parameters: `page`, `size`, `sort`, `search`

- **Get a Single Customer**

  - Method: GET
  - Path: `/api/customers/{id}`

- **Delete a Customer**

  - Method: DELETE
  - Path: `/api/customers/{id}`

- **Authentication**
  - Method: POST
  - Path: `/api/authenticate`
  - Request Body:
    ```json
    {
      "login_id": "test@sunbasedata.com",
      "password": "Test@123"
    }
    ```
  - Response: Bearer Token

## Frontend

### Screens

1. **Login Screen**

   - Accepts login credentials and obtains a Bearer Token.

2. **Customer List Screen**

   - Displays a basic HTML table of customers with pagination, sorting, and searching capabilities.
   - Option to sync with a remote API to update the customer list.

3. **Add a New Customer**
   - Form to add a new customer.

### Sync with Remote API

- Method: POST
- Path: `https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp`
- Parameters: `cmd=get_customer_list`
- Headers: `Authorization: Bearer token_received_in_authentication_API_call`

Response:

```json
[
  {
    "uuid": "tytyytytyyyy345ryeyey",
    "first_name": "Jane",
    "last_name": "Doe",
    "street": "Elvnu Street",
    "address": "H no 2 ",
    "city": "Delhi",
    "state": "Delhi",
    "email": "sam@gmail.com",
    "phone": "12345678"
  }
]
```

## How to Run

- Set up MySQL database and configure backend accordingly.
- Run the backend application.
- Hit Url: http://localhost:8080/ (login page, use id and password defined above)

#

Happy Coding !
